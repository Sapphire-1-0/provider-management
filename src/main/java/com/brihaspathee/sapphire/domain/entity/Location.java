package com.brihaspathee.sapphire.domain.entity;

import lombok.*;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 08, November 2025
 * Time: 06:48
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.entity
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    /**
     * Unique identifier for an element within the system.
     * This property is used to uniquely identify an instance of the class
     * or a related entity in the context of the application, ensuring
     * traceability and consistency across data operations.
     */
    private String elementId;

    /**
     * Represents the name of the location.
     * This property is used to store a human-readable identifier
     * or descriptor for the location, providing additional context
     * and clarity about the specific location within the system.
     */
    private String name;

    /**
     * Represents the primary street address of a location.
     * This property is used to store the main address information
     * such as the building number and street name, providing
     * the foundation for identifying a specific geographic location.
     */
    private String streetAddress;

    /**
     * Represents the secondary address of the location.
     * This property is used to store additional address details
     * such as apartment numbers, suite numbers, or other address
     * components that complement the primary street address.
     */
    private String secondaryAddress;

    /**
     * Represents the city where the location is situated.
     * This property is used to identify and store the name of the city
     * associated with a specific geographic location.
     */
    private String city;

    /**
     * Represents the state where the location is situated.
     * This property is used to identify and store the name of the state
     * associated with a specific geographic location.
     */
    private String state;

    /**
     * Represents the ZIP code of a location.
     * This property is used to identify and store the postal code
     * that corresponds to a specific geographic area or address.
     */
    private String zipCode;

    /**
     * Represents the county in which the location is situated.
     * This property is used to identify and store the name of the county
     * within a specific geographic address context.
     */
    private String county;

    /**
     * Represents the Federal Information Processing Standards (FIPS) code of a county.
     * The FIPS code is a unique identifier assigned to each county in the United States
     * for the purpose of geographic and statistical identification.
     */
    private String countyFIPS;

    /**
     * Represents a collection of identifiers associated with a location.
     * Identifiers can include unique attributes like NPI (National Provider Identifier),
     * TIN (Tax Identification Number), or other domain-specific identifiers.
     * These are used to uniquely identify and differentiate the location
     * in various contexts and operations within the system.
     */
    private List<Identifier> identifiers;

    /**
     * Represents the collection of networks associated with the location.
     * Each network in the list provides detailed information such as its
     * unique identifier, code, name, classification as an HNET or vendor network,
     * and associated locations. This property allows for tracking and managing
     * multiple networks that are linked to a specific location within the context of an organization.
     */
    private List<Network> networks;

    /**
     * Represents the network service information associated with a specific location.
     * This property contains detailed attributes about the services offered by the
     * location, such as whether it functions as a Primary Care Provider (PCP),
     * specializes in specific medical areas, supports behavioral health, and other
     * service-related roles or limitations. This information helps in classifying,
     * managing, and identifying the service capabilities of a location within the system.
     */
    private LocationNetworkServiceInfo networkServiceInfo;
}
