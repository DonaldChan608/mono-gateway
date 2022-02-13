package com.donald.gateway.config;

import com.donald.gateway.MonoGateway;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.net.SocketException;

@Configuration
@Import({FilterConfig.class, StorageConfig.class, ConnectorConfig.class})
@PropertySource("classpath:application.properties")
@Getter
public class MonoGatewayConfig {

    @Value("${upstream.port}")
    private int upstreamPort;

    @Value("${downstream.ip}")
    private String downstreamIp;

    @Value("${downstream.port}")
    private int downstreamPort;

    @Value("${orderProcessor.thread}")
    private int orderProcessorThread;

    @Value("${msgSender.msgPerSec}")
    private int msgPerSec;

    @Bean
    public MonoGateway monoGateway() throws SocketException {
        return new MonoGateway();
    }
}
