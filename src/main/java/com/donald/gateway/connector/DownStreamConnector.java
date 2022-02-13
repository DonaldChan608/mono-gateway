package com.donald.gateway.connector;

import com.donald.gateway.config.MonoGatewayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

@Slf4j
public class DownStreamConnector {

    @Autowired
    private MonoGatewayConfig config;

    private Socket socket;
    private BufferedOutputStream out;

    public void init() throws InterruptedException {
        connect();
    }

    private void connect() throws InterruptedException {
        while (true) {
            try {
                this.socket = new Socket(config.getDownstreamIp(), config.getDownstreamPort());
//                socket.setTcpNoDelay(true);
                out = new BufferedOutputStream(socket.getOutputStream());
                break;
            } catch (IOException e) {
                Thread.sleep(1000);
                log.info("fail to connect downstream, reconnecting");
            }
        }
        log.info("connected to downstream");
    }

    public synchronized void sendMsg(byte[] msg) throws IOException {
        out.write(msg);
        out.flush();
    }
}
