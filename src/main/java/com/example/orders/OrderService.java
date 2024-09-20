package com.example.orders;

import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    UUID addProductionPlan(OrderDto orderDto);

    List<OrderEntity> getOrders();
}
