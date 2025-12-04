package com.brihaspathee.sapphire.integration.base;

import org.testcontainers.containers.Neo4jContainer;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 04, December 2025
 * Time: 04:46
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.integration.base
 * To change this template use File | Settings | File and Code Template
 */
public class TestNeo4jContainer {

    private static final Neo4jContainer<?> container =
            new Neo4jContainer<>("neo4j:5.11")
                    .withAdminPassword("password")
                    .withEnv("NEO4JLABS_PLUGINS", "apoc")
                    .withEnv("NEO4J_AUTH", "neo4j/password")
                    .withEnv("NEO4J_apoc_export_file_enabled", "true")
                    .withExposedPorts(7687);

    static {
        container.start();
    }

    public static Neo4jContainer<?> getInstance() {
        return container;
    }
}
