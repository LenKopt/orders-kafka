package com.example.orders;

import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class OrderDto {
    private String status;
    private String name;
}
