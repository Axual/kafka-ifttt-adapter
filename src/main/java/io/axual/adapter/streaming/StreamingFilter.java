package io.axual.adapter.streaming;

import io.axual.adapter.events.IFTTTEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class StreamingFilter {

    private final KafkaTemplate<String, Object> template;

    private static final String keyword = "pipeline";

    @Value("${cluster.topic.gitlab.name}")
    private String gitlabTopic;

    @Autowired
    public StreamingFilter(KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    @KafkaListener(topics = "#{'${cluster.topic.ifttt.name}'}", autoStartup = "${cluster.consumer.enabled}")
    public void consume(@Payload IFTTTEvent event) {
        log.info("Consumed message: \"" + event.getMessage() + "\" produce by: \"" + event.getService() + "\"");
        if(event.getMessage().contains(keyword)) {
            try {
                log.info("Forwarding message which contains keyword \"" + keyword + "\"");
                this.template.send(gitlabTopic, event).get();
            } catch (InterruptedException | ExecutionException e) {
                log.warn("Failed to send message", e);
            }
        }
    }
}
