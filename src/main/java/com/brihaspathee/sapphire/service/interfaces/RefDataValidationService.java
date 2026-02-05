package com.brihaspathee.sapphire.service.interfaces;

import java.util.List;
import java.util.Map;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 04, February 2026
 * Time: 15:40
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface RefDataValidationService {

    /**
     * Validates the reference data provided and returns details regarding any validation errors or issues.
     *
     * @param refData a map where the key is a {@code String} representing the reference data type,
     *                and the value is a {@code List<String>} containing the items of reference data
     *                for validation
     * @return a map of maps where each map represents a validation result and provides details such as
     *         errors, warnings, or any issues found during the validation process
     */
    Map<String, Map<String, Boolean>> validateRefData(Map<String, List<String>> refData);
}
