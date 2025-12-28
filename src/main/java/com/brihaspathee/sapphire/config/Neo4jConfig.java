package com.brihaspathee.sapphire.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Configuration
public class Neo4jConfig {

    /**
     * Represents the URI used to connect to the Neo4j database.
     * This value is injected from the property `neo4j.uri` in the application configuration.
     */
    @Value( "${neo4j.uri}")
    private String neo4jUri;

    /**
     * Represents the username required to authenticate with the Neo4j database.
     * This value is injected from the property `neo4j.username` in the application configuration.
     */
    @Value( "${neo4j.username}")
    private String neo4jUsername;

    /**
     * Represents the password required to authenticate with the Neo4j database.
     * This value is injected from the property `neo4j.password` in the application configuration.
     */
    @Value( "${neo4j.password}")
    private String neo4jPassword;

    /**
     * Represents the name of the Neo4j database configured through the application properties.
     * This value is injected from the property `neo4j.database` and determines which database
     * within the Neo4j instance is to be used for operations.
     */
    @Value( "${neo4j.database}")
    private String database;

    /**
     * Creates and provides a singleton instance of the Neo4j {@link Driver}.
     * The driver is configured using the connection URI, username, and password
     * specified in the application configuration.
     *
     * @return a configured {@link Driver} instance for connecting to the Neo4j database
     */
    @Bean
    public Driver neo4jDriver() {
        return GraphDatabase.driver(neo4jUri, AuthTokens.basic(neo4jUsername, neo4jPassword));
    }

    /**
     * Provides the name of the Neo4j database as specified in the application configuration.
     *
     * @return the name of the configured Neo4j database
     */
    @Bean
    public String neo4jDatabase() {
        return database;
    }

    @PostConstruct
    public void logDatabaseName() {
        log.info("Connecting to Neo4j database: {}", database);
    }


}
