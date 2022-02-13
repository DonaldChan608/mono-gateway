package com.donald.gateway.config;

import com.donald.gateway.connector.DownStreamConnector;
import com.donald.gateway.connector.UpStreamConnector;
import com.donald.gateway.receiver.MessageReceiver;
import com.donald.gateway.sender.MessageSender;
import org.springframework.context.annotation.Bean;

import java.net.SocketException;

public class ConnectorConfig {

    @Bean
    public UpStreamConnector upStreamConnector() throws SocketException {
        return new UpStreamConnector();
    }

    @Bean
    public DownStreamConnector downStreamConnector() {
        return new DownStreamConnector();
    }

    @Bean
    public MessageSender messageSender() {
        return new MessageSender();
    }

    @Bean
    public MessageReceiver messageReceiver() {
        return new MessageReceiver();
    }

}
