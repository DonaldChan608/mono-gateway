package com.donald.gateway.filter;

import com.donald.gateway.pojo.Order;
import com.donald.gateway.sotrage.OrderQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * If MsgType = F recieved, cancel the New Order with matches the CIOrdID in queue
 * if not found in queue, send to out board
 */
@Slf4j
public class CancelOrderFilter implements Filter {

    @Autowired
    private OrderQueue orderQueue;

    @Override
    public boolean process(Order order) {
        if (order.getMsgType() == 'F') {
            // not sending cancel order outbound if successfully deactivate order in the system
            boolean foundPreviousOrder = orderQueue.deactivateOrder(order.getClOrdID());
            if (!foundPreviousOrder) {
                log.info("send cancel order to outbound - orderID = {}", order.getClOrdID());
            }
            return foundPreviousOrder;
        }
        return false;
    }
}
