package com.brihaspathee.sapphire.config;

import com.brihaspathee.sapphire.web.model.TestOrganizationSearchRequest;
import com.brihaspathee.sapphire.web.model.TestPractitionerSearchRequest;
import com.brihaspathee.test.BuildTestData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11, November 2025
 * Time: 12:04
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.config
 * To change this template use File | Settings | File and Code Template
 */
@Configuration
public class TestDataConfig {

    /**
     * Defines a Spring bean that creates an instance of BuildTestData for the TestOrganizationSearchRequest type.
     * This bean is used to construct and manage test data for organization search request-related test cases.
     *
     * @return an instance of BuildTestData configured for handling TestOrganizationSearchRequest objects.
     */
    @Bean
    public BuildTestData<TestOrganizationSearchRequest> testOrganizationBuildTestData() {
        return new BuildTestData<>();
    }

    /**
     * Defines a Spring bean that creates an instance of BuildTestData for the TestPractitionerSearchRequest type.
     * This bean is used to construct and manage test data for practitioner search request-related test cases.
     *
     * @return an instance of BuildTestData configured for handling TestPractitionerSearchRequest objects.
     */
    @Bean
    public BuildTestData<TestPractitionerSearchRequest> testPractitionerBuildTestData() {
        return new BuildTestData<>();
    }

}
