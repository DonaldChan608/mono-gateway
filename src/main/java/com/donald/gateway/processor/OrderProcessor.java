package com.donald.gateway.processor;

import com.donald.gateway.config.MonoGatewayConfig;
import com.donald.gateway.filter.Filter;
import com.donald.gateway.pojo.Order;
import com.donald.gateway.sotrage.OrderQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class OrderProcessor {

    @Autowired
    private MonoGatewayConfig config;

    @Autowired
    private OrderQueue orderQueue;

    @Autowired
    @Qualifier("orderFilters")
    private List<Filter> orderFilters;

    private final ConcurrentHashMap<Short, Long> priceHistory = new ConcurrentHashMap<>();

    private Worker[] workers;

    @PostConstruct
    public void init() {
        workers = new Worker[config.getOrderProcessorThread()];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Worker();
            workers[i].start();
        }
    }

    class Worker extends Thread {
        private boolean running;

        @Override
        public void run() {
            running = true;
            while (running) {
                try {
                    Order order = orderQueue.getOrder();
                    processOrderByFilter(order);
                } catch (InterruptedException ignored) {
                }
            }
        }

        private void processOrderByFilter(Order order) {
            for (Filter filter : orderFilters) {
                if (filter.process(order)) {
                    break;     // do not send to next filter if process return true
                }
            }
        }

        public void stopThread() {
            running = false;
        }
    }


}
