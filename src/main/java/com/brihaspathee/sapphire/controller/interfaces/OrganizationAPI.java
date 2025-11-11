package com.brihaspathee.sapphire.controller.interfaces;

import com.brihaspathee.sapphire.model.OrganizationDto;
import com.brihaspathee.sapphire.model.OrganizationList;
import com.brihaspathee.sapphire.model.web.OrganizationSearchRequest;
import com.brihaspathee.sapphire.web.response.SapphireAPIResponse;
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
@RequestMapping("/api/v1/sapphire/organization/private")
public interface OrganizationAPI {

    /**
     * Creates a new organization based on the provided organization details.
     *
     * @param organizationDto the DTO containing organization details to be created
     * @return a ResponseEntity containing a SapphireAPIResponse encapsulating the created OrganizationDto
     */
    @PostMapping("/create-org")
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
     * Retrieves the details of a specific organization based on the provided organization ID.
     *
     * @param id the unique identifier of the organization to be retrieved
     * @return a ResponseEntity containing a SapphireAPIResponse that encapsulates an OrganizationDto,
     * which represents the details of the retrieved organization
     */
    @GetMapping("/{organizationId}")
    ResponseEntity<SapphireAPIResponse<OrganizationDto>> getOrganizationById(@PathVariable("organizationId") String id);

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
    ResponseEntity<SapphireAPIResponse<OrganizationDto>> getNetworksForOrgAndLoc(@PathVariable("orgId") String orgId, @PathVariable("locId") String netId);
}
