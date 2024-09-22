package com.example.warehouse;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class WarehouseConsumer {
    public static void main(String[] args) {
        // Create Kafka consumer configuration
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");  // Kafka broker address
        props.put("group.id", "warehaus-group");  // Consumer group ID
        props.put("enable.auto.commit", "true");  // Automatically commit offsets
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        // Set auto_offset_reset to 'latest' so that we consume only new messages
        props.put("auto.offset.reset", "latest");

        // Create the Kafka consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // Subscribe to the topic
        consumer.subscribe(Collections.singletonList("orders"));

        // Continuously poll the topic for new messages
        try {
            while (true) {
                // Poll messages with a timeout of 1 second
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));

                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("Consumed message: key = %s, value = %s, partition = %d, offset = %d%n",
                            record.key(), record.value(), record.partition(), record.offset());
                    sendAnswertToProducer(record.value());
                }
            }
        } finally {
            consumer.close();  // Ensure consumer is closed on exit
        }

    }
    private static void sendAnswertToProducer(String recordValue) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Adres serwera Kafka
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        String topic = "orders-confirmation";

        String userInput = "Zamówienie " + recordValue + " otrzymano przez Warehause";

        ProducerRecord<String, String> record = new ProducerRecord<>(topic, userInput);
        producer.send(record);

        System.out.println("Wysłano wiadomość do Kafka: " + userInput);

        producer.close();
        System.out.println("Aplikacja zakończyła działanie.");
    }
}
