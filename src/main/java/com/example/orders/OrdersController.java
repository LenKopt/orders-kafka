package com.example.orders;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/orders")
public class OrdersController {
    private final OrdersService ordersService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/send-order")
    public void createNewOrder(@RequestBody String order) throws JsonProcessingException {

        ordersService.addOrder(order);

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ConcurrentLinkedQueue<String> getOrders() {
        return ordersService.getMessages();
    }
}
