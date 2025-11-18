package com.brihaspathee.sapphire.service.interfaces;

import com.brihaspathee.sapphire.domain.entity.Organization;
import com.brihaspathee.sapphire.model.OrganizationDto;
import com.brihaspathee.sapphire.model.web.OrganizationSearchRequest;

import java.util.List;
import java.util.Map;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 27, August 2025
 * Time: 05:20
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface IOrganizationService {

    /**
     * Inserts a new organization or updates an existing one based on the provided organization details.
     *
     * @param organizationDto an instance of {@code OrganizationDto} containing the details of the organization
     *                        to be inserted or updated, including its identifiers, name, type, and associated
     *                        information such as locations and networks
     */
    void upsertOrganization(OrganizationDto organizationDto);

    /**
     * Retrieves a list of all organizations.
     *
     * @return a list of OrganizationDto instances, where each instance represents
     *         the details of an individual organization
     */
    List<OrganizationDto> getAllOrganizations();

    /**
     * Retrieves a list of organizations that match the given identifiers.
     * The identifiers are provided as key-value pairs in a map, where the key
     * represents the type of identifier and the value represents the corresponding
     * identifier value.
     *
     * @param organizationSearchRequest a map of identifiers where the key is the type of identifier
     *                    and the value is the identifier value
     * @return a list of OrganizationDto instances that match the given identifiers
     */
    List<OrganizationDto> getOrganizationsByIdentifiers(OrganizationSearchRequest organizationSearchRequest);


    /**
     * Retrieves the organization details along with its associated networks
     * based on the provided element ID.
     *
     * @param elementId the unique identifier of the organization for which the
     *                  details and associated networks are being fetched
     * @return an instance of {@code OrganizationDto} containing the details of
     *         the organization along with its associated networks
     */
    OrganizationDto getOrganizationAndNetworks(String elementId);

    /**
     * Retrieves the network-specific location details of an organization
     * based on the provided organization ID and network ID.
     *
     * @param orgId the unique identifier of the organization whose network locations are being retrieved
     * @param netId the unique identifier of the network associated with the specified organization
     * @return an instance of {@code OrganizationDto} containing the details of the organization,
     *         including its associated network locations
     */
    OrganizationDto getOrganizationNetworkLocations(String orgId, String netId);

    /**
     * Retrieves the organization details along with its associated locations
     * based on the provided element ID.
     *
     * @param elementId the unique identifier of the organization for which the
     *                  details and associated locations are being fetched
     * @return an instance of {@code OrganizationDto} containing the details of
     *         the organization along with its associated locations
     */
    OrganizationDto getOrganizationAndLocations(String elementId);

    /**
     * Retrieves the organization details, including its location and network information,
     * based on the provided organization ID and network ID.
     *
     * @param orgId the unique identifier of the organization for which the details are being fetched
     * @param locId the unique identifier of the location associated with the specified organization
     * @return an instance of {@code OrganizationDto} containing the details of the organization,
     *         including its associated locations and networks
     */
    OrganizationDto getOrganizationLocationNetworks(String orgId, String locId);
}
