package com.brihaspathee.sapphire.config;

import com.brihaspathee.sapphire.utils.CypherLoader;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 17, October 2025
 * Time: 15:37
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.config
 * To change this template use File | Settings | File and Code Template
 */
@Configuration
@RequiredArgsConstructor
public class CypherPreLoadConfig {

    private final CypherLoader cypherLoader;

    @PostConstruct
    public void preloadCypher() {
        cypherLoader.preload();
    }
}
