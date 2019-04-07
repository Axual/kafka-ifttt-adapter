package io.axual.adapter.service;

import io.axual.adapter.events.IFTTTEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class ProducerService {

    private final KafkaTemplate<String, Object> template;

    @Value("${cluster.topic.name}")
    private String topicName;

    @Autowired
    public ProducerService(KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    public void sendMessage(IFTTTEvent event) {
        try {
            log.info("A message from a " + event.getService() + " device will be sent to kafka: " + event.getMessage());
            this.template.send(topicName, event).get();
        } catch (InterruptedException | ExecutionException e) {
            log.warn("Failed to send message", e);
        }
    }
}
