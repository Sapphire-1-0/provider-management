package com.brihaspathee.sapphire.controller.impl;

import com.brihaspathee.sapphire.controller.interfaces.OrganizationAPI;
import com.brihaspathee.sapphire.domain.entity.Organization;
import com.brihaspathee.sapphire.model.OrganizationDto;
import com.brihaspathee.sapphire.model.OrganizationList;
import com.brihaspathee.sapphire.service.interfaces.IOrganizationService;
import com.brihaspathee.sapphire.web.response.SapphireAPIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
    private final IOrganizationService organizationService;

    /**
     * Creates a new organization based on the provided organization details.
     *
     * @param organizationDto the DTO containing organization details to be created
     * @return a ResponseEntity containing a SapphireAPIResponse encapsulating the created OrganizationDto
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<OrganizationDto>> createOrganization(
            OrganizationDto organizationDto) {
        SapphireAPIResponse<OrganizationDto> apiResponse =
                SapphireAPIResponse.<OrganizationDto>builder()
                        .statusCode(201)
                        .status(HttpStatus.CREATED)
                        .message("Organization created successfully")
                        .response(OrganizationDto.builder()
                                .name("Test Organization")
                                .build())
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
        SapphireAPIResponse<OrganizationDto> apiResponse =
                SapphireAPIResponse.<OrganizationDto>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Organization matched with id" + id +" was Retrieved successfully")
                        .response(OrganizationDto.builder()
                                .name("Test Organization")
                                .build())
                        .timestamp(LocalDateTime.now())
                        .reason("Organization matched with id" + id +" was Retrieved successfully")
                        .developerMessage("Organization matched with id" + id +" was Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves a list of organizations based on the provided set of identifiers.
     *
     * @param identifiers a map containing key-value pairs representing the identifiers used to filter organizations
     *                     (e.g., organization IDs, names, or codes)
     * @return a ResponseEntity containing a SapphireAPIResponse that encapsulates an OrganizationList,
     *         which includes the organizations matching the provided identifiers
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<OrganizationList>> getOrganizationsByIdentifiers(Map<String, String> identifiers) {
        List<OrganizationDto> organizations = organizationService.getOrganizationsByIdentifiers(identifiers);
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
}
