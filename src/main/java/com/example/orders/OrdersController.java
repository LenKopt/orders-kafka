package com.example.orders;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController("/api/v1/orders")
public class OrdersController {
    public final OrderService orderService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UUID createNewOrder(@RequestBody OrderDto orderDto) {
        return orderService.addProductionPlan(orderDto);
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<OrderEntity> getOrders() {
        return orderService.getOrders();
    }
}
