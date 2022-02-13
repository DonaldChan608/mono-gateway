package com.donald.gateway;

import com.donald.gateway.config.MonoGatewayConfig;
import com.donald.gateway.connector.DownStreamConnector;
import com.donald.gateway.connector.UpStreamConnector;
import com.donald.gateway.receiver.MessageReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MonoGateway {

    @Autowired
    private UpStreamConnector upStreamConnector;

    @Autowired
    private DownStreamConnector downStreamConnector;

    @Autowired
    private MessageReceiver messageReceiver;

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new AnnotationConfigApplicationContext(MonoGatewayConfig.class);
        MonoGateway server = context.getBean(MonoGateway.class);
        server.upStreamConnector.start();
        Thread.sleep(500);      // udp warm up
        server.downStreamConnector.init();
        server.messageReceiver.start();
    }

}
