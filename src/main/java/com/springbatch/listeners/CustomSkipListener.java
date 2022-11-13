package com.springbatch.listeners;

import com.springbatch.models.Order;
import com.springbatch.models.TrackedOrder;
import org.springframework.batch.core.SkipListener;

public class CustomSkipListener implements SkipListener<Order, TrackedOrder> {

    @Override
    public void onSkipInRead(Throwable throwable) {

    }

    @Override
    public void onSkipInWrite(TrackedOrder trackedOrder, Throwable throwable) {

    }

    @Override
    public void onSkipInProcess(Order order, Throwable throwable) {
        System.out.println("Skipping processing an item with an id: " + order.getOrderId());
    }
}
