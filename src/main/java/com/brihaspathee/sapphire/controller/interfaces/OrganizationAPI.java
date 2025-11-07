package com.brihaspathee.sapphire.controller.interfaces;

import com.brihaspathee.sapphire.model.OrganizationDto;
import com.brihaspathee.sapphire.model.OrganizationList;
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
     * @param identifiers a map where the key represents the type of identifier
     *                    and the value represents the specific identifier value
     *                    to filter organizations by
     * @return a ResponseEntity containing a SapphireAPIResponse encapsulating
     *         an OrganizationList, which represents a list of matching organizations
     */
    @PostMapping("/_search")
    ResponseEntity<SapphireAPIResponse<OrganizationList>> getOrganizationsByIdentifiers(@RequestBody Map<String, String> identifiers);

    @PostMapping("/{organizationId}/network/_search")
    ResponseEntity<SapphireAPIResponse<OrganizationDto>> getOrganizationNetworks(@PathVariable("organizationId") String organizationId);
}
