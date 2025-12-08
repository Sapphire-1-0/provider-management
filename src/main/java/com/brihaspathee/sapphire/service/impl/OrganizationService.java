package com.brihaspathee.sapphire.service.impl;

import com.brihaspathee.sapphire.domain.entity.*;
import com.brihaspathee.sapphire.domain.repository.interfaces.OrganizationRepository;
import com.brihaspathee.sapphire.mapper.interfaces.OrganizationMapper;
import com.brihaspathee.sapphire.model.*;
import com.brihaspathee.sapphire.model.web.IdentifierInfo;
import com.brihaspathee.sapphire.model.web.OrganizationSearchRequest;
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


//    /**
//     * Converts an {@link Organization} entity into an {@link OrganizationDto} object.
//     *
//     * @param organization the {@link Organization} entity that needs to be transformed into a DTO
//     * @return the resulting {@link OrganizationDto} containing all necessary details of the organization
//     */
//    private OrganizationDto toOrganizationDto(Organization organization) {
//        log.info("Organization in service: {}", organization);
//        List<IdentifierDto> identifierDtos = null;
//        List<NetworkDto> networkDtos = null;
//        List<LocationDto> locationDtos = null;
//        if (organization.getIdentifiers() != null && !organization.getIdentifiers().isEmpty()){
//            identifierDtos = organization.getIdentifiers().stream().map(identifier -> {
//                return IdentifierDto.builder()
//                        .elementId(identifier.getElementId())
//                        .value(identifier.getValue())
//                        .type(identifier.getClass().getSimpleName())
//                        .startDate(identifier.getStartDate())
//                        .endDate(identifier.getEndDate())
//                        .additionalProperties(getAdditionalProperties(identifier))
//                        .build();
//            }).toList();
//        }
//        if (organization.getNetworks() != null && !organization.getNetworks().isEmpty()) {
//            networkDtos = organization.getNetworks().stream().map(network -> {
//                NetworkDto networkDto = NetworkDto.builder()
//                        .elementId(network.getElementId())
//                        .name(network.getName())
//                        .code(network.getCode())
//                        .isHNETNetwork(network.getIsHNETNetwork())
//                        .isVendorNetwork(network.getIsVendorNetwork())
//                        .build();
//                if (network.getLocations() != null && !network.getLocations().isEmpty()) {
//                    networkDto.setLocations(network.getLocations().stream().map(location -> {
//                        LocationDto locationDto =  LocationDto.builder()
//                                .elementId(location.getElementId())
//                                .name(location.getName())
//                                .streetAddress(location.getStreetAddress())
//                                .secondaryAddress(location.getSecondaryAddress())
//                                .city(location.getCity())
//                                .state(location.getState())
//                                .zipCode(location.getZipCode())
//                                .build();
//                        if (location.getNetworkServiceInfo() != null){
//                            LocationNetworkDto locationNetworkDto = toLocationNetworkDto(location.getNetworkServiceInfo());
//                            locationDto.setLocationNetwork(locationNetworkDto);
//                        }
//                        return locationDto;
//                    }).toList());
//                }
//                return networkDto;
//            }).toList();
//        }
//        if(organization.getLocations() != null && !organization.getLocations().isEmpty()){
//            locationDtos = organization.getLocations().stream().map(location -> {
//                LocationDto locationDto = LocationDto.builder()
//                        .elementId(location.getElementId())
//                        .name(location.getName())
//                        .streetAddress(location.getStreetAddress())
//                        .secondaryAddress(location.getSecondaryAddress())
//                        .city(location.getCity())
//                        .state(location.getState())
//                        .zipCode(location.getZipCode())
//                        .county(location.getCounty())
//                        .countyFIPS(location.getCountyFIPS())
//                        .build();
//                if (location.getNetworks() != null && !location.getNetworks().isEmpty()) {
//                    locationDto.setNetworks(location.getNetworks().stream().map(network -> {
//                        NetworkDto networkDto =  NetworkDto.builder()
//                                .elementId(network.getElementId())
//                                .name(network.getName())
//                                .code(network.getCode())
//                                .build();
//                        if (network.getNetworkServiceInfo() != null){
//                            LocationNetworkDto locationNetworkDto = toLocationNetworkDto(network.getNetworkServiceInfo());
//                            networkDto.setLocationNetwork(locationNetworkDto);
//                        }
//                        return networkDto;
//                    }).toList());
//                }
//                return locationDto;
//            }).toList();
//        }
//        OrganizationDto organizationDto = OrganizationDto.builder()
//                .elementId(organization.getElementId())
//                .name(organization.getName())
//                .aliasName(organization.getAliasName())
//                .type(organization.getType())
//                .atypical(organization.getAtypical())
//                .capitated(organization.getCapitated())
//                .pcpPractitionerRequired(organization.getPcpPractitionerRequired())
//                .identifiers(identifierDtos)
//                .networks(networkDtos)
//                .locations(locationDtos)
//                .build();
//        log.info("Organization DTO in service: {}", organizationDto);
//        return organizationDto;
//    }

//    /**
//     * Converts a {@code LocationNetworkServiceInfo} object to a {@code LocationNetworkDto}.
//     * Extracts and maps relevant data from the input object, including panel details,
//     * location network spans, and PCP status.
//     *
//     * @param locationNetworkServiceInfo an object containing information about the location network services
//     *                                   to be converted into a {@code LocationNetworkDto}
//     * @return a {@code LocationNetworkDto} object populated with data extracted from
//     *         the given {@code LocationNetworkServiceInfo}
//     */
//    private static LocationNetworkDto toLocationNetworkDto(LocationNetworkServiceInfo locationNetworkServiceInfo) {
//        LocationNetworkDto locationNetworkDto = LocationNetworkDto.builder().build();
////        LocationNetworkServiceInfo locationNetworkServiceInfo = network.getNetworkServiceInfo();
//        HasPanel hasPanel = locationNetworkServiceInfo.getHasPanel();
//        List<RoleLocationServes> roleLocationServesList = locationNetworkServiceInfo.getRoleLocationServes();
//        if (hasPanel != null) {
//            PanelDto panelDto = PanelDto.builder()
//                    .genderLimitation(hasPanel.getGenderLimitation())
//                    .ageLimitation(hasPanel.getAgeLimitation())
//                    .highestAgeMonths(hasPanel.getHighestAgeMonths())
//                    .lowestAgeMonths(hasPanel.getLowestAgeMonths())
//                    .highestAgeYears(hasPanel.getHighestAgeYears())
//                    .lowestAgeYears(hasPanel.getLowestAgeYears())
//                    .status(hasPanel.getStatus())
//                    .build();
//            locationNetworkDto.setPanel(panelDto);
//        }
//        if (roleLocationServesList != null && !roleLocationServesList.isEmpty()) {
//            List<LocationNetworkSpanDto> spanDtos = new ArrayList<>();
//            for (RoleLocationServes roleLocationServes : roleLocationServesList) {
//                LocationNetworkSpanDto spanDto = LocationNetworkSpanDto.builder()
//                        .startDate(roleLocationServes.getStartDate())
//                        .endDate(roleLocationServes.getEndDate())
//                        .termReason(roleLocationServes.getTermReason())
//                        .build();
//                spanDtos.add(spanDto);
//            }
//            locationNetworkDto.setSpans(spanDtos);
//        }
//        locationNetworkDto.setIsPCP(locationNetworkServiceInfo.getIsPCP());
//        return locationNetworkDto;
////        networkDto.setLocationNetwork(locationNetworkDto);
//    }
//
//    /**
//     * Constructs a map of additional properties based on the type of the provided identifier.
//     * If the identifier is of type {@code MedicaidID}, its state is added to the map.
//     * If the identifier is of type {@code TIN}, its legal name is added to the map.
//     *
//     * @param identifier the identifier whose additional properties need to be collected
//     * @return a map containing additional properties specific to the identifier type
//     */
//    private Map<String, Object> getAdditionalProperties(Identifier identifier) {
//        Map<String, Object> additionalProperties = new HashMap<>();
//        if (identifier instanceof MedicaidID medicaidID) {
//            additionalProperties.put("state",medicaidID.getState());
//        }
//        if (identifier instanceof TIN tin) {
//            additionalProperties.put("legalName", tin.getLegalName());
//        }
//        return additionalProperties;
//    }
}
