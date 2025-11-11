package com.brihaspathee.sapphire.validator;


import com.brihaspathee.sapphire.model.IdentifierDto;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11/11/25
 * Time: 6:06 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.validator
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
public class IdentifierValidator {

    /**
     * Validates that all identifiers in the expectedList are present in the actualList.
     * For each identifier in the expectedList, it ensures the corresponding type of identifier
     * (NPI, TIN, or MedicareID) is found in the actualList and meets validation criteria.
     *
     * @param expectedList the list of expected {@link IdentifierDto} objects to be validated
     * @param actualList the list of actual {@link IdentifierDto} objects to validate against
     */
    public static void assertIdentifierList(List<IdentifierDto> expectedList, List<IdentifierDto> actualList){
        for (IdentifierDto expectedIdentifier : expectedList) {
            if (expectedIdentifier.getType().equals("NPI")){
                assertTrue(assertNPI(expectedIdentifier, actualList));
            }
            if (expectedIdentifier.getType().equals("TIN")){
                assertTrue(assertTIN(expectedIdentifier, actualList));
            }
            if (expectedIdentifier.getType().equals("MEDICARE_ID")){
                assertTrue(assertMedicareId(expectedIdentifier, actualList));
            }
        }
    }

    /**
     * Validates that the given NPI exists in the list of actual identifiers.
     *
     * @param npi the NPI to be validated
     * @param actualList the list of actual identifiers to search for the NPI
     * @return true if the NPI is found in the list and its value, start date, and end date match; otherwise false
     */
    private static Boolean assertNPI(IdentifierDto npi, List<IdentifierDto> actualList){
        boolean npiValidationPass = false;
        for (IdentifierDto actualIdentifier : actualList) {
            if (actualIdentifier.getType().equals("NPI")){
                if ((npi.getValue().equals(actualIdentifier.getValue())) &&
                        (npi.getStartDate() == null || npi.getStartDate().equals(actualIdentifier.getStartDate())) &&
                        (npi.getEndDate() == null || npi.getEndDate().equals(actualIdentifier.getEndDate()))){
                    npiValidationPass = true;
                    break;
                }
            }
        }
        return npiValidationPass;
    }

    /**
     * Validates that the given TIN exists in the list of actual identifiers.
     *
     * @param tin the TIN to be validated
     * @param actualList the list of actual identifiers to search for the TIN
     * @return true if the TIN is found in the list and its value and legal name match, otherwise false
     */
    private static Boolean assertTIN(IdentifierDto tin, List<IdentifierDto> actualList){
        boolean tinValidationPass = false;
        for (IdentifierDto actualIdentifier : actualList) {
            if (actualIdentifier.getType().equals("TIN")){
                if ((tin.getValue().equals(actualIdentifier.getValue())) &&
                        (tin.getAdditionalProperties().get("legalName") == null ||
                                tin.getAdditionalProperties().get("legalName").equals(tin.getAdditionalProperties().get("legalName")))){
                    tinValidationPass = true;
                    break;
                }
            }
        }
        return tinValidationPass;
    }

    /**
     * Validates that the given MedicareID exists in the list of actual identifiers.
     *
     * @param medicareID the MedicareID to be validated
     * @param actualList the list of actual identifiers to search for the MedicareID
     * @return true if the MedicareID exists in the list, otherwise false
     */
    private static Boolean assertMedicareId(IdentifierDto medicareID, List<IdentifierDto> actualList){
        boolean medicareIdValidationPass = false;
        for (IdentifierDto actualIdentifier : actualList) {
            if (actualIdentifier.getType().equals("MEDICARE_ID")) {
                if (medicareID.getValue().equals(actualIdentifier.getValue())) {
                    medicareIdValidationPass = true;
                    break;
                }
            }
        }
        return medicareIdValidationPass;
    }
}
