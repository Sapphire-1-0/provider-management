package com.brihaspathee.sapphire.integration;

import com.brihaspathee.sapphire.integration.base.Neo4jIntegrationTest;
import com.brihaspathee.sapphire.web.model.TestOrganizationSearchRequest;
import com.brihaspathee.test.BuildTestData;
import com.brihaspathee.test.TestClass;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 04, December 2025
 * Time: 04:29
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.integration
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PractitionerReadAPIIntTest extends Neo4jIntegrationTest {

    @LocalServerPort
    private int port;


    /**
     * Used to handle JSON serialization and deserialization operations.
     * This instance of {@link ObjectMapper} is provided by Spring's dependency
     * injection mechanism and is typically utilized for converting Java objects
     * to JSON and vice versa. It offers extensive configuration options and
     * customization capabilities to manage the structure and format of JSON data,
     * ensuring seamless integration between application components and JSON-based
     * APIs or resources.
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * A WebTestClient instance used to perform integration testing of web applications.
     * This variable is automatically injected into the test class and allows for
     * testing HTTP endpoints by simulating client requests and validating responses.
     *
     * The WebTestClient is leveraged for executing HTTP methods like GET, POST, PUT, DELETE,
     * and provides a fluent API for performing request constructions, executions,
     * and response validations. It is commonly used for verifying the correctness
     * of controller endpoints in a web layer.
     */
    private WebTestClient webTestClient;

    /**
     * Represents a resource file utilized in the OrganizationReadAPIIntTest.
     * This resource contains JSON data required for testing the functionality
     * of organization-related API integration tests, specifically for reading
     * organization data.
     *
     * The file is located in the classpath at
     * "com/brihaspathee/sapphire/integration/organization/read/OrganizationReadAPIIntTest.json"
     * and is loaded as a Spring-managed resource for use in various test cases.
     * It serves as input data to validate the behavior and correctness of the
     * organization read API during integration tests.
     */
    @Value("classpath:com/brihaspathee/sapphire/integration/organization/read/OrganizationReadAPIIntTest.json")
    Resource resourceFile;

    /**
     * Represents a test class instance for testing organization search requests.
     * This variable is of type {@code TestClass<TestOrganizationSearchRequest>}
     * and manages a collection of test methods along with their related test data.
     * It provides functionality for organizing and executing tests related to
     * the {@code TestOrganizationSearchRequest} model.
     */
    private TestClass<TestOrganizationSearchRequest> organizationSearchRequestTestClass;

    /**
     * An autowired instance of the {@link BuildTestData} class configured to create
     * and manage test data specifically for instances of {@link TestOrganizationSearchRequest}.
     * This variable is utilized to build and provide test data for test scenarios within
     * the {@code OrganizationReadAPIIntTest} class. The test data is constructed dynamically
     * based on the test method and the corresponding test class being executed.
     */
    @Autowired
    private BuildTestData<TestOrganizationSearchRequest> buildTestData;

    /**
     * Represents a list of test organization search requests.
     * This variable is used during integration tests to hold
     * multiple instances of {@link TestOrganizationSearchRequest} objects,
     * allowing for the testing of various scenarios related to
     * organization search operations. Each object in the list contains
     * details such as search criteria, exception expectations,
     * and response validation information.
     */
    private List<TestOrganizationSearchRequest> requests = new ArrayList<>();

    /**
     * Sets up the necessary test data and configurations required for each test method execution.
     * This method initializes the test data based on the test method being executed and reads relevant
     * resource files to build test objects which will be used during test execution.
     *
     * @param testInfo an instance of {@link TestInfo} which provides information about the test method
     *                 currently being executed, such as its name, display name, and tags.
     * @throws IOException if there is an error reading the resource file or processing the test data.
     */
    @BeforeEach
    void setUp(TestInfo testInfo) throws IOException {

        this.webTestClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();

        // Read the file information and convert to test class object
        organizationSearchRequestTestClass = objectMapper.readValue(resourceFile.getFile(), new TypeReference<>() {
        });

        // Build the test data for the test method that is to be executed
        this.requests = buildTestData.buildData(testInfo.getTestMethod().get().getName(),this.organizationSearchRequestTestClass);
    }

    @RepeatedTest(value = 1, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @Order(1)
    void testGetOrganizationById(RepetitionInfo repetitionInfo){
        log.info("Test Organization Search Request:{}",requests.get(repetitionInfo.getCurrentRepetition() - 1));
    }
}
