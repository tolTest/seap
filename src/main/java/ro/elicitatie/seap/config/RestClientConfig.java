package ro.elicitatie.seap.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * Configuration for REST client
 */
@Configuration
public class RestClientConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, ApiConfig apiConfig) {
        return builder
                .setConnectTimeout(Duration.ofMillis(apiConfig.getConnectionTimeout()))
                .setReadTimeout(Duration.ofMillis(apiConfig.getReadTimeout()))
                .build();
    }
}
