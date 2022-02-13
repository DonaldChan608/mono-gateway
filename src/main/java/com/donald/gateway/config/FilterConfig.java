package com.donald.gateway.config;

import com.donald.gateway.filter.Filter;
import com.donald.gateway.filter.PreviousPriceFilter;
import com.donald.gateway.filter.CancelOrderFilter;
import com.donald.gateway.filter.SendMsgFilter;
import com.donald.gateway.processor.OrderProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

@Import(StorageConfig.class)
public class FilterConfig {

    @Autowired
    private PreviousPriceFilter previousPriceFilter;

    @Autowired
    private CancelOrderFilter cancelOrderFilter;

    @Autowired
    private SendMsgFilter sendMsgFilter;

    @Bean("orderProcessor")
    public OrderProcessor orderProcessor() {
        return new OrderProcessor();
    }

    @Bean("previousPriceFilter")
    public PreviousPriceFilter previousPriceFilter() {
        return new PreviousPriceFilter();
    }

    @Bean("cancelOrderFilter")
    public CancelOrderFilter cancelOrderFilter() {
        return new CancelOrderFilter();
    }

    @Bean("sendMsgFilter")
    public SendMsgFilter sendMsgFilter() {
        return new SendMsgFilter();
    }

    @Bean("orderFilters")
    public List<Filter> orderFilters() {
        List<Filter> filters = new ArrayList<>();
        filters.add(cancelOrderFilter);
        filters.add(previousPriceFilter);
        filters.add(sendMsgFilter);
        return filters;
    }


}
