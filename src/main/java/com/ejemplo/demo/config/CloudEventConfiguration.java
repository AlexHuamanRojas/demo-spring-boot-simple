package com.ejemplo.demo.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.cloudevents.transformer.FromCloudEventTransformer;

@Configuration
public class CloudEventConfiguration {

    @Bean
    public FromCloudEventTransformer fromCloudEventTransformer() {
        return new FromCloudEventTransformer();
    }
}