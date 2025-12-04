package com.brihaspathee.sapphire.integration.base;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.neo4j.driver.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 25, November 2025
 * Time: 19:21
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.integration.base
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Testcontainers
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class Neo4jIntegrationTest {

//    @Container
//    protected static Neo4jContainer<?> neo4jContainer =
//            new Neo4jContainer<>("neo4j:5.11")
//                    .withAdminPassword("password")
//                    .withEnv("NEO4JLABS_PLUGINS", "apoc")
//                    .withEnv("NEO4J_AUTH", "neo4j/password")
//                    .withEnv("NEO4J_apoc_export_file_enabled", "true")
//                    .withExposedPorts(7687);

    protected static final Neo4jContainer<?> neo4jContainer =
            TestNeo4jContainer.getInstance();

    protected Driver driver;

    private static boolean dataLoaded = false; // <-- prevent multiple loads

    static final String NEO4J_USERNAME = "neo4j";
    static final String NEO4J_PASSWORD = "password";
    static final String NEO4J_DATABASE = "neo4j";

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        // Container will be started automatically before this runs
        registry.add("neo4j.uri", () -> neo4jContainer.getBoltUrl());
        registry.add("neo4j.username", () -> NEO4J_USERNAME);
        registry.add("neo4j.password", () -> NEO4J_PASSWORD);
        registry.add("neo4j.database", () -> NEO4J_DATABASE);
    }

//    /**
//     * Start container manually BEFORE Spring tries to load properties
//     */
//    static {
//        neo4jContainer.start();
//    }

    @BeforeAll
    void setUp() throws Exception {
        driver = GraphDatabase.driver(
                neo4jContainer.getBoltUrl(),
                AuthTokens.basic(NEO4J_USERNAME, NEO4J_PASSWORD)
        );

//        try (Session session = driver.session(SessionConfig.forDatabase("system"))) {
//            var result = session.run("SHOW DATABASES WHERE name = $dbName",
//                    Values.parameters("dbName", NEO4J_DATABASE));
//            if (!result.hasNext()) {
//                log.info("Creating database '{}'", NEO4J_DATABASE);
//                session.executeWrite(tx -> {
//                    tx.run("CREATE DATABASE " + NEO4J_DATABASE);
//                    return null;
//                });
//            } else {
//                log.info("Database '{}' already exists", NEO4J_DATABASE);
//            }
//        }

        waitForDatabaseOnline();
        // load only once for whole JVM test run
        synchronized (Neo4jIntegrationTest.class) {
            if (!dataLoaded) {
                loadTestData();
                dataLoaded = true;
            }
        }
    }

    void loadTestData() throws Exception {
        Path cypherPath = Paths.get("src/test/resources/test-data-dump/test-data.cypher").toAbsolutePath();
        log.info("Test data starting to loaded into '{}'", NEO4J_DATABASE);
        try (BufferedReader reader = Files.newBufferedReader(cypherPath);
             Session session = driver.session(SessionConfig.forDatabase(NEO4J_DATABASE))) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("//") || line.startsWith("#")) continue;

                sb.append(line).append(" ");
                if (line.endsWith(";")) {
                    String stmt = sb.toString().trim();
                    stmt = stmt.substring(0, stmt.length() - 1); // remove ;
                    String finalStmt = stmt;
//                    log.info("Loading test data: {}", stmt);
                    session.executeWrite(tx -> { tx.run(finalStmt); return null; });
                    sb.setLength(0);
                }
            }

            if (sb.length() > 0) {
                String stmt = sb.toString().trim();
                if (!stmt.isEmpty()) session.executeWrite(tx -> { tx.run(stmt); return null; });
            }
        }
        log.info("Test data loaded into '{}'", NEO4J_DATABASE);
    }

    private void waitForDatabaseOnline() throws Exception {
        int retries = 10;
        int sleepMs = 2000;
        for (int i = 0; i < retries; i++) {
            try (Session session = driver.session(SessionConfig.forDatabase("neo4j"))) {
                var result = session.run("SHOW DATABASES WHERE name='" + NEO4J_DATABASE + "'");
                var record = result.single();
                String status = record.get("currentStatus").asString();
                if ("online".equalsIgnoreCase(status)) return;
            } catch (Exception ignored) {}
            Thread.sleep(sleepMs);
        }
        throw new RuntimeException("Database '" + NEO4J_DATABASE + "' did not become ONLINE in time");
    }
}
