package com.brihaspathee.sapphire.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 08, November 2025
 * Time: 06:54
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationDto {

    /**
     * Represents a unique identifier for an element.
     * This variable is used to reference or track a specific entity
     * within its respective context or scope.
     */
    private String elementId;

    /**
     * Represents the name associated with a location.
     * This variable is used to store the name or descriptive title
     * that identifies a specific location within a given context.
     */
    private String name;

    /**
     * Represents the primary street address associated with a location.
     * This variable is used to store the main address line, which typically
     * includes details such as the street name, number, or other primary
     * location-specific information. It serves as the core component
     * of the physical address for identifying a precise location.
     */
    private String streetAddress;

    /**
     * Represents the secondary address associated with a location.
     * This variable is used to store additional address information,
     * such as apartment numbers, suite details, or other sub-addresses
     * that complement the primary street address.
     */
    private String secondaryAddress;

    /**
     * Represents the city associated with a location.
     * This variable is used to identify the name of the city
     * for a particular address or geographic location.
     */
    private String city;

    /**
     * Represents the state or province associated with a location.
     * This variable is used to specify the administrative subdivision
     * (e.g., state, province, or region) relevant to the address
     * or location context within the system.
     */
    private String state;

    /**
     * Represents the postal, ZIP, or PIN code for a location.
     * This variable is used to store the code associated with a specific
     * geographic area, typically for mailing and addressing purposes.
     */
    private String zipCode;

    /**
     * Represents the name of the county associated with a location.
     * This variable is used to specify the county in which a particular
     * location resides or is related to.
     */
    private String county;

    /**
     * Represents the Federal Information Processing Standards (FIPS) code
     * for a county. This variable is used to uniquely identify a county
     * within the United States as per the FIPS standard.
     */
    private String countyFIPS;

    /**
     * Represents a collection of networks associated with a specific location.
     * This variable is used to store a list of {@link NetworkDto} objects, each
     * representing details about an individual network, such as its name, code,
     * and specific characteristics like whether it is part of an HNET or vendor network.
     * These networks provide context and structure for managing associated network data
     * within the system.
     */
    private List<NetworkDto> networks;

    /**
     * Represents the network-related information associated with a specific location.
     * This variable is used to store an instance of {@link LocationNetworkDto}, which
     * contains details such as panel criteria, network categorizations (e.g., PCP, specialist,
     * behavioral health), and network span data. The location network encapsulates
     * attributes defining the characteristics and classifications of the network or
     * provider associated with the location.
     */
    private LocationNetworkDto locationNetwork;

}
