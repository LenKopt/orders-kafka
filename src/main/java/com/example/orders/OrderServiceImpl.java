package com.example.orders;

import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.util.List;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {
    public final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public UUID addProductionPlan(OrderDto orderDto) {
        OrderEntity orderEntity = new OrderEntity(orderDto.getName(), orderDto.getStatus());
        orderRepository.save(orderEntity);
        return orderEntity.getOrderId();
    }

    @Override
    public List<OrderEntity> getOrders() {
        return orderRepository.findAll();
    }
}
