package io.axual.adapter.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TopicResolver {

    private Map<String, String> topicMap = new HashMap<>();

    @Autowired
    public TopicResolver(Topics topics) {
        topicMap.put("ifttt", topics.getIfttt());
        topicMap.put("notifications", topics.getNotifications());
        topicMap.put("gitlab", topics.getGitlab());
    }

    public String getTopicForService(String service) {
        return topicMap.get(service);
    }
}
