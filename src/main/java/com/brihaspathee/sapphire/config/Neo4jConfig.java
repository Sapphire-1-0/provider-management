package com.brihaspathee.sapphire.config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 17, October 2025
 * Time: 14:12
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.config
 * To change this template use File | Settings | File and Code Template
 */
@Configuration
public class Neo4jConfig {

    @Value( "${neo4j.uri}")
    private String neo4jUri;

    @Value( "${neo4j.username}")
    private String neo4jUsername;

    @Value( "${neo4j.password}")
    private String neo4jPassword;

    @Value( "${neo4j.database}")
    private String database;

    @Bean
    public Driver neo4jDriver() {
        return GraphDatabase.driver(neo4jUri, AuthTokens.basic(neo4jUsername, neo4jPassword));
    }

    @Bean
    public String neo4jDatabase() {
        return database;
    }


}
