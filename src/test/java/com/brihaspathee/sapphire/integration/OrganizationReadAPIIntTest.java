package com.brihaspathee.sapphire.integration;

import com.brihaspathee.sapphire.model.*;
import com.brihaspathee.sapphire.model.web.LocationSearchRequest;
import com.brihaspathee.sapphire.model.web.NetworkSearchRequest;
import com.brihaspathee.sapphire.model.web.OrganizationSearchRequest;
import com.brihaspathee.sapphire.validator.OrganizationValidator;
import com.brihaspathee.sapphire.web.model.TestOrganizationSearchRequest;
import com.brihaspathee.sapphire.web.response.SapphireAPIResponse;
import com.brihaspathee.test.BuildTestData;
import com.brihaspathee.test.TestClass;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11, November 2025
 * Time: 11:39
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.integration
 * To change this template use File | Settings | File and Code Template
 *
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrganizationReadAPIIntTest {

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

    /**
     * Tests the retrieval of organization details based on the provided identifier.
     * This method utilizes a repeated test mechanism to validate the response
     * against the expected results for different test scenarios. It performs
     * operations including logging, invoking the service to fetch the organization list,
     * and validating the actual data with the expected results.
     *
     * @param repetitionInfo provides details about the current repetition of the test,
     *                       including the current iteration and total repetitions.
     */
    @RepeatedTest(value = 1, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @Order(1)
    void testGetOrganizationById(RepetitionInfo repetitionInfo){
        log.info("Current Repetition:{}", repetitionInfo.getCurrentRepetition());
        TestOrganizationSearchRequest testOrganizationSearchRequest = requests.get(repetitionInfo.getCurrentRepetition() - 1);
        log.info("Test Organization Search Request:{}", testOrganizationSearchRequest);
        log.info("Organization Search Request:{}", testOrganizationSearchRequest.getOrgSearchRequest());
        OrganizationList actualOrganizationList = getOrganizationList(testOrganizationSearchRequest);
        OrganizationList expectedOrganizationList = testOrganizationSearchRequest.getExpectedOrganizationList();
        log.info("Actual Organization List:{}", actualOrganizationList);
        log.info("Expected Organization List:{}", expectedOrganizationList);
        OrganizationValidator.assertOrganizationList(actualOrganizationList, expectedOrganizationList);
    }

    /**
     * Tests the retrieval of network details for an organization based on
     * the provided search request. This method simulates a POST request to
     * fetch network information associated with a specific organization and
     * validates the response.
     *
     * @param repetitionInfo an instance of {@link RepetitionInfo} providing
     *                       details about the current repetition of the
     *                       repeated test method, such as the current index
     *                       and total repetitions.
     */
    @RepeatedTest(value = 1, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @Order(2)
    void testGetNetworksForOrg(RepetitionInfo repetitionInfo) throws JsonProcessingException {
        TestOrganizationSearchRequest testOrganizationSearchRequest = requests.get(repetitionInfo.getCurrentRepetition() - 1);
        OrganizationList actualOrganizationList = getOrganizationList(testOrganizationSearchRequest);
        OrganizationDto organizationDto = actualOrganizationList.getOrganizationList().getFirst();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OrganizationSearchRequest> httpEntity = new HttpEntity<>(null, headers);
        String orgElementId = organizationDto.getElementId();
        String uri = "/api/v1/sapphire/organization/" + orgElementId +"/network/_search";
//        ResponseEntity<SapphireAPIResponse<OrganizationDto>> responseEntity =
//                testRestTemplate.exchange(
//                        uri,
//                        HttpMethod.POST,
//                        httpEntity,
//                        new ParameterizedTypeReference<>() {
//                        }
//                );

        SapphireAPIResponse<OrganizationDto> apiResponse = webTestClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.empty(), Void.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<SapphireAPIResponse<OrganizationDto>>(){})
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(apiResponse);
        OrganizationDto actualOrgDto = objectMapper.convertValue(apiResponse.getResponse(), OrganizationDto.class);
        OrganizationDto expectedOrgDto = testOrganizationSearchRequest.getExpectedOrganizationList().getOrganizationList().getFirst();
        OrganizationValidator.assertOrganization(actualOrgDto, expectedOrgDto);
    }

    /**
     * Tests the retrieval of network locations associated with an organization
     * based on the provided search request. This method simulates a POST request
     * to fetch location information linked to a specific organization's network
     * and validates the response.
     *
     * @param repetitionInfo provides details about the current repetition of
     *                       the repeated test method, including the current
     *                       iteration index and total repetitions.
     * @throws JsonProcessingException if there is an error during the processing
     *                                 of JSON data, such as serialization or
     *                                 deserialization issues.
     */
    @RepeatedTest(value = 1, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @Order(3)
    void testGetNetworkLocationsForOrg(RepetitionInfo repetitionInfo) throws JsonProcessingException {
        TestOrganizationSearchRequest testOrganizationSearchRequest = requests.get(repetitionInfo.getCurrentRepetition() - 1);
        OrganizationList actualOrganizationList = getOrganizationList(testOrganizationSearchRequest);
        OrganizationDto organizationDto = actualOrganizationList.getOrganizationList().getFirst();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OrganizationSearchRequest> httpEntity = new HttpEntity<>(null, headers);
        String orgElementId = organizationDto.getElementId();
        String networkCode = testOrganizationSearchRequest.getNetworkCode();
        log.info("Network code is:{}", networkCode);
        NetworkDto networkDto = getNetwork(networkCode);
        log.info("Network is:{}", networkDto);
        String uri = "/api/v1/sapphire/organization/" + orgElementId +"/network/" +
                networkDto.getElementId() +
                "/location/_search";
//        ResponseEntity<SapphireAPIResponse<OrganizationDto>> responseEntity =
//                testRestTemplate.exchange(
//                        uri,
//                        HttpMethod.POST,
//                        httpEntity,
//                        new ParameterizedTypeReference<>() {
//                        }
//                );

        SapphireAPIResponse<OrganizationDto> apiResponse = webTestClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.empty(), Void.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<SapphireAPIResponse<OrganizationDto>>(){})
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(apiResponse);
        OrganizationDto actualOrgDto = objectMapper.convertValue(apiResponse.getResponse(), OrganizationDto.class);
        OrganizationDto expectedOrgDto = testOrganizationSearchRequest.getExpectedOrganizationList().getOrganizationList().getFirst();
        OrganizationValidator.assertOrganization(actualOrgDto, expectedOrgDto);
    }

    /**
     * Tests the retrieval of location details for an organization based on the
     * provided identifier in the search request. This method simulates a POST request to
     * fetch location information linked to a specific organization and validates the response
     * against the expected data.
     *
     * @param repetitionInfo provides details about the current repetition of the repeated test method,
     *                       including the current iteration index and total repetitions.
     * @throws JsonProcessingException if there is an error during the processing
     *                                 of JSON data, such as serialization or deserialization issues.
     */
    @RepeatedTest(value = 1, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @Order(4)
    void testGetLocationsForOrg(RepetitionInfo repetitionInfo) throws JsonProcessingException {
        TestOrganizationSearchRequest testOrganizationSearchRequest = requests.get(repetitionInfo.getCurrentRepetition() - 1);
        OrganizationList actualOrganizationList = getOrganizationList(testOrganizationSearchRequest);
        OrganizationDto organizationDto = actualOrganizationList.getOrganizationList().getFirst();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OrganizationSearchRequest> httpEntity = new HttpEntity<>(null, headers);
        String orgElementId = organizationDto.getElementId();
        String uri = "/api/v1/sapphire/organization/" + orgElementId +"/location/_search";
//        ResponseEntity<SapphireAPIResponse<OrganizationDto>> responseEntity =
//                testRestTemplate.exchange(
//                        uri,
//                        HttpMethod.POST,
//                        httpEntity,
//                        new ParameterizedTypeReference<>() {
//                        }
//                );

        SapphireAPIResponse<OrganizationDto> apiResponse = webTestClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.empty(), Void.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<SapphireAPIResponse<OrganizationDto>>(){})
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(apiResponse);
        OrganizationDto actualOrgDto = objectMapper.convertValue(apiResponse.getResponse(), OrganizationDto.class);
        OrganizationDto expectedOrgDto = testOrganizationSearchRequest.getExpectedOrganizationList().getOrganizationList().getFirst();
        OrganizationValidator.assertOrganization(actualOrgDto, expectedOrgDto);
    }

    /**
     * Tests the retrieval of location network details for an organization
     * based on a search request. This method issues a POST request to fetch
     * the networks linked to specific locations associated with an organization's
     * identifier and verifies the response.
     *
     * @param repetitionInfo provides details about the current repetition of
     *                       the repeated test method, such as the current repetition
     *                       index and total number of repetitions.
     * @throws JsonProcessingException if there is an error during the processing
     *                                 of JSON data, such as serialization or
     *                                 deserialization issues.
     */
    @RepeatedTest(value = 1, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @Order(5)
    void testGetLocationNetworksForOrg(RepetitionInfo repetitionInfo) throws JsonProcessingException {
        TestOrganizationSearchRequest testOrganizationSearchRequest = requests.get(repetitionInfo.getCurrentRepetition() - 1);
        OrganizationList actualOrganizationList = getOrganizationList(testOrganizationSearchRequest);
        OrganizationDto organizationDto = actualOrganizationList.getOrganizationList().getFirst();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OrganizationSearchRequest> httpEntity = new HttpEntity<>(null, headers);
        String orgElementId = organizationDto.getElementId();
        String locationName = testOrganizationSearchRequest.getLocationName();
        log.info("Location Name is:{}", locationName);
        LocationDto locationDto = getLocation(locationName);
        log.info("Location is:{}", locationDto);
        String uri = "/api/v1/sapphire/organization/" + orgElementId +"/location/" +
                locationDto.getElementId() +
                "/network/_search";
//        ResponseEntity<SapphireAPIResponse<OrganizationDto>> responseEntity =
//                testRestTemplate.exchange(
//                        uri,
//                        HttpMethod.POST,
//                        httpEntity,
//                        new ParameterizedTypeReference<>() {
//                        }
//                );

        SapphireAPIResponse<OrganizationDto> apiResponse = webTestClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.empty(), Void.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<SapphireAPIResponse<OrganizationDto>>(){})
                .returnResult()
                .getResponseBody();;
        Assertions.assertNotNull(apiResponse);
        OrganizationDto actualOrgDto = objectMapper.convertValue(apiResponse.getResponse(), OrganizationDto.class);
        OrganizationDto expectedOrgDto = testOrganizationSearchRequest.getExpectedOrganizationList().getOrganizationList().getFirst();
        OrganizationValidator.assertOrganization(actualOrgDto, expectedOrgDto);
    }

    /**
     * Retrieves a list of organizations based on the provided search request.
     * This method sends a POST request with the organization search criteria
     * and parses the response to extract the list of organizations.
     *
     * @param testOrganizationSearchRequest the search request containing details and criteria
     *                                      to filter or locate specific organizations.
     * @return a list of organizations matching the search criteria encapsulated in an
     *         {@link OrganizationList} object.
     */
    private OrganizationList getOrganizationList(TestOrganizationSearchRequest testOrganizationSearchRequest) {
        OrganizationSearchRequest organizationSearchRequest = testOrganizationSearchRequest.getOrgSearchRequest();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OrganizationSearchRequest> httpEntity = new HttpEntity<>(organizationSearchRequest, headers);
        String uri = "/api/v1/sapphire/organization/_search";
//        ResponseEntity<SapphireAPIResponse<OrganizationList>> responseEntity =
//                testRestTemplate.exchange(
//                        uri,
//                        HttpMethod.POST,
//                        httpEntity,
//                        new ParameterizedTypeReference<>() {
//                        }
//                );
        SapphireAPIResponse<OrganizationList> apiResponse = webTestClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(organizationSearchRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<SapphireAPIResponse<OrganizationList>>(){})
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(apiResponse);
        return objectMapper.convertValue(apiResponse.getResponse(), OrganizationList.class);
    }

    private NetworkDto getNetwork(String networkCode){
        NetworkSearchRequest networkSearchRequest = NetworkSearchRequest.builder()
                .networkCode(networkCode)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<NetworkSearchRequest> httpEntity = new HttpEntity<>(networkSearchRequest, headers);
        String uri = "/api/v1/sapphire/network/_search";
//        ResponseEntity<SapphireAPIResponse<NetworkList>> responseEntity =
//                testRestTemplate.exchange(
//                        uri,
//                        HttpMethod.POST,
//                        httpEntity,
//                        new ParameterizedTypeReference<>() {
//                        }
//                );
        SapphireAPIResponse<NetworkList> apiResponse = webTestClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(networkSearchRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<SapphireAPIResponse<NetworkList>>(){})
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(apiResponse);
        NetworkList networkList = objectMapper.convertValue(apiResponse.getResponse(), NetworkList.class);
        return  networkList.getNetworks().getFirst();
    }

    private LocationDto getLocation(String locationName){
        LocationSearchRequest locationSearchRequest = LocationSearchRequest.builder()
                .locationName(locationName)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LocationSearchRequest> httpEntity = new HttpEntity<>(locationSearchRequest, headers);
        String uri = "/api/v1/sapphire/location/_search";
//        ResponseEntity<SapphireAPIResponse<LocationList>> responseEntity =
//                testRestTemplate.exchange(
//                        uri,
//                        HttpMethod.POST,
//                        httpEntity,
//                        new ParameterizedTypeReference<>() {
//                        }
//                );
        SapphireAPIResponse<LocationList> apiResponse =
                webTestClient.post()
                        .uri(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(locationSearchRequest)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBody(new ParameterizedTypeReference<SapphireAPIResponse<LocationList>>(){})
                        .returnResult()
                        .getResponseBody();
        Assertions.assertNotNull(apiResponse);
        LocationList locationList = objectMapper.convertValue(apiResponse.getResponse(), LocationList.class);
        return  locationList.getLocations().getFirst();
    }

}
