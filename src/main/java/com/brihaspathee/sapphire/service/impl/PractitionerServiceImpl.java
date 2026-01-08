package com.brihaspathee.sapphire.service.impl;

import com.brihaspathee.sapphire.domain.entity.Identifier;
import com.brihaspathee.sapphire.domain.entity.Practitioner;
import com.brihaspathee.sapphire.domain.repository.interfaces.PractitionerRepository;
import com.brihaspathee.sapphire.mapper.interfaces.PractitionerMapper;
import com.brihaspathee.sapphire.model.PractitionerDto;
import com.brihaspathee.sapphire.model.PractitionerList;
import com.brihaspathee.sapphire.model.web.PractitionerSearchRequest;
import com.brihaspathee.sapphire.service.interfaces.PractitionerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
public class PractitionerServiceImpl implements PractitionerService {

    /**
     * Repository interface for performing CRUD operations and custom queries
     * related to the Practitioner entity. Utilized by the service layer to interact
     * with the persistence layer for practitioner data.
     */
    private final PractitionerRepository practitionerRepository;

    /**
     * Facilitates the mapping of {@code Practitioner} entities to {@code PractitionerDto} objects.
     * This field is used in the service layer to transform entity data for client-side or external use.
     */
    private final PractitionerMapper practitionerMapper;

    /**
     * Retrieves the details of a practitioner based on the provided practitioner code.
     *
     * @param practitionerCode the unique code of the practitioner whose details are to be retrieved
     * @return an instance of {@code PractitionerDto} containing the details of the practitioner
     * that corresponds to the specified code, or {@code null} if no such practitioner exists
     */
    @Override
    public PractitionerDto getPractitionerByCode(String practitionerCode) {
        log.info("Retrieving practitioner with code: {}", practitionerCode);
        Practitioner practitioner = practitionerRepository.findPractitionerByCode(practitionerCode);
        return practitionerMapper.toPractitionerDto(practitioner);
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
        return practitionerMapper.toPractitionerDto(practitioner);
    }

    /**
     * Retrieves information about a practitioner and network based on their respective element IDs.
     *
     * @param pracId the unique identifier for the practitioner
     * @param netId  the unique identifier for the network
     * @return an instance of {@code PractitionerDto} containing the details of the associated
     * practitioner and network, or {@code null} if not found
     */
    @Override
    public PractitionerDto getPracAndNetByElementId(String pracId, String netId) {
        Practitioner practitioner = practitionerRepository.findPracAndNetByElementId(pracId, netId);
        return practitionerMapper.toPractitionerDto(practitioner);
    }

    /**
     * Retrieves a practitioner's details and location information based on the provided practitioner ID and location ID.
     *
     * @param pracId the unique identifier for the practitioner
     * @param locId  the unique identifier for the location
     * @return an instance of {@code PractitionerDto} containing the practitioner's details and associated location information,
     * or {@code null} if no matching record is found
     */
    @Override
    public PractitionerDto getPracAndLocByElementId(String pracId, String locId) {
        Practitioner practitioner = practitionerRepository.findPracAndLocByElementId(pracId, locId);
        return practitionerMapper.toPractitionerDto(practitioner);
    }

    /**
     * Retrieves information about a practitioner, network, and location based on their respective element IDs.
     *
     * @param pracId the unique identifier for the practitioner
     * @param netId  the unique identifier for the network
     * @param locId  the unique identifier for the location
     * @return an instance of {@code PractitionerDto} containing the details of the practitioner,
     * network, and location, or {@code null} if no matching record is found
     */
    @Override
    public PractitionerDto getPracAndNetAndLocByElementId(String pracId, String netId, String locId) {
        Practitioner practitioner = practitionerRepository.findPracAndNetAndLocByElementId(pracId, netId, locId);
        return practitionerMapper.toPractitionerDto(practitioner);
    }

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
        log.info("Practitioners in service: {}", practitioners);
        List<PractitionerDto> practitionerDtos = practitionerMapper.toPractitionerDtoList(practitioners);
        return PractitionerList.builder()
                .practitioners(practitionerDtos)
                .build();
    }

    /**
     * Creates a new practitioner in the system with the provided details.
     *
     * @param practitionerDto an instance of {@code PractitionerDto} containing
     *                        the details of the practitioner to be created.
     *                        This includes attributes such as personal information,
     *                        identifiers, and qualifications.
     */
    @Override
    public void createPractitioner(PractitionerDto practitionerDto) {
        practitionerRepository.createPractitioner(practitionerDto);
    }


}
