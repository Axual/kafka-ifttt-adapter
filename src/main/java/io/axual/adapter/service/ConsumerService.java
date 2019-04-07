package io.axual.adapter.service;

import io.axual.adapter.events.IFTTTEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConsumerService {

    @Value("${cluster.topic.name}")
    private String topicName;


    @KafkaListener(topics = "#{'${cluster.topic.name}'}", autoStartup = "${cluster.consumer.enabled}")
    public void consume(@Payload IFTTTEvent event) {
        if (event == null) {
            log.warn("Found null message");
            return;
        }
        log.info("Consumed message: " + event.getMessage() + " produce by a " + event.getService());
    }
}
