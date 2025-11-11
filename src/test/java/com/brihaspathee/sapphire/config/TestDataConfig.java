package com.brihaspathee.sapphire.config;

import com.brihaspathee.sapphire.web.model.TestOrganizationSearchRequest;
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

    @Bean
    public BuildTestData<TestOrganizationSearchRequest> testOrganizationBuildTestData() {
        return new BuildTestData<>();
    }

}
