package com.brihaspathee.sapphire.validator;

import com.brihaspathee.sapphire.model.OrganizationDto;
import com.brihaspathee.sapphire.model.OrganizationList;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11/11/25
 * Time: 5:53 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.validator
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
public class OrganizationValidator {

    /**
     * Asserts that the two given {@link OrganizationList} objects are equivalent by comparing
     * their respective {@link OrganizationDto} lists. Each {@link OrganizationDto} in the expected
     * list is matched with a corresponding {@link OrganizationDto} in the actual list based
     * on the organization name. The individual properties of matched {@link OrganizationDto}
     * objects are compared for equality.
     *
     * @param expected the expected {@link OrganizationList} containing the expected values
     * @param actual the actual {@link OrganizationList} to be compared against the expected values
     * @throws RuntimeException if the comparison fails or if an exception occurs during processing
     */
    public static void assertOrganizationList(OrganizationList expected, OrganizationList actual){
        List<OrganizationDto> expectedList = expected.getOrganizationList();
        List<OrganizationDto> actualList = actual.getOrganizationList();
        assert expectedList.size() == actualList.size();
        expectedList.stream().forEach( (expectedOrganization -> {
            OrganizationDto actualOrganization = actualList.stream().filter(
                            (actualOrgDto) -> {
                                return expectedOrganization.getName().equals(actualOrgDto.getName());
                            })
                    .findFirst().orElse(OrganizationDto.builder()
                            .name("Random Organization")
                            .build());
            try {
                assertAccount(expectedOrganization, actualOrganization);
            } catch (JsonProcessingException e) {
                log.info(e.getMessage());
                throw new RuntimeException(e);

            }
        }));
    }

    /**
     * Asserts that the properties of the two given {@link OrganizationDto} objects are equal.
     *
     * @param expected the expected {@link OrganizationDto} object containing the expected values
     * @param actual the actual {@link OrganizationDto} object to be compared against the expected values
     * @throws JsonProcessingException if an error occurs during JSON processing (not used in the current logic)
     */
    public static void assertAccount(OrganizationDto expected, OrganizationDto actual)
            throws JsonProcessingException {
        assert expected.getName().equals(actual.getName());
        assert expected.getAliasName().equals(actual.getAliasName());
        assert expected.getType().equals(actual.getType());
        assert expected.getAtypical() == actual.getAtypical();
        assert expected.getCapitated() == actual.getCapitated();
        assert expected.getPcpPractitionerRequired() == actual.getPcpPractitionerRequired();
        IdentifierValidator.assertIdentifierList(expected.getIdentifiers(), actual.getIdentifiers());
    }
}
