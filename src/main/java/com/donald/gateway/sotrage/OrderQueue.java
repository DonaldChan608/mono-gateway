package com.donald.gateway.sotrage;

import com.donald.gateway.pojo.Order;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class OrderQueue {

    private final LinkedBlockingQueue<Order> orderQueue  = new LinkedBlockingQueue<>();
    private final ConcurrentMap<Long, Order> newOrderMap = new ConcurrentHashMap<>();   // save 'D' order only

    public void addOrder(Order order) {
        if (order.getMsgType() == 'D') {
            synchronized (newOrderMap) {
                newOrderMap.put(order.getClOrdID(), order);
            }
        }
        orderQueue.add(order);
    }

    public Order getOrder() throws InterruptedException {
        return orderQueue.take();
    }

    public void removeFromOrderMap(long orderId) {
        newOrderMap.remove(orderId);
    }

    public boolean deactivateOrder(long clOrdID) {
        synchronized (newOrderMap) {
            Order order = newOrderMap.get(clOrdID);
            if (order == null) {
                return false;
            }

            order.setActive(false);
            return true;
        }
    }
}
