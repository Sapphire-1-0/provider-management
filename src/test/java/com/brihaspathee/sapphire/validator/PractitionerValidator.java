package com.brihaspathee.sapphire.validator;

import com.brihaspathee.sapphire.model.OrganizationDto;
import com.brihaspathee.sapphire.model.OrganizationList;
import com.brihaspathee.sapphire.model.PractitionerDto;
import com.brihaspathee.sapphire.model.PractitionerList;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11, December 2025
 * Time: 05:12
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.validator
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
public class PractitionerValidator {

    /**
     * Asserts that the actual PractitionerList matches the expected PractitionerList.
     * Validates that the lists of PractitionerDto objects in both PractitionerList instances
     * have the same size. Each corresponding PractitionerDto in the actual list matches
     * the one in the expected list by comparing their properties such as code, first name, last name,
     * and others if present.
     *
     * @param expected the expected PractitionerList containing the reference list of PractitionerDto objects.
     * @param actual the actual PractitionerList whose PractitionerDto objects are validated against the expected list.
     * @throws RuntimeException if a validation error occurs or if JSON processing encounters an issue.
     */
    public static void assertPractitionerList(PractitionerList expected, PractitionerList actual){
        List<PractitionerDto> expectedList = expected.getPractitioners();
        List<PractitionerDto> actualList = actual.getPractitioners();
        assert expectedList.size() == actualList.size();
        expectedList.stream().forEach( (expectedPractitioner -> {
            PractitionerDto actualPractitioner = actualList.stream().filter(
                            (actualPracDto) -> {
                                return expectedPractitioner.getCode().equals(actualPracDto.getCode());
                            })
                    .findFirst().orElse(PractitionerDto.builder()
                            .code("Random Practitioner")
                            .build());
            try {
                assertPractitioner(expectedPractitioner, actualPractitioner);
            } catch (JsonProcessingException e) {
                log.info(e.getMessage());
                throw new RuntimeException(e);

            }
        }));
    }

    /**
     * Validates that the actual {@link PractitionerDto} matches the expected {@link PractitionerDto}.
     * Each field is compared, and differences are asserted to ensure correctness.
     *
     * @param expected the expected {@link PractitionerDto} containing the reference values for validation.
     * @param actual the actual {@link PractitionerDto} to validate against the expected values.
     * @throws JsonProcessingException if an issue occurs during JSON processing.
     */
    public static void assertPractitioner(PractitionerDto expected, PractitionerDto actual)
            throws JsonProcessingException {
        assertNotNull(actual.getCode());
        assert expected.getCode().equals(actual.getCode());
        assertNotNull(actual.getFirstName());
        assert expected.getFirstName().equals(actual.getFirstName());
        assertNotNull(expected.getLastName());
        assert expected.getLastName().equals(actual.getLastName());
        if (expected.getMiddleName() != null) {
            assertNotNull(actual.getMiddleName());
            assert expected.getMiddleName().equals(actual.getMiddleName());
        }
        if (expected.getBirthDate() != null) {
            log.info("Birth Date: {}", expected.getBirthDate());
            log.info("Actual Birth Date: {}", actual.getBirthDate());
            assertNotNull(actual.getBirthDate());
            assert expected.getBirthDate().equals(actual.getBirthDate());
        }
        if (expected.getGender() != null) {
            assertNotNull(actual.getGender());
            assert expected.getGender().equals(actual.getGender());
        }
    }
}
