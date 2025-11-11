package com.brihaspathee.sapphire.integration;

import com.brihaspathee.sapphire.model.IdentifierDto;
import com.brihaspathee.sapphire.model.OrganizationDto;
import com.brihaspathee.sapphire.model.OrganizationList;
import com.brihaspathee.sapphire.model.web.OrganizationSearchRequest;
import com.brihaspathee.sapphire.web.model.TestOrganizationSearchRequest;
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
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

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
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrganizationReadAPIIntTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Value("classpath:com/brihaspathee/sapphire/integration/organization/read/OrganizationReadAPIIntTest.json")
    Resource resourceFile;

    private TestClass<TestOrganizationSearchRequest> organizationSearchRequestTestClass;

    @Autowired
    private BuildTestData<TestOrganizationSearchRequest> buildTestData;

    private List<TestOrganizationSearchRequest> requests = new ArrayList<>();

    @BeforeEach
    void setUp(TestInfo testInfo) throws IOException {

        // Read the file information and convert to test class object
        organizationSearchRequestTestClass = objectMapper.readValue(resourceFile.getFile(), new TypeReference<>() {
        });

        // Build the test data for the test method that is to be executed
        this.requests = buildTestData.buildData(testInfo.getTestMethod().get().getName(),this.organizationSearchRequestTestClass);
    }

    @RepeatedTest(value = 1, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @Order(1)
    void testGetOrganizationById(RepetitionInfo repetitionInfo){
        log.info("Current Repetition:{}", repetitionInfo.getCurrentRepetition());
        TestOrganizationSearchRequest testOrganizationSearchRequest = requests.get(repetitionInfo.getCurrentRepetition() - 1);
        log.info("Test Organization Search Request:{}", testOrganizationSearchRequest);
        log.info("Organization Search Request:{}", testOrganizationSearchRequest.getOrgSearchRequest());
        OrganizationSearchRequest organizationSearchRequest = testOrganizationSearchRequest.getOrgSearchRequest();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OrganizationSearchRequest> httpEntity = new HttpEntity<>(organizationSearchRequest, headers);
        String uri = "/api/v1/sapphire/organization/private/_search";
        ResponseEntity<SapphireAPIResponse> responseEntity = testRestTemplate
                .postForEntity(uri,httpEntity, SapphireAPIResponse.class);
        SapphireAPIResponse apiResponse = responseEntity.getBody();
        OrganizationList actualOrganizationList = objectMapper.convertValue(apiResponse.getResponse(), OrganizationList.class);
        for (OrganizationDto organizationDto : actualOrganizationList.getOrganizationList()) {
            log.info("Organization Id:{}", organizationDto.getElementId());
            log.info("Organization Name:{}", organizationDto.getName());
            for (IdentifierDto identifierDto : organizationDto.getIdentifiers()) {
                log.info("Identifier Type:{}", identifierDto.getType());
                log.info("Identifier Value:{}", identifierDto.getValue());
            }
        }
    }


}
