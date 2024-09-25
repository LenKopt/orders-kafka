package com.example.orders;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderTechId;
    private String text;
    private String statusWarehouse;
    private String statusFinance;
//    @Version
//    private Integer version;

    public OrderEntity(String text, String statusWarehouse, String statusFinance) {
        this.orderTechId = UUID.randomUUID().toString();
        this.text = text;
        this.statusWarehouse = statusWarehouse;
        this.statusFinance = statusFinance;
        //this.version = 0;
    }

    public void setStatusWarehouse(String statusWarehouse) {
        this.statusWarehouse = statusWarehouse;
    }

    public void setStatusFinance(String statusFinance) {
        this.statusFinance = statusFinance;
    }
}
