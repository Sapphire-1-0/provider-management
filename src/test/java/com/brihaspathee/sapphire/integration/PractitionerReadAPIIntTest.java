package com.brihaspathee.sapphire.integration;

import com.brihaspathee.sapphire.integration.base.Neo4jIntegrationTest;
import com.brihaspathee.sapphire.model.*;
import com.brihaspathee.sapphire.model.web.PractitionerSearchRequest;
import com.brihaspathee.sapphire.util.TestUtils;
import com.brihaspathee.sapphire.validator.OrganizationValidator;
import com.brihaspathee.sapphire.validator.PractitionerValidator;
import com.brihaspathee.sapphire.web.model.TestOrganizationSearchRequest;
import com.brihaspathee.sapphire.web.model.TestPractitionerSearchRequest;
import com.brihaspathee.sapphire.web.response.SapphireAPIResponse;
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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
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
public class PractitionerReadAPIIntTest {

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
    @Value("classpath:com/brihaspathee/sapphire/integration/practitioner/read/PractitionerReadAPIIntTest.json")
    Resource resourceFile;


    /**
     * Represents a test class configuration for {@link TestPractitionerSearchRequest} objects.
     * This variable is an instance of {@link TestClass} that encapsulates the details of a
     * test class containing methods and metadata specific to testing practitioner search requests.
     *
     * It allows the configuration and validation of different test methods associated
     * with {@link TestPractitionerSearchRequest}, ensuring comprehensive testing scenarios
     * for practitioner search operations.
     */
    private TestClass<TestPractitionerSearchRequest> practitionerSearchRequestTestClass;


    /**
     * An autowired instance of the {@link BuildTestData} utility class, parameterized for
     * {@link TestPractitionerSearchRequest}, used to construct test data specific to
     * test cases in the PractitionerReadAPI integration tests.
     * <p>
     * This variable is responsible for dynamically generating or retrieving the necessary
     * test data objects based on the invoked test method during integration testing.
     * It leverages method-specific configurations to provide tailored test data that can
     * validate the functionality of the test methods.
     */
    @Autowired
    private BuildTestData<TestPractitionerSearchRequest> buildTestData;

    /**
     * Represents a list of requests for testing practitioner search functionality.
     * Each element within the list is an instance of {@link TestPractitionerSearchRequest}.
     * This variable is utilized to store the test data needed for executing multiple
     * variations of practitioner search test cases. It provides access to the necessary
     * inputs and expected results required for validating the search functionality.
     */
    private List<TestPractitionerSearchRequest> requests = new ArrayList<>();

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
        practitionerSearchRequestTestClass = objectMapper.readValue(resourceFile.getFile(), new TypeReference<>() {
        });

        // Build the test data for the test method that is to be executed
        this.requests = buildTestData.buildData(testInfo.getTestMethod().get().getName(),this.practitionerSearchRequestTestClass);
    }

    @RepeatedTest(value = 1, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @Order(1)
    void testGetPractitioners(RepetitionInfo repetitionInfo){
        log.info("Current Repetition:{}",repetitionInfo.getCurrentRepetition());
        TestPractitionerSearchRequest testPractitionerSearchRequest =
                requests.get(repetitionInfo.getCurrentRepetition() - 1);
        log.info("Test Practitioner Search Request:{}",testPractitionerSearchRequest);
        PractitionerSearchRequest practitionerSearchRequest = testPractitionerSearchRequest.getPracSearchRequest();
        String uri = "/api/v1/sapphire/practitioner/_search";
        SapphireAPIResponse<PractitionerList> apiResponse = webTestClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(practitionerSearchRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<SapphireAPIResponse<PractitionerList>>(){})
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(apiResponse);
        PractitionerList practitionerList = objectMapper.convertValue(apiResponse.getResponse(), PractitionerList.class);
        log.info("Practitioner List:{}",practitionerList);
    }

    @RepeatedTest(value = 1, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @Order(1)
    void testGetPractitionerById(RepetitionInfo repetitionInfo){
        log.info("Current Repetition:{}", repetitionInfo.getCurrentRepetition());
        TestPractitionerSearchRequest testPractitionerSearchRequest = requests.get(repetitionInfo.getCurrentRepetition() - 1);
        log.info("Test Practitioner Search Request:{}", testPractitionerSearchRequest);
        log.info("Practitioner Search Request:{}", testPractitionerSearchRequest.getPracSearchRequest());
        String practitionerCode = testPractitionerSearchRequest.getPracCode();
        PractitionerDto actualPractitionerDto = TestUtils.getPractitionerByCode(webTestClient, objectMapper, practitionerCode);
        PractitionerList expectedPractitionerList = testPractitionerSearchRequest.getExpectedPractitionerList();
        log.info("Actual Practitioner Dto:{}", actualPractitionerDto);
        log.info("Expected Practitioner List:{}", expectedPractitionerList);
        if (actualPractitionerDto.getIdentifiers()!=null && !actualPractitionerDto.getIdentifiers().isEmpty()) {
            for (IdentifierDto identifierDto : actualPractitionerDto.getIdentifiers()) {
                log.info("IdentifierDto:{}", identifierDto);
            }
        }
        PractitionerValidator.assertPractitionerList(PractitionerList.builder()
                        .practitioners(List.of(actualPractitionerDto))
                        .build(),
                expectedPractitionerList);
    }
}
