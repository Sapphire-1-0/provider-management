package com.brihaspathee.sapphire.service.interfaces;

import com.brihaspathee.sapphire.model.OrganizationDto;
import com.brihaspathee.sapphire.model.web.OrganizationSearchRequest;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 27, August 2025
 * Time: 05:20
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface OrganizationService {

    /**
     * Retrieves the details of an organization based on the provided organization code.
     *
     * @param code the unique code of the organization to be retrieved
     * @return an instance of {@code OrganizationDto} containing the details of the organization
     *         that corresponds to the specified code, or {@code null} if no such organization exists
     */
    OrganizationDto getOrganizationByCode(String code);

    /**
     * Retrieves the details of an organization based on the provided element ID.
     *
     * @param orgId the unique identifier of the element associated with the organization
     * @return an instance of {@code OrganizationDto} containing the details of the
     *         organization that corresponds to the specified element ID, or {@code null}
     *         if no such organization exists
     */
    OrganizationDto getOrganizationByElementId(String orgId);

    /**
     * Retrieves the organization details along with its associated network information
     * based on the provided organization ID and network ID.
     *
     * @param orgId the unique identifier of the organization for which the details are being retrieved
     * @param netId the unique identifier of the network associated with the specified organization
     * @return an instance of {@code OrganizationDto} containing the details of the organization,
     *         including its associated network information
     */
    OrganizationDto getOrgAndNetByElementId(String orgId, String netId);

    /**
     * Retrieves the organization details along with its associated location information
     * based on the provided organization ID and location ID.
     *
     * @param orgId the unique identifier of the organization for which the details are being retrieved
     * @param locId the unique identifier of the location associated with the specified organization
     * @return an instance of {@code OrganizationDto} containing the details of the organization,
     *         including its associated location information, or {@code null} if no such organization exists
     */
    OrganizationDto getOrgAndLocByElementId(String orgId, String locId);

    /**
     * Retrieves the organization details, including its network and location information,
     * based on the provided organization ID, network ID, and location ID.
     *
     * @param orgId the unique identifier of the organization for which details are being retrieved
     * @param netId the unique identifier of the network associated with the specified organization
     * @param locId the unique identifier of the location associated with the specified organization
     * @return an instance of {@code OrganizationDto} containing the details of the organization,
     *         including its associated network and location information, or {@code null} if
     *         no such organization exists
     */
    OrganizationDto getOrgAndNetAndLocByElementId(String orgId, String netId, String locId);

    /**
     * Retrieves the organization details along with its associated practice information
     * based on the provided organization ID and practice ID.
     *
     * @param orgId the unique identifier of the organization for which the details are being retrieved
     * @param pracId the unique identifier of the practice associated with the specified organization
     * @return an instance of {@code OrganizationDto} containing the details of the organization,
     *         including its associated practice information, or {@code null} if no such organization exists
     */
    OrganizationDto getOrgAndPracByElementId(String orgId, String pracId);

    /**
     * Retrieves the organization details, including its associated practices and locations,
     * based on the provided organization ID, practice ID, and location ID.
     *
     * @param orgId the unique identifier of the organization for which details are being retrieved
     * @param pracId the unique identifier of the practice associated with the specified organization
     * @param locId the unique identifier of the location associated with the specified organization
     * @return an instance of {@code OrganizationDto} containing the details of the organization,
     *         including its associated practices and locations, or {@code null} if no such organization exists
     */
    OrganizationDto getOrgAndPracAndLocByElementId(String orgId, String pracId, String locId);

    /**
     * Retrieves the details of an organization, including its associated practices
     * and networks, based on the provided organization ID, practice ID, and network ID.
     *
     * @param orgId the unique identifier of the organization for which the details are being retrieved
     * @param pracId the unique identifier of the practice associated with the specified organization
     * @param netId the unique identifier of the network associated with the specified organization
     * @return an instance of {@code OrganizationDto} containing the details of the organization,
     *         including its associated practices and networks, or {@code null} if no such organization exists
     */
    OrganizationDto getOrgAndPracAndNetByElementId(String orgId, String pracId, String netId);

    /**
     * Retrieves the details of an organization, including its associated practices, networks,
     * and locations, based on the provided organization ID, practice ID, network ID, and location ID.
     *
     * @param orgId the unique identifier of the organization for which the details are being retrieved
     * @param pracId the unique identifier of the practice associated with the specified organization
     * @param netId the unique identifier of the network associated with the specified organization
     * @param locId the unique identifier of the location associated with the specified organization
     * @return an instance of {@code OrganizationDto} containing the details of the organization,
     *         including its associated practices, networks, and locations, or {@code null} if no such
     *         organization exists
     */
    OrganizationDto getOrgAndPracAndNetAndLocByElementId(String orgId, String pracId, String netId, String locId);

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

    /**
     * Creates a new organization based on the provided organization details.
     *
     * @param organizationDto an instance of {@code OrganizationDto} containing the details of the organization
     *                        to be created, including its identifiers, name, type, and associated information
     *                        such as locations and networks.
     * @return an instance of {@code OrganizationDto} representing the newly created organization, including
     *         its identifier and any additional generated information.
     */
    OrganizationDto createOrganization(OrganizationDto organizationDto);
}
