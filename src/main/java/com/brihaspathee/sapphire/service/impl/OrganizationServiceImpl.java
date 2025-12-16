package com.brihaspathee.sapphire.service.impl;

import com.brihaspathee.sapphire.domain.entity.*;
import com.brihaspathee.sapphire.domain.repository.interfaces.OrganizationRepository;
import com.brihaspathee.sapphire.mapper.interfaces.OrganizationMapper;
import com.brihaspathee.sapphire.model.*;
import com.brihaspathee.sapphire.model.web.IdentifierInfo;
import com.brihaspathee.sapphire.model.web.OrganizationSearchRequest;
import com.brihaspathee.sapphire.service.interfaces.OrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class OrganizationServiceImpl implements OrganizationService {

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
    private final OrganizationMapper organizationMapper;


    /**
     * Retrieves an organization based on its unique code.
     *
     * @param code the unique code of the organization to retrieve
     * @return the organization matching the provided code, or null if no matching organization is found
     */
    @Override
    public OrganizationDto getOrganizationByCode(String code) {
        List<Organization> organizations = organizationRepository.findByCode(code);
        return organizationMapper.toOrganizationDto(organizations.isEmpty() ? null : organizations.getFirst());
    }

    /**
     * Inserts a new organization or updates an existing one based on the provided organization details.
     *
     * @param organizationDto an instance of {@code OrganizationDto} containing the details of the organization
     *                        to be inserted or updated, including its identifiers, name, type, and associated
     *                        information such as locations and networks
     */
    @Override
    public void upsertOrganization(OrganizationDto organizationDto) {

    }

    /**
     * Retrieves all organizations and converts them to a list of OrganizationDto objects.
     * Logs the name of each organization during the retrieval process.
     *
     * @return a list of OrganizationDto objects representing all the organizations
     */
    @Override
    public List<OrganizationDto> getAllOrganizations() {
        log.info("Fetching all organizations");
        List<Organization> organizations = organizationRepository.findAllWithIdentifiers();
        return organizations.stream().map(organizationMapper::toOrganizationDto).toList();
    }

    /**
     * Retrieves a list of organizations based on the provided identifiers.
     * The identifiers are used to filter and find the matching organizations.
     *
     * @param organizationSearchRequest Request containing the identifiers to be used for filtering.
     * @return a list of OrganizationDto objects representing the organizations
     *         that match the provided identifiers.
     */
    @Override
    public List<OrganizationDto> getOrganizationsByIdentifiers(OrganizationSearchRequest organizationSearchRequest) {
        Map<String, String> identifiers = new HashMap<>();
        for (IdentifierInfo identifierInfo : organizationSearchRequest.getIdentifiers()) {
            identifiers.put(identifierInfo.getIdentifierType(), identifierInfo.getIdentifierValue());
        }
        List<Organization> organizations = organizationRepository.findAllByIdentifier(identifiers, true);
        return organizations.stream().map(organizationMapper::toOrganizationDto).toList();
    }

    /**
     * Retrieves an organization's details along with its associated networks based on the given element ID.
     *
     * @param elementId the unique identifier of the element to fetch the organization and network details.
     * @return an OrganizationDto object containing the organization's information and its associated networks.
     */
    @Override
    public OrganizationDto getOrganizationAndNetworks(String elementId) {
        Organization organization = organizationRepository.findAllOrganizationNetworks(elementId);
        return organizationMapper.toOrganizationDto(organization);
    }

    /**
     * Retrieves the network locations for a specific organization and network based on
     * the provided organization ID and network ID.
     *
     * @param orgId the unique identifier of the organization
     * @param netId the unique identifier of the network
     * @return an OrganizationDto object containing the organization network location details
     */
    @Override
    public OrganizationDto getOrganizationNetworkLocations(String orgId, String netId) {
        Organization organization = organizationRepository.findLocationsByOrgAndNet(orgId, netId);
        return organizationMapper.toOrganizationDto(organization);
    }

    /**
     * Retrieves the organization details along with its associated locations
     * based on the provided element ID.
     *
     * @param elementId the unique identifier of the organization for which the
     *                  details and associated locations are being fetched
     * @return an instance of {@code OrganizationDto} containing the details of
     * the organization along with its associated locations
     */
    @Override
    public OrganizationDto getOrganizationAndLocations(String elementId) {
        Organization organization = organizationRepository.findAllOrganizationLocations(elementId);
        return organizationMapper.toOrganizationDto(organization);
    }

    /**
     * Retrieves the organization details, including its location and network information,
     * based on the provided organization ID and network ID.
     *
     * @param orgId the unique identifier of the organization for which the details are being fetched
     * @param locId the unique identifier of the location associated with the specified organization
     * @return an instance of {@code OrganizationDto} containing the details of the organization,
     * including its associated locations and networks
     */
    @Override
    public OrganizationDto getOrganizationLocationNetworks(String orgId, String locId) {
        Organization organization = organizationRepository.findNetworksByOrgAndLoc(orgId, locId);
        return organizationMapper.toOrganizationDto(organization);
    }

    /**
     * Retrieves the details of an organization based on the provided element ID.
     *
     * @param orgId the unique identifier of the element associated with the organization
     * @return an instance of {@code OrganizationDto} containing the details of the
     * organization that corresponds to the specified element ID, or {@code null}
     * if no such organization exists
     */
    @Override
    public OrganizationDto getOrganizationByElementId(String orgId) {
        Organization organization = organizationRepository.findOrganizationByElementId(orgId);
        return organizationMapper.toOrganizationDto(organization);
    }
}
