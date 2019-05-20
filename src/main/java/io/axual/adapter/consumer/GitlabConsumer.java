package io.axual.adapter.consumer;


import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.axual.adapter.events.IFTTTEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GitlabConsumer {

    @Value("${cluster.gitlab.token}")
    private String gitlabToken;

    @Value("${cluster.gitlab.refName}")
    private String refName;

    @Value("${cluster.gitlab.projectId}")
    private String projectId;

    @KafkaListener(topics = "#{'${cluster.topic.gitlab.name}'}", autoStartup = "${cluster.consumer.enabled}")
    public void consume(@Payload IFTTTEvent event) throws UnirestException {
        if (event == null) {
            log.warn("Found null message");
            return;
        }
        log.info("I'm going to trigger the pipeline");
        Unirest.post("https://gitlab.com/api/v4/projects/" + projectId + "/ref/" + refName + "/trigger/pipeline")
                .queryString("token", gitlabToken)
                .asString();
    }
}
