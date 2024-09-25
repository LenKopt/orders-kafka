package com.example.orders;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findByOrderTechId(String techID);
}
