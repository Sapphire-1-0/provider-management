package com.brihaspathee.sapphire.domain.repository.interfaces;

import java.util.List;
import java.util.Map;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 04, February 2026
 * Time: 15:42
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.repository.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface RefDataRepository {

    /**
     * Validates the reference data provided in the input map and returns a map
     * indicating the validation results for each entry.
     *
     * @param refData a map where the key is a String representing the reference type
     *                and the value is a list of Strings representing the reference values
     *                to be validated
     * @return a map where the key is a String representing the reference type
     *         and the value is another map where the key is a reference value
     *         and the value is a Boolean indicating whether the value is valid
     */
    Map<String, Map<String, Boolean>> validateRefData(Map<String, List<String>> refData);
}
