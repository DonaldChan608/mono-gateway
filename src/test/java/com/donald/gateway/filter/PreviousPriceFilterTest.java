package com.donald.gateway.filter;

import com.donald.gateway.config.FilterConfig;
import com.donald.gateway.config.StorageConfig;
import com.donald.gateway.pojo.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {FilterConfig.class, StorageConfig.class})
public class PreviousPriceFilterTest {


    @Autowired
    private PreviousPriceFilter previousPriceFilter;

    @Test
    public void test() {
        previousPriceFilter.process(new Order((short) 21, 'D', 100, (short) 10, 10));
        boolean result = previousPriceFilter.process(new Order((short) 21, 'D', 100, (short) 10, 11));
        Assertions.assertTrue(result);
    }


}
