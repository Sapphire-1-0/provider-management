package com.brihaspathee.sapphire.mapper.impl;

import com.brihaspathee.sapphire.domain.entity.*;
import com.brihaspathee.sapphire.domain.entity.relationships.HasPanel;
import com.brihaspathee.sapphire.domain.entity.relationships.RoleLocationServes;
import com.brihaspathee.sapphire.mapper.interfaces.IOrganizationMapper;
import com.brihaspathee.sapphire.model.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
public class OrganizationMapperImpl implements IOrganizationMapper {

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
        if (organization.getNetworks() != null && !organization.getNetworks().isEmpty()) {
            networkDtos = organization.getNetworks().stream().map(network -> {
                NetworkDto networkDto = NetworkDto.builder()
                        .elementId(network.getElementId())
                        .name(network.getName())
                        .code(network.getCode())
                        .isHNETNetwork(network.getIsHNETNetwork())
                        .isVendorNetwork(network.getIsVendorNetwork())
                        .build();
                if (network.getLocations() != null && !network.getLocations().isEmpty()) {
                    networkDto.setLocations(network.getLocations().stream().map(location -> {
                        LocationDto locationDto =  LocationDto.builder()
                                .elementId(location.getElementId())
                                .name(location.getName())
                                .streetAddress(location.getStreetAddress())
                                .secondaryAddress(location.getSecondaryAddress())
                                .city(location.getCity())
                                .state(location.getState())
                                .zipCode(location.getZipCode())
                                .build();
                        if (location.getNetworkServiceInfo() != null){
                            LocationNetworkDto locationNetworkDto = toLocationNetworkDto(location.getNetworkServiceInfo());
                            locationDto.setLocationNetwork(locationNetworkDto);
                        }
                        return locationDto;
                    }).toList());
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
                        NetworkDto networkDto =  NetworkDto.builder()
                                .elementId(network.getElementId())
                                .name(network.getName())
                                .code(network.getCode())
                                .build();
                        if (network.getNetworkServiceInfo() != null){
                            LocationNetworkDto locationNetworkDto = toLocationNetworkDto(network.getNetworkServiceInfo());
                            networkDto.setLocationNetwork(locationNetworkDto);
                        }
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

    /**
     * Converts a {@code LocationNetworkServiceInfo} object to a {@code LocationNetworkDto}.
     * Extracts and maps relevant data from the input object, including panel details,
     * location network spans, and PCP status.
     *
     * @param locationNetworkServiceInfo an object containing information about the location network services
     *                                   to be converted into a {@code LocationNetworkDto}
     * @return a {@code LocationNetworkDto} object populated with data extracted from
     *         the given {@code LocationNetworkServiceInfo}
     */
    private static LocationNetworkDto toLocationNetworkDto(LocationNetworkServiceInfo locationNetworkServiceInfo) {
        LocationNetworkDto locationNetworkDto = LocationNetworkDto.builder().build();
//        LocationNetworkServiceInfo locationNetworkServiceInfo = network.getNetworkServiceInfo();
        HasPanel hasPanel = locationNetworkServiceInfo.getHasPanel();
        List<RoleLocationServes> roleLocationServesList = locationNetworkServiceInfo.getRoleLocationServes();
        if (hasPanel != null) {
            PanelDto panelDto = PanelDto.builder()
                    .genderLimitation(hasPanel.getGenderLimitation())
                    .ageLimitation(hasPanel.getAgeLimitation())
                    .highestAgeMonths(hasPanel.getHighestAgeMonths())
                    .lowestAgeMonths(hasPanel.getLowestAgeMonths())
                    .highestAgeYears(hasPanel.getHighestAgeYears())
                    .lowestAgeYears(hasPanel.getLowestAgeYears())
                    .status(hasPanel.getStatus())
                    .build();
            locationNetworkDto.setPanel(panelDto);
        }
        if (roleLocationServesList != null && !roleLocationServesList.isEmpty()) {
            List<LocationNetworkSpanDto> spanDtos = new ArrayList<>();
            for (RoleLocationServes roleLocationServes : roleLocationServesList) {
                LocationNetworkSpanDto spanDto = LocationNetworkSpanDto.builder()
                        .startDate(roleLocationServes.getStartDate())
                        .endDate(roleLocationServes.getEndDate())
                        .termReason(roleLocationServes.getTermReason())
                        .build();
                spanDtos.add(spanDto);
            }
            locationNetworkDto.setSpans(spanDtos);
        }
        locationNetworkDto.setIsPCP(locationNetworkServiceInfo.getIsPCP());
        return locationNetworkDto;
//        networkDto.setLocationNetwork(locationNetworkDto);
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
