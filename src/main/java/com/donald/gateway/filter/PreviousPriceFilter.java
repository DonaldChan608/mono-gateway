package com.donald.gateway.filter;

import com.donald.gateway.pojo.Order;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Drop order if price > previous order price * 1.05 per symbol
 */
@Slf4j
public class PreviousPriceFilter implements Filter {

    private final ConcurrentHashMap<Short, Long> priceHistory = new ConcurrentHashMap<>();

    @Override
    public boolean process(Order order) {
        if (order.getMsgType() == 'D') {
            Long prevPrice = priceHistory.get(order.getSymbol());
            if (prevPrice != null && prevPrice * 1.05 < order.getPrice()) {
                log.info("1.05 drop, orderID = {}", order.getClOrdID());
                return true; // drop
            }
            priceHistory.put(order.getSymbol(), order.getPrice());
        }
        return false;
    }


}
