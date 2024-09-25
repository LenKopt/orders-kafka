package com.example.orders;

import com.example.finance.MessageFromProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component

public class KafkaConsumer {
    @Autowired
    private OrdersService ordersService;
    private final ObjectMapper objectMapper;
    private final OrderRepository orderRepository;

    public KafkaConsumer(OrdersService ordersService, ObjectMapper objectMapper, OrderRepository orderRepository) {
        this.ordersService = ordersService;
        this.objectMapper = objectMapper;
        this.orderRepository = orderRepository;
    }

    //@Transactional
    @KafkaListener(topics = "orders-confirmation", groupId = "my-group")
    public void consume(ConsumerRecord<String, String> record) throws JsonProcessingException {
        //MessageFromProducer message = objectMapper.readValue(record.value(), MessageFromProducer.class);
        System.out.println("Received message: " + record.value());
        AnswerFromConsumer answerFromConsumer = objectMapper.readValue(record.value(), AnswerFromConsumer.class);
        editEntity(answerFromConsumer);
        ordersService.addMessage(answerFromConsumer.getName());
    }

    @Transactional
    private void editEntity(AnswerFromConsumer answerFromConsumer) {
        OrderEntity byOrderTechId = orderRepository.findByOrderTechId(answerFromConsumer.getId());

        if (answerFromConsumer.getName().equalsIgnoreCase("finance")) {
            byOrderTechId.setStatusFinance("Received");
        } else if (answerFromConsumer.getName().equalsIgnoreCase("warehouse")) {
            byOrderTechId.setStatusWarehouse("Received");
        }
        orderRepository.save(byOrderTechId);
    }
}
