package com.donald.gateway.receiver;

import com.donald.gateway.helper.LittleEndianByteHelper;
import com.donald.gateway.pojo.Order;
import com.donald.gateway.sotrage.OrderQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class MessageReceiver extends Thread {

    @Autowired
    private OrderQueue orderQueue;

    private final BlockingQueue<byte[]> incomingMsgQueue = new LinkedBlockingQueue<>(30000);

    private boolean running;

    public void putMsg(byte[] incomingMsg) throws InterruptedException {
        incomingMsgQueue.put(incomingMsg);
    }

    public void stopRunning() {
        running = false;
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                byte[] msg = incomingMsgQueue.take();
                processMsg(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void processMsg(byte[] msg) {
        short msgLength = LittleEndianByteHelper.getShortFromBytes(msg, 0);
        char msgType = (char) msg[2];
        long clOrdID = LittleEndianByteHelper.getLongFromBytes(msg, 3);
        short symbol = LittleEndianByteHelper.getShortFromBytes(msg, 11);
        long price = LittleEndianByteHelper.getLongFromBytes(msg, 13);

        Order order =  new Order(msgLength, msgType, clOrdID, symbol, price);
        orderQueue.addOrder(order);
    }

}
