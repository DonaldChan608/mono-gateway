package com.donald.gateway.connector;

import com.donald.gateway.config.MonoGatewayConfig;
import com.donald.gateway.receiver.MessageReceiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

@Slf4j
public class UpStreamConnector extends Thread {

    @Autowired
    private MonoGatewayConfig config;

    @Autowired
    private MessageReceiver receiver;

    private boolean running;

    @Override
    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket(config.getUpstreamPort());
            running = true;
            int count = 0;
            while (running) {
                try {
                    byte[] buf = new byte[21];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);
                    receiver.putMsg(packet.getData());
//                    log.info("inbound = {}", ++count);
                } catch (IOException e) {
                    log.error("error on receiving packet", e);
                } catch (InterruptedException ignored) {
                }
            }
            socket.close();
        } catch (SocketException e) {
            log.error("fail to connect upstream", e);
        }
    }

    public void shutdown() {
        running = false;
    }
}
