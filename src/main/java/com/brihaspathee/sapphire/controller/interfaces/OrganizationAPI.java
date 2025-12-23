package com.brihaspathee.sapphire.controller.interfaces;

import com.brihaspathee.sapphire.model.OrganizationDto;
import com.brihaspathee.sapphire.model.OrganizationList;
import com.brihaspathee.sapphire.model.web.OrganizationSearchRequest;
import com.brihaspathee.sapphire.web.response.SapphireAPIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 5/9/25
 * Time: 1:36 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.controller.interfaces
 * To change this template use File | Settings | File and Code Template
 */
@Tag(name = "Organizations", description = "APIs for managing organizations")
@RequestMapping("/api/v1/sapphire/organization")
public interface OrganizationAPI {

    /**
     * Retrieves the details of an organization based on the provided organization code.
     *
     * @param orgCode the unique code identifying the organization to be retrieved
     * @return a ResponseEntity containing a SapphireAPIResponse encapsulating an OrganizationDto,
     *         which represents the details of the requested organization
     */
    @GetMapping("/code/{orgCode}")
    ResponseEntity<SapphireAPIResponse<OrganizationDto>> getOrganizationByCode(@PathVariable("orgCode") String orgCode);

    /**
     * Retrieves the details of a specific organization based on the provided organization ID.
     *
     * @param id the unique identifier of the organization to be retrieved
     * @return a ResponseEntity containing a SapphireAPIResponse that encapsulates an OrganizationDto,
     * which represents the details of the retrieved organization
     */
    @Operation(
            summary = "Retrieves the details of a specific organization based on the provided organization ID.",
            description = "Retrieves the details of a specific organization based on the provided organization ID.",
            parameters = {
                    @Parameter(name = "organizationId", description = "The unique identifier of the organization to be retrieved.", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "The details of the organization were successfully retrieved.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = OrganizationDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "The provided organization ID is invalid.",
                            content = @Content()
                    )
            }
    )
    @GetMapping("/{orgId}")
    ResponseEntity<SapphireAPIResponse<OrganizationDto>> getOrganizationById(@PathVariable("orgId") String id);

    /**
     * Retrieves the details of a specific organization and its associated network
     * based on the provided organization ID and network ID.
     *
     * @param orgId the unique identifier of the organization to be retrieved
     * @param netId the unique identifier of the network associated with the organization
     * @return a ResponseEntity containing a SapphireAPIResponse encapsulating an OrganizationDto,
     *         which represents the details of the requested organization and network
     */
    @GetMapping("/{orgId}/network/{netId}")
    ResponseEntity<SapphireAPIResponse<OrganizationDto>> getOrganizationAndNetworkById(@PathVariable("orgId") String orgId,
                                                                                       @PathVariable("netId") String netId);

    /**
     * Retrieves the details of a specific organization and its associated location
     * based on the provided organization ID and location ID.
     *
     * @param orgId the unique identifier of the organization to be retrieved
     * @param locId the unique identifier of the location associated with the organization
     * @return a ResponseEntity containing a SapphireAPIResponse encapsulating an OrganizationDto,
     *         which represents the details of the requested organization and location
     */
    @GetMapping("/{orgId}/location/{locId}")
    ResponseEntity<SapphireAPIResponse<OrganizationDto>> getOrganizationAndLocationById(@PathVariable("orgId") String orgId,
                                                                                       @PathVariable("locId") String locId);

    /**
     * Retrieves the details of a specific organization, its associated network,
     * and location based on the provided organization ID, network ID, and location ID.
     *
     * @param orgId the unique identifier of the organization to be retrieved
     * @param netId the unique identifier of the network associated with the organization
     * @param locId the unique identifier of the location associated with the organization
     * @return a ResponseEntity containing a SapphireAPIResponse encapsulating an OrganizationDto,
     *         which represents the details of the organization, network, and location
     */
    @GetMapping("/{orgId}/network/{netId}/location/{locId}")
    ResponseEntity<SapphireAPIResponse<OrganizationDto>> getOrgAndNetAndLocById(@PathVariable("orgId") String orgId,
                                                                                @PathVariable("netId") String netId,
                                                                                @PathVariable("locId") String locId);

    /**
     * Retrieves organization and practitioner details based on the given organization ID and practitioner ID.
     *
     * @param orgId the unique identifier for the organization
     * @param pracId the unique identifier for the practitioner
     * @return a ResponseEntity containing a SapphireAPIResponse with the organization details encapsulated in an OrganizationDto
     */
    @GetMapping("/{orgId}/practitioner/{pracId}")
    ResponseEntity<SapphireAPIResponse<OrganizationDto>> getOrgAndPracById(@PathVariable("orgId") String orgId,
                                                                            @PathVariable("pracId") String pracId);

    /**
     * Retrieves organization, practice, and location details based on the provided identifiers.
     *
     * @param orgId the unique identifier of the organization
     * @param pracId the unique identifier of the practice
     * @param locId the unique identifier of the location
     * @return a ResponseEntity containing the SapphireAPIResponse with OrganizationDto details
     */
    @GetMapping("/{orgId}/practitioner/{pracId}/location/{locId}")
    ResponseEntity<SapphireAPIResponse<OrganizationDto>> getOrgAndPracAndLocById(@PathVariable("orgId") String orgId,
                                                                                    @PathVariable("pracId") String pracId,
                                                                                    @PathVariable("locId") String locId);

    /**
     * Retrieves organization, practitioner, and network details by their respective IDs.
     *
     * @param orgId the unique identifier of the organization
     * @param pracId the unique identifier of the practitioner
     * @param netId the unique identifier of the network
     * @return a ResponseEntity containing a SapphireAPIResponse with an OrganizationDto object,
     *         representing the organization details along with related practitioner and network information
     */
    @GetMapping("/{orgId}/practitioner/{pracId}/network/{netId}")
    ResponseEntity<SapphireAPIResponse<OrganizationDto>> getOrgAndPracAndNetById(@PathVariable("orgId") String orgId,
                                                                                    @PathVariable("pracId") String pracId,
                                                                                    @PathVariable("netId") String netId);

    /**
     * Retrieves detailed information about a specific organization, its associated practice,
     * network, and location based on the provided organization ID, practice ID,
     * network ID, and location ID.
     *
     * @param orgId the unique identifier of the organization to be retrieved
     * @param pracId the unique identifier of the practice associated with the organization
     * @param netId the unique identifier of the network associated with the organization and practice
     * @param locId the unique identifier of the location associated with the organization, practice, and network
     * @return a ResponseEntity containing a SapphireAPIResponse encapsulating an OrganizationDto,
     * which represents the details of the specified organization, practice, network, and location
     */
    @GetMapping("/{orgId}/practitioner/{pracId}/network/{netId}/location/{locId}")
    ResponseEntity<SapphireAPIResponse<OrganizationDto>> getOrgAndPracAndNetAndLocById(@PathVariable("orgId") String orgId,
                                                                                        @PathVariable("pracId") String pracId,
                                                                                        @PathVariable("netId") String netId,
                                                                                        @PathVariable("locId") String locId);

    /**
     * Creates a new organization based on the provided organization details.
     *
     * @param organizationDto the DTO containing organization details to be created
     * @return a ResponseEntity containing a SapphireAPIResponse encapsulating the created OrganizationDto
     */
    @PostMapping("/_upsert")
    ResponseEntity<SapphireAPIResponse<OrganizationDto>> createOrganization(
            @RequestBody OrganizationDto organizationDto);

    /**
     * Retrieves a list of organizations.
     *
     * @return a ResponseEntity containing a SapphireAPIResponse that encapsulates an OrganizationList,
     * representing the collection of organizations.
     */
    @GetMapping("/get-orgs")
    ResponseEntity<SapphireAPIResponse<OrganizationList>> getOrganizations();


    /**
     * Retrieves a list of organizations based on the specified identifiers.
     *
     * @param organizationSearchRequest The organization search request.
     * @return a ResponseEntity containing a SapphireAPIResponse encapsulating
     *         an OrganizationList, which represents a list of matching organizations
     */
    @PostMapping("/_search")
    ResponseEntity<SapphireAPIResponse<OrganizationList>> getOrganizationsByIdentifiers(@RequestBody OrganizationSearchRequest organizationSearchRequest);

    /**
     * Retrieves network details associated with a specific organization based on the provided organization ID.
     *
     * @param organizationId the unique identifier of the organization whose networks are to be retrieved
     * @return a ResponseEntity containing a SapphireAPIResponse encapsulating an OrganizationDto,
     *         which includes the network details related to the specified organization
     */
    @PostMapping("/{organizationId}/network/_search")
    ResponseEntity<SapphireAPIResponse<OrganizationDto>> getOrganizationNetworks(@PathVariable("organizationId") String organizationId);

    /**
     * Retrieves a list of locations associated with a specific organization and network
     * based on the provided organization ID and network ID.
     *
     * @param orgId the unique identifier of the organization for which locations are to be retrieved
     * @param netId the unique identifier of the network associated with the organization whose locations are to be retrieved
     * @return a ResponseEntity containing a SapphireAPIResponse that encapsulates an OrganizationDto,
     *         which includes the list of locations related to the specified organization and network
     */
    @PostMapping("/{orgId}/network/{netId}/location/_search")
    ResponseEntity<SapphireAPIResponse<OrganizationDto>> getLocationsForOrgAndNet(@PathVariable("orgId") String orgId, @PathVariable("netId") String netId);

    /**
     * Retrieves a list of locations associated with a specific organization based on the provided organization ID.
     *
     * @param organizationId the unique identifier of the organization whose locations are to be retrieved
     * @return a ResponseEntity containing a SapphireAPIResponse encapsulating an OrganizationDto,
     *         which includes the list of locations related to the specified organization
     */
    @PostMapping("/{organizationId}/location/_search")
    ResponseEntity<SapphireAPIResponse<OrganizationDto>> getOrganizationLocations(@PathVariable("organizationId") String organizationId);

    /**
     * Retrieves network details for a specific organization and location.
     *
     * @param orgId the unique identifier of the organization for which the networks need to be retrieved
     * @param locId the unique identifier of the location associated with the organization
     * @return a ResponseEntity containing a SapphireAPIResponse encapsulating an OrganizationDto,
     *         representing the network details for the specified organization and location
     */
    @PostMapping("/{orgId}/location/{locId}/network/_search")
    ResponseEntity<SapphireAPIResponse<OrganizationDto>> getNetworksForOrgAndLoc(@PathVariable("orgId") String orgId, @PathVariable("locId") String locId);
}
