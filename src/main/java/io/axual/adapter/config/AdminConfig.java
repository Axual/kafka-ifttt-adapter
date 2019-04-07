package io.axual.adapter.config;

import lombok.Data;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.*;

import static io.axual.adapter.config.SecurityConfig.createSecurityConfig;

@Configuration
@Data
public class AdminConfig {

    @Value("${cluster.bootstrapServers}")
    private List<String> bootstrapServers = new ArrayList<>(
            Collections.singletonList("localhost:9092"));

    @Value("${cluster.producer.clientId}")
    private String clientId;

    @Value("${cluster.securityProtocol}")
    private String securityProtocol;

    @Value("${cluster.ssl.key-store-location}")
    private String keystoreLocation;

    @Value("${cluster.ssl.key-store-password}")
    private String keystorePassword;

    @Value("${cluster.ssl.key-password}")
    private String keyPassword;

    @Value("${cluster.ssl.trust-store-location}")
    private String truststoreLocation;

    @Value("${cluster.ssl.trust-store-password}")
    private String truststorePassword;

    @Value("${cluster.autoCreateTopic}")
    private boolean autoCreateTopic;

    @Value("${cluster.topic.name}")
    private String topicName;

    @Value("${cluster.topic.minIsr}")
    private String topicMinIsr;

    @Value("${cluster.topic.partitions}")
    private String topicPartitions;

    @Value("${cluster.topic.replicationFactor}")
    private String topicReplicationFactor;

    @Value("${cluster.topic.retentionMs}")
    private String topicRetentionMs;

    @Value("${cluster.topic.segmentMs}")
    private String topicSegmentMs;

    @Bean
    @ConditionalOnProperty(value = "config.autoCreateTopic", havingValue = "true")
    public KafkaAdmin admin() {
        Map<String, Object> props = new HashMap<>();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        props.put(AdminClientConfig.CLIENT_ID_CONFIG, this.clientId);
        props.putAll(createSecurityConfig(this.securityProtocol, this.keyPassword, this.keystoreLocation,
                this.keystorePassword, this.truststoreLocation, this.truststorePassword));
        KafkaAdmin kafkaAdmin = new KafkaAdmin(props);
        kafkaAdmin.setAutoCreate(autoCreateTopic);
        return kafkaAdmin;
    }

    @Bean
    @ConditionalOnProperty(value = "config.autoCreateTopic", havingValue = "true")
    public NewTopic topic() {
        Map<String, String> topicConfigs = new HashMap<>();
        topicConfigs.put(TopicConfig.MIN_IN_SYNC_REPLICAS_CONFIG, topicMinIsr);
        topicConfigs.put(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE);
        topicConfigs.put(TopicConfig.RETENTION_MS_CONFIG, topicRetentionMs);
        topicConfigs.put(TopicConfig.SEGMENT_MS_CONFIG, topicSegmentMs);

        return new NewTopic(topicName, Integer.valueOf(topicPartitions), Short.valueOf(topicReplicationFactor))
                .configs(topicConfigs);
    }
}
