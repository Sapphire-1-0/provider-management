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
import java.util.List;

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

    /**
     * A statically initialized instance of the Neo4j test container used for integration testing.
     * The container is configured with environment variables, including authentication credentials
     * and plugin settings, to support test scenarios. This container ensures that a Neo4j database
     * instance is available and ready during the execution of tests.
     * <p>
     * The `TestNeo4jContainer` class manages the lifecycle of the container, starting it upon class
     * loading and providing a singleton instance that can be accessed by the test setup. This allows
     * consistent database configurations and reusable test environment setups.
     */
    protected static final Neo4jContainer<?> neo4jContainer =
            TestNeo4jContainer.getInstance();

    /**
     * The Neo4j database driver used to establish and manage connections to the database during integration tests.
     *
     * This driver is initialized with database connection details (such as URI, username, password, etc.) provided by
     * the Neo4j test container. It enables executing Cypher queries, loading test data, validating database state,
     * and other operations required for integration testing.
     *
     * The driver is configured once during the test environment setup and is reused across various test methods.
     * Proper management of the driver ensures efficient connections to the database and prevents resource leakage.
     */
    protected Driver driver;

    /**
     * Indicates whether the test data has already been loaded into the Neo4j database.
     * This flag prevents multiple loading of test data across the JVM session during the
     * execution of integration tests, ensuring that test data is only loaded once to improve
     * performance and avoid redundant operations.
     */
    private static boolean dataLoaded = false; // <-- prevent multiple loads

    /**
     * Represents the username used for authenticating with the Neo4j database.
     * This username is utilized in the integration test environment to establish
     * a connection to the Neo4j database instance.
     * <p>
     * The value is statically defined as "neo4j," which is the default administrative
     * username for a Neo4j database. It is primarily used in the context of setting
     * up test configurations or integrations where this default credential is sufficient.
     */
    static final String NEO4J_USERNAME = "neo4j";

    /**
     * The password used for authenticating with the Neo4j database during integration tests.
     * This constant holds the value configured for the Neo4j test container and is used
     * by integration test classes to establish a secure connection with the database.
     *
     * It is intended for use in a test environment and is not recommended for use
     * in production due to hardcoded credentials.
     */
    static final String NEO4J_PASSWORD = "password";

    /**
     * Represents the name of the Neo4j database used in the integration tests.
     * This constant is utilized for configuring and referencing the target database
     * within test setups and operations.
     */
    static final String NEO4J_DATABASE = "neo4j";

    /**
     * Configures Neo4j properties dynamically for the integration test environment.
     * This method registers the connection details (such as URI, username, password, and database name)
     * from the Neo4j test container to the given dynamic property registry, ensuring that these properties
     * are available during the test runtime.
     *
     * @param registry the DynamicPropertyRegistry used to register dynamic property values
     */
    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        // Container will be started automatically before this runs
        registry.add("neo4j.uri", () -> neo4jContainer.getBoltUrl());
        registry.add("neo4j.username", () -> NEO4J_USERNAME);
        registry.add("neo4j.password", () -> NEO4J_PASSWORD);
        registry.add("neo4j.database", () -> NEO4J_DATABASE);
    }


    /**
     * Sets up the Neo4j test environment for integration tests.
     * <p>
     * This method initializes the Neo4j driver using the Bolt URL from the test container
     * and authenticates with the provided credentials. It ensures the database is online
     * before performing operations. Additionally, it loads test data for the integration
     * tests by executing a list of predefined Cypher scripts. The test data is loaded
     * only once per JVM test run to optimize performance.
     *
     * @throws Exception if there is an error initializing the driver, ensuring the database is online,
     *                   or loading the test data
     */
    @BeforeAll
    void setUp() throws Exception {
        driver = GraphDatabase.driver(
                neo4jContainer.getBoltUrl(),
                AuthTokens.basic(NEO4J_USERNAME, NEO4J_PASSWORD)
        );

        waitForDatabaseOnline();
        // load only once for whole JVM test run
        synchronized (Neo4jIntegrationTest.class) {
            if (!dataLoaded) {
                List<Path> cypherPaths = List.of(
                        Paths.get("src/test/resources/test-data-dump/case-1/00_CA_Networks.cypher"),
                        Paths.get("src/test/resources/test-data-dump/case-1/org_1/parent_ppg/01_river_valley.cypher"),
                        Paths.get("src/test/resources/test-data-dump/case-1/org_1/child_ppg_1/0101_golden_state_health_alliance.cypher"),
                        Paths.get("src/test/resources/test-data-dump/case-1/org_1/child_ppg_2/0102_california_wellness_group.cypher"),
                        Paths.get("src/test/resources/test-data-dump/case-1/org_1/child_ppg_3/0103_golden_state_health_group.cypher"),
                        Paths.get("src/test/resources/test-data-dump/case-1/org_1/parent_ppg/prac_1/01_prac.cypher")
                );
//                List<Path> cypherPaths = List.of(
//                        Paths.get("src/test/resources/test-data-dump/case-1/00_CA_Networks.cypher"),
//                        Paths.get("src/test/resources/test-data-dump/case-1/org_1/parent_ppg/01_river_valley.cypher")
//                );
                loadTestData(cypherPaths);
                dataLoaded = true;
            }
        }
    }

    /**
     * Loads test data into a Neo4j database using a list of Cypher script file paths.
     * This method iterates over the provided list of Cypher file paths and invokes the
     * individual file loading method for each path.
     *
     * @param cypherPaths a list of paths to the Cypher script files containing database commands
     * @throws Exception if an error occurs while loading test data from any of the files
     */
    void loadTestData(List<Path> cypherPaths) throws Exception {
        for (Path cypherPath : cypherPaths) {
            loadTestData(cypherPath);
        }
    }

    /**
     * Loads test data into a Neo4j database using a Cypher script file.
     * The method reads Cypher commands from the provided file, processes them,
     * and executes the commands in the database while ignoring comments and empty lines.
     * It validates the test data after the load to ensure nodes exist in the database.
     *
     * @param cypherPath the path to the Cypher script file containing database commands
     * @throws Exception if an error occurs while reading the file, executing the Cypher commands,
     *                   or validating the test data
     */
    void loadTestData(Path cypherPath) throws Exception {
//        Path cypherPath = Paths.get("src/test/resources/test-data-dump/test-data.cypher").toAbsolutePath();
        log.info("Test data starting load data from {} into '{}'", cypherPath.getFileName().toString(), NEO4J_DATABASE);
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
            validateTestDataLoaded(session);
        }
        log.info("Test data from {} loaded into '{}'", cypherPath.getFileName().toString(), NEO4J_DATABASE);
    }


    /**
     * Waits for the specified Neo4j database to become online.
     * <p>
     * This method repeatedly checks the status of the database by querying the "SHOW DATABASES" command
     * with a configured number of retries and delays between each retry. If the database transitions
     * to the "online" status within the allowed retries, the method exits successfully. If the
     * database fails to reach the "online" status within the allotted time, an exception is thrown.
     *
     * @throws Exception if the database does not become online within the specified time or if an error occurs
     *                   while querying the database status
     */
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

    /**
     * Validates that the test data has been successfully loaded into the Neo4j database.
     * This method checks the existence of at least one node in the database and throws
     * an exception if the database is empty.
     *
     * @param session the active Neo4j session used to execute the validation query
     * @throws IllegalStateException if no nodes are found in the database, indicating
     *                               that the test data failed to load
     */
    private void validateTestDataLoaded(Session session) {
        // Example: ensure at least 1 node exists
        Long count = session.executeRead(tx ->
                tx.run("MATCH (n) RETURN count(n) AS c")
                        .single()
                        .get("c")
                        .asLong()
        );
        if (count == 0) {
            throw new IllegalStateException("Test data failed to load — database is empty!");
        }

        log.info("Validation passed: {} nodes found in test database", count);
    }
}
