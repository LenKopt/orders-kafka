package com.example.orders;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/orders")
public class OrdersController {
    private final OrdersService ordersService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/send-order")
    public void createNewOrder(@RequestBody String order) {

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Adres serwera Kafka
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        String topic = "orders";

        String userInput = order;

        ProducerRecord<String, String> record = new ProducerRecord<>(topic, userInput);
        producer.send(record);

        producer.close();

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ConcurrentLinkedQueue<String> getOrders() {
        return ordersService.getMessages();
    }
}
