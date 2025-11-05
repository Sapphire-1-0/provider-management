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
        Map<String, String> identifiers = new HashMap<>();
        identifiers.put("PPG_ID", "MS_200");
        identifiers.put("NPI", "34234");
        organizationRepository.findAllByIdentifier(identifiers, true);
//        for (Organization organization : organizations) {
//            log.info("Organization name: {}", organization.getName());
//            log.info("Organization Alias Name:{}", organization.getAliasName());
//            log.info("Element id of the Org:{}", organization.getElementId());
//            for (Identifier identifier : organization.getIdentifiers()) {
//                log.info("Identifier value: {}", identifier.getValue());
//                if (identifier instanceof NPI){
//                    log.info("NPI value: {}", identifier.getValue());
//                } else if (identifier instanceof MedicaidID) {
//                    log.info("MedicaidID value: {}", ((MedicaidID) identifier).getState());
//                }else if (identifier instanceof TIN){
//                    log.info("TIN legal name: {}", ((TIN) identifier).getLegalName());
//                }
//
//            }
//        }
        return organizations.stream().map(this::toOrganizationDto).toList();
    }

    private OrganizationDto toOrganizationDto(Organization organization) {
        List<IdentifierDto> identifierDtos = organization.getIdentifiers().stream().map(identifier -> {
           return IdentifierDto.builder()
                   .elementId(identifier.getElementId())
                   .value(identifier.getValue())
                   .type(identifier.getClass().getSimpleName())
                   .startDate(identifier.getStartDate())
                   .endDate(identifier.getEndDate())
                   .additionalProperties(getAdditionalProperties(identifier))
                   .build();
        }).toList();
        OrganizationDto organizationDto = OrganizationDto.builder()
                .elementId(organization.getElementId())
                .name(organization.getName())
                .aliasName(organization.getAliasName())
                .identifiers(identifierDtos)
                .build();
        return organizationDto;
    }

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
