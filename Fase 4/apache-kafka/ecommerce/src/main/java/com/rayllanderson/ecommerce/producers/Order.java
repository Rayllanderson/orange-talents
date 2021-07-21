package com.rayllanderson.ecommerce.producers;

import java.math.BigDecimal;

public class Order {

    private final String userId;
    private final String orderId;
    private final BigDecimal price;

    public Order(String userId, String orderId, BigDecimal price) {
        this.userId = userId;
        this.orderId = orderId;
        this.price = price;
    }
}
