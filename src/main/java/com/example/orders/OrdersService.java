package com.example.orders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;

@AllArgsConstructor
@Service
public class OrdersService {
    private final ConcurrentLinkedQueue<String> messages = new ConcurrentLinkedQueue<>();
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;

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

    @Transactional
    public void addOrder(String order) throws JsonProcessingException {
        OrderEntity orderEntity = new OrderEntity(order, null, null);
        orderRepository.save(orderEntity);

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Adres serwera Kafka
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        String topic = "orders";

        SendMessage sendMessage = new SendMessage(orderEntity.getOrderTechId().toString(), orderEntity.getText());
        String jsonSendMessage = objectMapper.writeValueAsString(sendMessage);

        ProducerRecord<String, String> record = new ProducerRecord<>(topic, jsonSendMessage);
        producer.send(record);

        producer.close();
    }
}
