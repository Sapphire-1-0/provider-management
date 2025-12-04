package com.brihaspathee.sapphire.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 25, November 2025
 * Time: 05:49
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.config
 * To change this template use File | Settings | File and Code Template
 */
@Configuration
public class JacksonConfig {

    /**
     * Provides a configured {@link ObjectMapper} bean for the application context.
     * The returned {@link ObjectMapper} includes support for Java 8 Date/Time API
     * through registration of the {@link com.fasterxml.jackson.datatype.jsr310.JavaTimeModule}
     * and automatically scans for additional modules.
     *
     * @return a configured {@link ObjectMapper} instance with Java 8 Date/Time module support
     */
    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        mapper.findAndRegisterModules();
        return mapper;
    }
}
