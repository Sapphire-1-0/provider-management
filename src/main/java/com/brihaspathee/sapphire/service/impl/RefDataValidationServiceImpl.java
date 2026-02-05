package com.brihaspathee.sapphire.service.impl;

import com.brihaspathee.sapphire.domain.repository.interfaces.RefDataRepository;
import com.brihaspathee.sapphire.service.interfaces.RefDataValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 04, February 2026
 * Time: 15:41
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.service.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RefDataValidationServiceImpl implements RefDataValidationService {

    /**
     * A repository interface used for interacting with the persistence layer for reference data.
     * It provides methods for validating and retrieving reference data from the database or other
     * data sources which is used for the validation logic within the service layer.
     */
    private final RefDataRepository refDataRepository;

    /**
     * Validates the reference data provided and returns details regarding any validation errors or issues.
     *
     * @param refData a map where the key is a {@code String} representing the reference data type,
     *                and the value is a {@code List<String>} containing the items of reference data
     *                for validation
     * @return a list of maps where each map represents a validation result and provides details such as
     * errors, warnings, or any issues found during the validation process
     */
    @Override
    public Map<String, Map<String, Boolean>> validateRefData(Map<String, List<String>> refData) {
        return refDataRepository.validateRefData(refData);
    }
}
