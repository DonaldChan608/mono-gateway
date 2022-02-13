package com.donald.gateway.filter;

import com.donald.gateway.config.FilterConfig;
import com.donald.gateway.config.StorageConfig;
import com.donald.gateway.sotrage.OrderQueue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {FilterConfig.class, StorageConfig.class})
public class CancelOrderFilterTest {

    @Autowired
    private OrderQueue orderQueue;

    @Autowired
    private CancelOrderFilter cancelOrderFilter;

    @Test
    public void testFilter() {
//        cancelOrderFilter.
    }

}
