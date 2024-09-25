package com.example.finance;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class MessageToProducer {
    private String id;
    private String name;
}
