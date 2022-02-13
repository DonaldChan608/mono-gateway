package com.donald.gateway.filter;

import com.donald.gateway.pojo.Order;

public interface Filter {

    /**
     * @return true if process aborted (not sending to outbound)
     */
    boolean process(Order order);

}
