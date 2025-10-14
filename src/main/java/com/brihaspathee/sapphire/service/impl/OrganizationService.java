package com.brihaspathee.sapphire.service.impl;

import com.brihaspathee.sapphire.domain.entity.Organization;
import com.brihaspathee.sapphire.domain.repository.OrganizationRepository;
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


    @Override
    public List<Organization> getAllOrganizations() {
        log.info("Fetching all organizations");
        List<Organization> organizations = organizationRepository.findAll();
        for (Organization organization : organizations) {
            log.info("Organization name: {}", organization.getName());
        }
        return organizations;
    }
}
