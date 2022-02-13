package com.donald.gateway.config;

import com.donald.gateway.sotrage.OrderQueue;
import org.springframework.context.annotation.Bean;

public class StorageConfig {

    @Bean("orderQueue")
    public OrderQueue orderQueue() {
        return new OrderQueue();
    }

}
