package io.axual.adapter.kafka;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class Topics {

    @Value("${cluster.topic.ifttt.name}")
    private String ifttt;

    @Value("${cluster.topic.notifications.name}")
    private String notifications;

    @Value("${cluster.topic.gitlab.name}")
    private String gitlab;
}
