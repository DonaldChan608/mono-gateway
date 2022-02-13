package com.donald.gateway.filter;

import com.donald.gateway.pojo.Order;
import com.donald.gateway.sender.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;

public class SendMsgFilter implements Filter {

    @Autowired
    private MessageSender msgSender;

    @Override
    public boolean process(Order order) {
        msgSender.sendMsg(order);
        return true;
    }
}
