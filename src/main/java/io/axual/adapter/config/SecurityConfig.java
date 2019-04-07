package io.axual.adapter.config;

import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.CommonClientConfigs.SECURITY_PROTOCOL_CONFIG;
import static org.apache.kafka.common.config.SslConfigs.*;

@Configuration
public class SecurityConfig {

    private static final String SSL = "SSL";

    public static Map<String, String> createSecurityConfig(String securityProtocol, String keyPassword, String keystoreLocation, String keystorePassword, String truststoreLocation, String truststorePassword) {
        Map<String, String> securityProps = new HashMap<>();
        if (SSL.equals(securityProtocol)) {
            securityProps.put(SSL_KEYSTORE_LOCATION_CONFIG, keystoreLocation);
            securityProps.put(SSL_KEYSTORE_PASSWORD_CONFIG, keystorePassword);
            securityProps.put(SSL_KEY_PASSWORD_CONFIG, keyPassword);
            securityProps.put(SSL_TRUSTSTORE_LOCATION_CONFIG, truststoreLocation);
            securityProps.put(SSL_TRUSTSTORE_PASSWORD_CONFIG, truststorePassword);
            securityProps.put(SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");
            securityProps.put(SECURITY_PROTOCOL_CONFIG, securityProtocol);
        }
        return securityProps;
    }
}
