package com.brihaspathee.sapphire.service.impl;

import com.brihaspathee.sapphire.domain.entity.Organization;
import com.brihaspathee.sapphire.domain.repository.OrganizationRepository;
import com.brihaspathee.sapphire.mapper.interfaces.IOrganizationMapper;
import com.brihaspathee.sapphire.model.OrganizationDto;
import com.brihaspathee.sapphire.service.interfaces.IOrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 27, August 2025
 * Time: 05:17
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.service
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationService implements IOrganizationService {

    /**
     * Serves as the repository interface for performing database operations
     * related to the Organization entity. This dependency is injected to
     * enable interaction with the underlying Neo4j database to manage
     * organization data. It is used within the OrganizationService class.
     */
    private final OrganizationRepository organizationRepository;

    /**
     * Maps Organization entities to their corresponding DTOs and vice versa.
     * This mapper is responsible for converting between the database entity
     * representations of organizations and their data transfer object (DTO) representations.
     */
    private final IOrganizationMapper organizationMapper;


    /**
     * Retrieves all organizations and converts them to a list of OrganizationDto objects.
     * Logs the name of each organization during the retrieval process.
     *
     * @return a list of OrganizationDto objects representing all the organizations
     */
    @Override
    public List<OrganizationDto> getAllOrganizations() {
        log.info("Fetching all organizations");
        List<Organization> organizations = organizationRepository.findAllWithElementId();
        for (Organization organization : organizations) {
            log.info("Organization name: {}", organization.getName());
            log.info("Element id of the Org:{}", organization.getElementId());
        }
        return organizationMapper.toOrganizationDtoList(organizations);
    }
}
