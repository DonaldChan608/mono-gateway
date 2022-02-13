package com.donald.gateway.sender;

import com.donald.gateway.config.MonoGatewayConfig;
import com.donald.gateway.connector.DownStreamConnector;
import com.donald.gateway.helper.LittleEndianByteHelper;
import com.donald.gateway.pojo.Order;
import com.donald.gateway.sotrage.OrderQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Throttle: if sending rates from UDP is faster than config throttle.
 * cache messages and release base on message rate
 */
@Slf4j
public class MessageSender {

    @Autowired
    private MonoGatewayConfig config;

    @Autowired
    private OrderQueue orderQueue;

    @Autowired
    private DownStreamConnector downStreamConnector;


    private SenderThread senderThread;
    private final LinkedBlockingQueue<Order> sendQueue = new LinkedBlockingQueue<>();
    private final AtomicInteger sendCountPerSec = new AtomicInteger(0);

    @PostConstruct
    public void init() throws InterruptedException {
        // setup sender thread
        senderThread = new SenderThread();
        senderThread.start();

        // setup reset schedule
//        ScheduledExecutorService resetSchedule = Executors.newSingleThreadScheduledExecutor();
//        resetSchedule.scheduleAtFixedRate(() -> {
//            log.info("sendCountPerSec = {}", sendCountPerSec);
//            synchronized (sendCountPerSec) {
//                sendCountPerSec.set(0);
//            }
//        }, 1, 1, TimeUnit.SECONDS);
    }

    /**
     * @return false if queue's fulled
     */
    public boolean sendMsg(Order order) {
        return sendQueue.add(order);
    }

     class SenderThread extends Thread {
        private boolean running;

        @Override
        public void run() {
            running = true;
            long eachSendTime = 1000/config.getMsgPerSec();
            while (running) {
                try {
                    long startTime = System.currentTimeMillis();
                    Order order = sendQueue.take();
                    orderQueue.removeFromOrderMap(order.getClOrdID());
                    if (!order.isActive()) {
                        continue;
                    }

                    sendCountPerSec.addAndGet(1);
                    downStreamConnector.sendMsg(orderToBytes(order));

                    long sleepMillis = eachSendTime + startTime - System.currentTimeMillis();
                    if (sleepMillis > 0) {
                        Thread.sleep(sleepMillis);
                    }
                } catch (InterruptedException ignored) {
                } catch (IOException e) {
                    log.error("fail to send msg", e);
                }
            }
        }

        public void stopThread() {
            running = false;
        }

        private byte[] orderToBytes(Order order) {
            if (order.getMsgLength() != 21) {
                log.error("msgLength = {}", order.getMsgLength());
            }
            byte[] bytes = new byte[order.getMsgLength()];
            int pos = 0;
            pos = LittleEndianByteHelper.writeShortToByteArray(bytes, order.getMsgLength(), pos);
            bytes[pos++] = (byte) order.getMsgType();
            pos = LittleEndianByteHelper.writeLongToByteArray(bytes, order.getClOrdID(), pos);
            pos = LittleEndianByteHelper.writeShortToByteArray(bytes, order.getSymbol(), pos);
            pos = LittleEndianByteHelper.writeLongToByteArray(bytes, order.getPrice(), pos);
            return bytes;
        }
    }


}
