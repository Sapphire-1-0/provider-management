package com.brihaspathee.sapphire.domain.repository.interfaces;

import com.brihaspathee.sapphire.domain.entity.Organization;
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
}
