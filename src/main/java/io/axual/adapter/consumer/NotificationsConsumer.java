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
public class NotificationsConsumer {

    @Value("${googleHome.url}")
    private String googleHomeUrl;

    @KafkaListener(topics = "#{'${cluster.topic.notifications.name}'}", autoStartup = "${cluster.consumer.enabled}")
    public void consume(@Payload IFTTTEvent event) throws UnirestException {
        if (event == null) {
            log.warn("Found null message");
            return;
        }
        log.info("A notification will be sent to Google Home: \"" + event.getMessage() + "\" produce by: \"" + event.getService() + "\"");

        Unirest.post(googleHomeUrl + "/google-home")
                .field("service", event.getService())
                .field("message", event.getMessage())
                .asJson();
    }
}
