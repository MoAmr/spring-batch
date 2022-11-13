package com.springbatch.processors;

import com.springbatch.models.TrackedOrder;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;

public class FreeShippingItemProcessor implements ItemProcessor<TrackedOrder, TrackedOrder> {

    @Override
    public TrackedOrder process(TrackedOrder trackedOrder) throws Exception {
        if (trackedOrder.getCost().compareTo(new BigDecimal("80")) == 1) {
            trackedOrder.setFreeShipping(true);
        } else {
            trackedOrder.setFreeShipping(false);
        }

        return trackedOrder.isFreeShipping() ? trackedOrder : null;
    }
}
