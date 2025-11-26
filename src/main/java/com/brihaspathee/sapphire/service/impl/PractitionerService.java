package com.brihaspathee.sapphire.service.impl;

import com.brihaspathee.sapphire.domain.entity.Identifier;
import com.brihaspathee.sapphire.domain.entity.Practitioner;
import com.brihaspathee.sapphire.domain.repository.interfaces.PractitionerRepository;
import com.brihaspathee.sapphire.model.PractitionerDto;
import com.brihaspathee.sapphire.model.PractitionerList;
import com.brihaspathee.sapphire.model.web.PractitionerSearchRequest;
import com.brihaspathee.sapphire.service.interfaces.IPractitionerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11/18/25
 * Time: 7:08 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.service.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PractitionerService implements IPractitionerService {

    /**
     * Repository interface for performing CRUD operations and custom queries
     * related to the Practitioner entity. Utilized by the service layer to interact
     * with the persistence layer for practitioner data.
     */
    private final PractitionerRepository practitionerRepository;

    /**
     * Retrieves a list of practitioners based on the provided search criteria.
     *
     * @param practitionerSearchRequest the search request containing criteria such as identifiers
     *                                   to filter and retrieve practitioners
     * @return an instance of {@code PractitionerList} containing the list of practitioners
     *         that match the search criteria
     */
    @Override
    public PractitionerList getPractitioners(PractitionerSearchRequest practitionerSearchRequest) {
        List<Practitioner> practitioners = practitionerRepository.findPractitioners(practitionerSearchRequest);
        return null;
    }

    /**
     * Retrieves the details of a practitioner based on the provided practitioner ID.
     *
     * @param practitionerId the unique identifier of the practitioner whose details are to be retrieved
     * @return an instance of {@code PractitionerDto} containing the details of the practitioner
     */
    @Override
    public PractitionerDto getPractitionerById(String practitionerId) {
        Practitioner practitioner = practitionerRepository.findPractitionerById(practitionerId);
        log.info("Practitioner in service: {}", practitioner);
        for (Identifier identifier : practitioner.getIdentifiers()) {
            log.info("Practitioner Identifier: {}", identifier);
            log.info("Practitioner Identifier Value: {}", identifier.getValue());
        }
        return null;
    }
}
