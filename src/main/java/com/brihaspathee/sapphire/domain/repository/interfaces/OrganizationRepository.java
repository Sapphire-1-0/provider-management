package com.brihaspathee.sapphire.domain.repository.interfaces;

import com.brihaspathee.sapphire.domain.entity.Organization;
import com.brihaspathee.sapphire.model.OrganizationDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 27, August 2025
 * Time: 05:18
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.repository
 * To change this template use File | Settings | File and Code Template
 */
@Repository
public interface OrganizationRepository {

    /**
     * Retrieves a list of organizations that match the provided code.
     *
     * @param code the unique code representing the organization
     * @return a list of Organization objects that have the specified code
     */
    List<Organization> findByCode(String code);

    /**
     * Retrieves an organization based on the provided unique element ID.
     *
     * @param elementId the unique identifier of the organization element to be retrieved
     * @return an Organization object that corresponds to the specified element ID,
     *         or null if no organization matches the given ID
     */
    Organization findOrganizationByElementId(String elementId);

    /**
     * Retrieves the organization and its associated networks based on the specified organization ID and network ID.
     *
     * @param orgId the unique identifier of the organization
     * @param netId the unique identifier of the network
     * @return an Organization object containing the organization details and its associated networks
     */
    Organization findOrgAndNetByElementId(String orgId, String netId);

    /**
     * Retrieves an organization and its associated locations based on the provided organization ID and location ID.
     *
     * @param orgId the unique identifier of the organization
     * @param locId the unique identifier of the location
     * @return an Organization object containing the details of the organization and its associated locations,
     *         or null if no matching organization is found
     */
    Organization findOrgAndLocByElementId(String orgId, String locId);

    /**
     * Retrieves the organization, its associated networks, and locations based on the specified identifiers.
     *
     * @param orgId the unique identifier of the organization
     * @param netId the unique identifier of the network
     * @param locId the unique identifier of the location
     * @return an Organization object containing the details of the organization, its networks, and locations,
     *         or null if no matching organization is found
     */
    Organization findOrgAndNetAndLocByElementId(String orgId, String netId, String locId);

    /**
     * Retrieves an organization and its associated practices based on the specified organization ID and practice ID.
     *
     * @param orgId the unique identifier of the organization
     * @param pracId the unique identifier of the practice
     * @return an Organization object containing the organization details and its associated practices,
     *         or null if no matching organization is found
     */
    Organization findOrgAndPracByElementId(String orgId, String pracId);

    /**
     * Retrieves an organization along with its associated practices and locations
     * based on the specified identifiers.
     *
     * @param orgId  the unique identifier of the organization
     * @param pracId the unique identifier of the practice
     * @param locId  the unique identifier of the location
     * @return an Organization object containing the details of the organization,
     *         its practices, and locations, or null if no matching organization is found
     */
    Organization findOrgAndPracAndLocByElementId(String orgId, String pracId, String locId);

    /**
     * Retrieves an organization along with its associated practices and networks
     * based on the specified organization ID, practice ID, and network ID.
     *
     * @param orgId  the unique identifier of the organization
     * @param pracId the unique identifier of the practice
     * @param netId  the unique identifier of the network
     * @return an Organization object containing the details of the organization,
     *         its practices, and networks, or null if no matching organization is found
     */
    Organization findOrgAndPracAndNetByElementId(String orgId, String pracId, String netId);

    /**
     * Retrieves an organization along with its associated practices, networks, and locations
     * based on the provided identifiers.
     *
     * @param orgId the unique identifier of the organization
     * @param pracId the unique identifier of the practice
     * @param netId the unique identifier of the network
     * @param locId the unique identifier of the location
     * @return an Organization object containing the organization details, practices, networks,
     *         and locations, or null if no matching organization is found
     */
    Organization findOrgAndPracAndNetAndLocByElementId(String orgId, String pracId, String netId, String locId);

    /**
     * Retrieves a list of all organizations.
     *
     * @return a list of Organization objects representing all organizations in the repository
     */
    List<Organization> findAll();

    /**
     * Retrieves a list of all organizations, along with their associated identifiers.
     *
     * @return a list of Organization objects, each containing details about the organization
     *         and its related identifiers
     */
    List<Organization> findAllWithIdentifiers();

    /**
     * Retrieves all organizations that match the provided identifiers.
     * Allows for matching either all specified identifiers or any of them,
     * based on the value of the matchAll parameter.
     *
     * @param identifiers a map of identifiers where the key represents the type of the identifier
     *                    (e.g., NPI, Tax ID) and the value represents the identifier value
     * @param matchAll    a boolean flag indicating the matching behavior:
     *                    true to match all specified identifiers, false to match any of them
     * @return a list of Organization objects that match the provided identifiers
     */
    List<Organization> findAllByIdentifier(Map<String, String> identifiers, boolean matchAll);

    /**
     * Retrieves an organization and all networks associated with it,
     * based on the provided element ID.
     *
     * @param elementId the unique identifier of the organization for which the networks are to be retrieved
     * @return an Organization object containing the organization details and its associated networks
     */
    Organization findAllOrganizationNetworks(String elementId);

    /**
     * Retrieves the organization and its associated locations based on the provided organization ID and network ID.
     *
     * @param orgId the unique identifier of the organization
     * @param netId the unique identifier of the network
     * @return an Organization object containing the organization details and its associated locations
     */
    Organization findLocationsByOrgAndNet(String orgId, String netId);

    /**
     * Retrieves an organization along with all its associated locations based on the provided element ID.
     *
     * @param elementId the unique identifier of the organization element to find associated locations
     * @return an Organization object containing the organization details and its associated locations
     */
    Organization findAllOrganizationLocations(String elementId);

    /**
     * Retrieves the details of networks associated with a specific organization and location.
     *
     * @param orgId the unique identifier of the organization
     * @param locId the unique identifier of the location
     * @return an Organization object containing the organization details and associated networks for the specified location
     */
    Organization findNetworksByOrgAndLoc(String orgId, String locId);


    /**
     * Creates a new organization using the details provided in the OrganizationDto object.
     *
     * @param organizationDto the data transfer object containing the details of the organization to be created
     * @return the newly created Organization object
     */
    Organization createOrganization(OrganizationDto organizationDto);

}
