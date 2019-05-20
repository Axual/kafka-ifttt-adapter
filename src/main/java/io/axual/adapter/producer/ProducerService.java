package io.axual.adapter.producer;

import io.axual.adapter.events.IFTTTEvent;
import io.axual.adapter.kafka.TopicResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class ProducerService {

    private KafkaTemplate<String, Object> template;

    private TopicResolver topicResolver;

    @Autowired
    public ProducerService(KafkaTemplate<String, Object> template, TopicResolver topicResolver) {
        this.template = template;
        this.topicResolver = topicResolver;
    }

    public void sendMessage(IFTTTEvent event) {
        try {
            log.info("A message from \"" + event.getService() + "\" device will be sent to kafka: \"" + event.getMessage() + "\"");
            this.template.send(topicResolver.getTopicForService(event.getService()), event).get();
        } catch (InterruptedException | ExecutionException e) {
            log.warn("Failed to send message", e);
        }
    }
}
