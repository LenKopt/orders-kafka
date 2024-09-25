package com.example.orders;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class OrdersService {
    private final ConcurrentLinkedQueue<String> messages = new ConcurrentLinkedQueue<>();

    public void addMessage(String message) {
        messages.add(message);
    }

    public ConcurrentLinkedQueue<String> getMessages() {
        return messages;
    }

    @KafkaListener(topics = "orders", groupId = "my-group")
    public void consume(ConsumerRecord<String, String> record) {
        System.out.println("Zamówienie: " + record.value() + " zostało wysłane");
    }
}
