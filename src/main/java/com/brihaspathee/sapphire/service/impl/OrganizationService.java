package com.brihaspathee.sapphire.service.impl;

import com.brihaspathee.sapphire.domain.entity.*;
import com.brihaspathee.sapphire.domain.repository.interfaces.OrganizationRepository;
import com.brihaspathee.sapphire.mapper.interfaces.IOrganizationMapper;
import com.brihaspathee.sapphire.model.IdentifierDto;
import com.brihaspathee.sapphire.model.OrganizationDto;
import com.brihaspathee.sapphire.service.interfaces.IOrganizationService;
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
        List<Organization> organizations = organizationRepository.findAllWithIdentifiers();
        return organizations.stream().map(this::toOrganizationDto).toList();
    }

    /**
     * Retrieves a list of organizations based on the provided identifiers.
     * The identifiers are used to filter and find the matching organizations.
     *
     * @param identifiers a map where the key represents the type of identifier (e.g., "NPI", "PPG_ID")
     *                    and the value represents the corresponding identifier value.
     * @return a list of OrganizationDto objects representing the organizations
     *         that match the provided identifiers.
     */
    @Override
    public List<OrganizationDto> getOrganizationsByIdentifiers(Map<String, String> identifiers) {
        List<Organization> organizations = organizationRepository.findAllByIdentifier(identifiers, true);
        return organizations.stream().map(this::toOrganizationDto).toList();
    }

    @Override
    public OrganizationDto getOrganizationAndNetworks(String elementId) {
        Organization organization = organizationRepository.findAllOrganizationNetworks(elementId);
        return toOrganizationDto(organization);
    }


    /**
     * Converts an {@link Organization} entity into an {@link OrganizationDto} object.
     *
     * @param organization the {@link Organization} entity that needs to be transformed into a DTO
     * @return the resulting {@link OrganizationDto} containing all necessary details of the organization
     */
    private OrganizationDto toOrganizationDto(Organization organization) {
        List<IdentifierDto> identifierDtos = null;
        if (organization.getIdentifiers() != null && !organization.getIdentifiers().isEmpty()){
            identifierDtos = organization.getIdentifiers().stream().map(identifier -> {
                return IdentifierDto.builder()
                        .elementId(identifier.getElementId())
                        .value(identifier.getValue())
                        .type(identifier.getClass().getSimpleName())
                        .startDate(identifier.getStartDate())
                        .endDate(identifier.getEndDate())
                        .additionalProperties(getAdditionalProperties(identifier))
                        .build();
            }).toList();
        }
        OrganizationDto organizationDto = OrganizationDto.builder()
                .elementId(organization.getElementId())
                .name(organization.getName())
                .aliasName(organization.getAliasName())
                .identifiers(identifierDtos)
                .build();
        return organizationDto;
    }

    /**
     * Constructs a map of additional properties based on the type of the provided identifier.
     * If the identifier is of type {@code MedicaidID}, its state is added to the map.
     * If the identifier is of type {@code TIN}, its legal name is added to the map.
     *
     * @param identifier the identifier whose additional properties need to be collected
     * @return a map containing additional properties specific to the identifier type
     */
    private Map<String, Object> getAdditionalProperties(Identifier identifier) {
        Map<String, Object> additionalProperties = new HashMap<>();
        if (identifier instanceof MedicaidID medicaidID) {
            additionalProperties.put("state",medicaidID.getState());
        }
        if (identifier instanceof TIN tin) {
            additionalProperties.put("legalName", tin.getLegalName());
        }
        return additionalProperties;
    }
}
