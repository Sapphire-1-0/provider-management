package com.brihaspathee.sapphire.mapper.impl;

import com.brihaspathee.sapphire.domain.entity.*;
import com.brihaspathee.sapphire.domain.entity.relationships.HasPanel;
import com.brihaspathee.sapphire.domain.entity.relationships.RoleLocationServes;
import com.brihaspathee.sapphire.mapper.interfaces.IdentifierMapper;
import com.brihaspathee.sapphire.mapper.interfaces.LocationMapper;
import com.brihaspathee.sapphire.mapper.interfaces.NetworkMapper;
import com.brihaspathee.sapphire.mapper.interfaces.OrganizationMapper;
import com.brihaspathee.sapphire.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 16, October 2025
 * Time: 05:14
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrganizationMapperImpl implements OrganizationMapper {

    /**
     * A mapper component responsible for converting Network entities to their
     * corresponding DTO representations and vice versa. This field holds a
     * reference to an implementation of {@code NetworkMapper}, which facilitates
     * the mapping between the {@code Network} domain entity and the {@code NetworkDto}.
     *
     * Utilized as part of the organization conversion logic and ensures that
     * the Network information is accurately converted and included in the output
     * DTOs where applicable.
     */
    private final NetworkMapper networkMapper;

    /**
     * A mapper component used for converting {@link Location} entities to their corresponding
     * DTO representations and vice versa. This field holds a reference to the {@link LocationMapper}
     * implementation and is utilized to facilitate mapping operations involving Location objects.
     */
    private final LocationMapper locationMapper;

    /**
     * A mapper component utilized for converting Identifier entities to their corresponding
     * DTO representations and vice versa. This field holds a reference to an {@link IdentifierMapper}
     * implementation, which provides methods for mapping individual Identifier objects or
     * lists of Identifier objects into their DTO equivalents.
     *
     * This mapper is specifically designed to facilitate mapping operations related
     * to identifiers within the context of the OrganizationMapperImpl component.
     */
    private final IdentifierMapper identifierMapper;

    /**
     * Converts an Organization entity to an OrganizationDto object.
     *
     * @param organization the Organization entity to be converted;
     *                     if null, the method will return null.
     * @return an OrganizationDto object built based on the data
     *         from the provided Organization entity, or null if
     *         the input organization is null.
     */
    @Override
    public OrganizationDto toOrganizationDto(Organization organization) {
        if(organization == null) {
            return null;
        }
        List<IdentifierDto> identifierDtos = null;
        List<NetworkDto> networkDtos = null;
        List<LocationDto> locationDtos = null;
        if (organization.getIdentifiers() != null && !organization.getIdentifiers().isEmpty()){
            identifierDtos = identifierMapper.toIdentifierDtoList(organization.getIdentifiers());
        }
        if (organization.getNetworks() != null && !organization.getNetworks().isEmpty()) {
            networkDtos = organization.getNetworks().stream().map(network -> {
                NetworkDto networkDto = networkMapper.toNetDto(network);
                if (network.getLocations() != null && !network.getLocations().isEmpty()) {
                    networkDto.setLocations(locationMapper.toLocationDtoList(network.getLocations()));
                }
                return networkDto;
            }).toList();
        }
        if(organization.getLocations() != null && !organization.getLocations().isEmpty()){
            locationDtos = organization.getLocations().stream().map(location -> {
                LocationDto locationDto = LocationDto.builder()
                        .elementId(location.getElementId())
                        .name(location.getName())
                        .streetAddress(location.getStreetAddress())
                        .secondaryAddress(location.getSecondaryAddress())
                        .city(location.getCity())
                        .state(location.getState())
                        .zipCode(location.getZipCode())
                        .county(location.getCounty())
                        .countyFIPS(location.getCountyFIPS())
                        .build();
                if (location.getNetworks() != null && !location.getNetworks().isEmpty()) {
                    locationDto.setNetworks(location.getNetworks().stream().map(network -> {
                        NetworkDto networkDto =  networkMapper.toNetDto(network);
                        return networkDto;
                    }).toList());
                }
                return locationDto;
            }).toList();
        }
        OrganizationDto organizationDto = OrganizationDto.builder()
                .elementId(organization.getElementId())
                .name(organization.getName())
                .aliasName(organization.getAliasName())
                .type(organization.getType())
                .atypical(organization.getAtypical())
                .capitated(organization.getCapitated())
                .pcpPractitionerRequired(organization.getPcpPractitionerRequired())
                .identifiers(identifierDtos)
                .networks(networkDtos)
                .locations(locationDtos)
                .build();
        log.info("Organization DTO in service: {}", organizationDto);
        return organizationDto;
    }

    /**
     * Converts an OrganizationDto object to an Organization entity.
     *
     * @param organizationDto the OrganizationDto object to be converted;
     *                        if null, the method will return null.
     * @return an Organization entity built based on the data from the provided OrganizationDto,
     *         or null if the input OrganizationDto is null.
     */
    @Override
    public Organization toOrganization(OrganizationDto organizationDto) {
        if(organizationDto == null) {
            return null;
        }
        Organization organization = Organization.builder()
                .name(organizationDto.getName())
                .type(organizationDto.getType())
                .aliasName(organizationDto.getAliasName())
                .atypical(organizationDto.getAtypical())
                .capitated(organizationDto.getCapitated())
                .pcpPractitionerRequired(organizationDto.getPcpPractitionerRequired())
                .build();
        return organization;
    }

    /**
     * Converts a list of Organization entities to a list of OrganizationDto objects.
     *
     * @param organizations the list of Organization entities to be converted
     * @return a list of OrganizationDto objects corresponding to the provided Organization entities,
     *         or null if the input list is null or empty
     */
    @Override
    public List<OrganizationDto> toOrganizationDtoList(List<Organization> organizations) {
        if(organizations != null && !organizations.isEmpty()) {
            return organizations.stream()
                    .map(this::toOrganizationDto)
                    .toList();
        }
        else{
            return null;
        }
    }

    /**
     * Converts a list of OrganizationDto objects to a list of Organization entities.
     *
     * @param organizationDtos the list of OrganizationDto objects to be converted
     * @return a list of Organization entities corresponding to the provided OrganizationDto objects,
     *         or null if the input list is null or empty
     */
    @Override
    public List<Organization> toOrganizationList(List<OrganizationDto> organizationDtos) {
        if(organizationDtos != null && !organizationDtos.isEmpty()) {
            return organizationDtos.stream()
                    .map(this::toOrganization)
                    .toList();
        }else{
            return null;
        }
    }
}
