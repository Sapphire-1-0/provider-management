package com.brihaspathee.sapphire.controller.impl;

import com.brihaspathee.sapphire.controller.interfaces.OrganizationAPI;
import com.brihaspathee.sapphire.model.OrganizationDto;
import com.brihaspathee.sapphire.model.OrganizationList;
import com.brihaspathee.sapphire.model.web.OrganizationSearchRequest;
import com.brihaspathee.sapphire.service.interfaces.OrganizationService;
import com.brihaspathee.sapphire.web.response.SapphireAPIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 5/9/25
 * Time: 1:48 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.controller.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class OrganizationAPIImpl implements OrganizationAPI {

    /**
     * Service instance that provides operations related to organizations.
     * This variable is used to invoke business logic for handling organization-specific tasks such
     * as retrieving, creating, or managing organization details.
     */
    private final OrganizationService organizationService;

    /**
     * Retrieves the details of an organization based on the provided organization code.
     *
     * @param orgCode the unique code identifying the organization to be retrieved
     * @return a ResponseEntity containing a SapphireAPIResponse encapsulating an OrganizationDto,
     * which represents the details of the requested organization
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<OrganizationDto>> getOrganizationByCode(String orgCode) {
        OrganizationDto organizationDto = organizationService.getOrganizationByCode(orgCode);
        SapphireAPIResponse<OrganizationDto> apiResponse =
                SapphireAPIResponse.<OrganizationDto>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Organization matched with code" + orgCode +" was Retrieved successfully")
                        .response(organizationDto)
                        .timestamp(LocalDateTime.now())
                        .reason("Organization matched with code" + orgCode +" was Retrieved successfully")
                        .developerMessage("Organization matched with code" + orgCode +" was Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Creates a new organization based on the provided organization details.
     *
     * @param organizationDto the DTO containing organization details to be created
     * @return a ResponseEntity containing a SapphireAPIResponse encapsulating the created OrganizationDto
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<OrganizationDto>> createOrganization(
            OrganizationDto organizationDto) {
        OrganizationDto createdOrganizationDto = organizationService.createOrganization(organizationDto);
        SapphireAPIResponse<OrganizationDto> apiResponse =
                SapphireAPIResponse.<OrganizationDto>builder()
                        .statusCode(201)
                        .status(HttpStatus.CREATED)
                        .message("Organization created successfully")
                        .response(createdOrganizationDto)
                        .timestamp(LocalDateTime.now())
                        .reason("Organization created successfully")
                        .developerMessage("Organization created successfully")
                        .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieves a list of organizations.
     *
     * @return a ResponseEntity containing a SapphireAPIResponse that encapsulates an OrganizationList,
     * which represents the collection of organizations.
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<OrganizationList>> getOrganizations() {
        List<OrganizationDto> organizations = organizationService.getAllOrganizations();

        SapphireAPIResponse<OrganizationList> apiResponse =
                SapphireAPIResponse.<OrganizationList>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Organization Retrieved successfully")
                        .response(OrganizationList.builder()
                                .organizationList(organizations)
                                .build())
                        .timestamp(LocalDateTime.now())
                        .reason("Organization Retrieved successfully")
                        .developerMessage("Organization Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves the details of a specific organization based on the provided organization ID.
     *
     * @param id the unique identifier of the organization to be retrieved
     * @return a ResponseEntity containing a SapphireAPIResponse that encapsulates an OrganizationDto,
     * which represents the details of the retrieved organization
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<OrganizationDto>> getOrganizationById(String id) {
        OrganizationDto organizationDto = organizationService.getOrganizationByElementId(id);
        SapphireAPIResponse<OrganizationDto> apiResponse =
                SapphireAPIResponse.<OrganizationDto>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Organization matched with id" + id +" was Retrieved successfully")
                        .response(organizationDto)
                        .timestamp(LocalDateTime.now())
                        .reason("Organization matched with id" + id +" was Retrieved successfully")
                        .developerMessage("Organization matched with id" + id +" was Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves the details of a specific organization and its associated network
     * based on the provided organization ID and network ID.
     *
     * @param orgId the unique identifier of the organization to be retrieved
     * @param netId the unique identifier of the network associated with the organization
     * @return a ResponseEntity containing a SapphireAPIResponse encapsulating an OrganizationDto,
     * which represents the details of the requested organization and network
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<OrganizationDto>> getOrganizationAndNetworkById(String orgId, String netId) {
        OrganizationDto organizationDto = organizationService.getOrgAndNetByElementId(orgId, netId);
        SapphireAPIResponse<OrganizationDto> apiResponse =
                SapphireAPIResponse.<OrganizationDto>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Organization matched with id" + orgId +" was Retrieved successfully")
                        .response(organizationDto)
                        .timestamp(LocalDateTime.now())
                        .reason("Organization matched with id" + orgId +" was Retrieved successfully")
                        .developerMessage("Organization matched with id" + orgId +" was Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves the details of a specific organization and its associated location
     * based on the provided organization ID and location ID.
     *
     * @param orgId the unique identifier of the organization to be retrieved
     * @param locId the unique identifier of the location associated with the organization
     * @return a ResponseEntity containing a SapphireAPIResponse encapsulating an OrganizationDto,
     * which represents the details of the requested organization and location
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<OrganizationDto>> getOrganizationAndLocationById(String orgId, String locId) {
        OrganizationDto organizationDto = organizationService.getOrgAndLocByElementId(orgId, locId);
        SapphireAPIResponse<OrganizationDto> apiResponse =
                SapphireAPIResponse.<OrganizationDto>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Organization matched with id" + orgId +" was Retrieved successfully")
                        .response(organizationDto)
                        .timestamp(LocalDateTime.now())
                        .reason("Organization matched with id" + orgId +" was Retrieved successfully")
                        .developerMessage("Organization matched with id" + orgId +" was Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves the details of a specific organization, its associated network,
     * and location based on the provided organization ID, network ID, and location ID.
     *
     * @param orgId the unique identifier of the organization to be retrieved
     * @param netId the unique identifier of the network associated with the organization
     * @param locId the unique identifier of the location associated with the organization
     * @return a ResponseEntity containing a SapphireAPIResponse encapsulating an OrganizationDto,
     * which represents the details of the organization, network, and location
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<OrganizationDto>> getOrgAndNetAndLocById(String orgId, String netId, String locId) {
        OrganizationDto organizationDto = organizationService.getOrgAndNetAndLocByElementId(orgId, netId, locId);
        SapphireAPIResponse<OrganizationDto> apiResponse =
                SapphireAPIResponse.<OrganizationDto>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Organization matched with id" + orgId +" was Retrieved successfully")
                        .response(organizationDto)
                        .timestamp(LocalDateTime.now())
                        .reason("Organization matched with id" + orgId +" was Retrieved successfully")
                        .developerMessage("Organization matched with id" + orgId +" was Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves organization and practitioner details based on the given organization ID and practitioner ID.
     *
     * @param orgId  the unique identifier for the organization
     * @param pracId the unique identifier for the practitioner
     * @return a ResponseEntity containing a SapphireAPIResponse with the organization details encapsulated in an OrganizationDto
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<OrganizationDto>> getOrgAndPracById(String orgId, String pracId) {
        OrganizationDto organizationDto = organizationService.getOrgAndPracByElementId(orgId, pracId);
        SapphireAPIResponse<OrganizationDto> apiResponse =
                SapphireAPIResponse.<OrganizationDto>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Organization matched with id" + orgId +" was Retrieved successfully")
                        .response(organizationDto)
                        .timestamp(LocalDateTime.now())
                        .reason("Organization matched with id" + orgId +" was Retrieved successfully")
                        .developerMessage("Organization matched with id" + orgId +" was Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves organization, practice, and location details based on the provided identifiers.
     *
     * @param orgId  the unique identifier of the organization
     * @param pracId the unique identifier of the practice
     * @param locId  the unique identifier of the location
     * @return a ResponseEntity containing the SapphireAPIResponse with OrganizationDto details
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<OrganizationDto>> getOrgAndPracAndLocById(String orgId, String pracId, String locId) {
        OrganizationDto organizationDto = organizationService.getOrgAndPracAndLocByElementId(orgId, pracId, locId);
        SapphireAPIResponse<OrganizationDto> apiResponse =
                SapphireAPIResponse.<OrganizationDto>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Organization matched with id" + orgId +" was Retrieved successfully")
                        .response(organizationDto)
                        .timestamp(LocalDateTime.now())
                        .reason("Organization matched with id" + orgId +" was Retrieved successfully")
                        .developerMessage("Organization matched with id" + orgId +" was Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves organization, practitioner, and network details by their respective IDs.
     *
     * @param orgId  the unique identifier of the organization
     * @param pracId the unique identifier of the practitioner
     * @param netId  the unique identifier of the network
     * @return a ResponseEntity containing a SapphireAPIResponse with an OrganizationDto object,
     * representing the organization details along with related practitioner and network information
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<OrganizationDto>> getOrgAndPracAndNetById(String orgId, String pracId, String netId) {
        OrganizationDto organizationDto = organizationService.getOrgAndPracAndNetByElementId(orgId, pracId, netId);
        SapphireAPIResponse<OrganizationDto> apiResponse =
                SapphireAPIResponse.<OrganizationDto>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Organization matched with id" + orgId +" was Retrieved successfully")
                        .response(organizationDto)
                        .timestamp(LocalDateTime.now())
                        .reason("Organization matched with id" + orgId +" was Retrieved successfully")
                        .developerMessage("Organization matched with id" + orgId +" was Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves detailed information about a specific organization, its associated practice,
     * network, and location based on the provided organization ID, practice ID,
     * network ID, and location ID.
     *
     * @param orgId  the unique identifier of the organization to be retrieved
     * @param pracId the unique identifier of the practice associated with the organization
     * @param netId  the unique identifier of the network associated with the organization and practice
     * @param locId  the unique identifier of the location associated with the organization, practice, and network
     * @return a ResponseEntity containing a SapphireAPIResponse encapsulating an OrganizationDto,
     * which represents the details of the specified organization, practice, network, and location
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<OrganizationDto>> getOrgAndPracAndNetAndLocById(String orgId, String pracId, String netId, String locId) {
        OrganizationDto organizationDto = organizationService.getOrgAndPracAndNetAndLocByElementId(orgId, pracId, netId, locId);
        SapphireAPIResponse<OrganizationDto> apiResponse =
                SapphireAPIResponse.<OrganizationDto>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Organization matched with id" + orgId +" was Retrieved successfully")
                        .response(organizationDto)
                        .timestamp(LocalDateTime.now())
                        .reason("Organization matched with id" + orgId +" was Retrieved successfully")
                        .developerMessage("Organization matched with id" + orgId +" was Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves a list of organizations based on the provided set of identifiers.
     *
     * @param organizationSearchRequest a map containing key-value pairs representing the identifiers used to filter organizations
     *                     (e.g., organization IDs, names, or codes)
     * @return a ResponseEntity containing a SapphireAPIResponse that encapsulates an OrganizationList,
     *         which includes the organizations matching the provided identifiers
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<OrganizationList>> getOrganizationsByIdentifiers(OrganizationSearchRequest organizationSearchRequest) {
        List<OrganizationDto> organizations = organizationService.getOrganizationsByIdentifiers(organizationSearchRequest);
        SapphireAPIResponse<OrganizationList> apiResponse =
                SapphireAPIResponse.<OrganizationList>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Organization Retrieved successfully")
                        .response(OrganizationList.builder()
                                .organizationList(organizations)
                                .build())
                        .timestamp(LocalDateTime.now())
                        .reason("Organization Retrieved successfully")
                        .developerMessage("Organization Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves the organization and its associated networks based on the provided organization ID.
     *
     * @param organizationId the unique identifier of the organization whose networks are to be retrieved
     * @return a ResponseEntity containing a SapphireAPIResponse encapsulating an OrganizationDto,
     *         which includes the organization details along with its networks
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<OrganizationDto>> getOrganizationNetworks(String organizationId) {
        OrganizationDto organizationDto = organizationService.getOrganizationAndNetworks(organizationId);
        SapphireAPIResponse<OrganizationDto> apiResponse =
                SapphireAPIResponse.<OrganizationDto>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Organization Retrieved successfully")
                        .response(organizationDto)
                        .timestamp(LocalDateTime.now())
                        .reason("Organization Retrieved successfully")
                        .developerMessage("Organization Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves the locations associated with a specific organization and network.
     *
     * @param orgId the unique identifier of the organization
     * @param netId the unique identifier of the network under the organization
     * @return a ResponseEntity containing a SapphireAPIResponse that encapsulates an OrganizationDto,
     *         which includes the locations associated with the specified organization and network
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<OrganizationDto>> getLocationsForOrgAndNet(String orgId, String netId) {
        OrganizationDto organizationDto = organizationService.getOrganizationNetworkLocations(orgId, netId);
        SapphireAPIResponse<OrganizationDto> apiResponse =
                SapphireAPIResponse.<OrganizationDto>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Organization Retrieved successfully")
                        .response(organizationDto)
                        .timestamp(LocalDateTime.now())
                        .reason("Organization Retrieved successfully")
                        .developerMessage("Organization Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves a list of locations associated with a specific organization based on the provided organization ID.
     *
     * @param organizationId the unique identifier of the organization whose locations are to be retrieved
     * @return a ResponseEntity containing a SapphireAPIResponse encapsulating an OrganizationDto,
     * which includes the list of locations related to the specified organization
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<OrganizationDto>> getOrganizationLocations(String organizationId) {
        OrganizationDto organizationDto = organizationService.getOrganizationAndLocations(organizationId);
        SapphireAPIResponse<OrganizationDto> apiResponse =
                SapphireAPIResponse.<OrganizationDto>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Organization Retrieved successfully")
                        .response(organizationDto)
                        .timestamp(LocalDateTime.now())
                        .reason("Organization Retrieved successfully")
                        .developerMessage("Organization Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves network details for a specific organization and location.
     *
     * @param orgId the unique identifier of the organization for which the networks need to be retrieved
     * @param locId
     * @return a ResponseEntity containing a SapphireAPIResponse encapsulating an OrganizationDto,
     * representing the network details for the specified organization and location
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<OrganizationDto>> getNetworksForOrgAndLoc(String orgId, String locId) {
        OrganizationDto organizationDto = organizationService.getOrganizationLocationNetworks(orgId, locId);
        SapphireAPIResponse<OrganizationDto> apiResponse =
                SapphireAPIResponse.<OrganizationDto>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Organization Retrieved successfully")
                        .response(organizationDto)
                        .timestamp(LocalDateTime.now())
                        .reason("Organization Retrieved successfully")
                        .developerMessage("Organization Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }
}
