package com.example.orders;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private UUID orderId;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String name;

    public OrderEntity(String status, String name) {
        this.orderId = UUID.randomUUID();
        this.status = status;
        this.name = name;
    }
}
