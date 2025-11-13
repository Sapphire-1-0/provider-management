package com.brihaspathee.sapphire.domain.entity;

import lombok.*;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 06, November 2025
 * Time: 05:26
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.entity
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Network {

    /**
     * Unique identifier for an element within the system.
     * This property is used to uniquely identify an instance of the class
     * or a related entity in the context of the application, ensuring
     * traceability and consistency across data operations.
     */
    private String elementId;

    /**
     * Represents a code that uniquely identifies the network.
     * This property may be used to categorize or group networks
     * within the system based on predefined codes.
     */
    private String code;

    /**
     * Represents the name of the network.
     * This property serves as a human-readable identifier or descriptor
     * for the network, providing additional context and clarity
     * about the specific network within the system.
     */
    private String name;

    /**
     * Indicates whether the network is classified as an HNET (Health Network) network.
     * HNET networks typically represent a specific type of healthcare network
     * designed to facilitate health-related communication and services
     * across participating entities.
     */
    private Boolean isHNETNetwork;

    /**
     * Indicates whether the network is a vendor network.
     * A vendor network typically refers to a network that is managed
     * or operated by an external vendor rather than being handled
     * internally by an organization. This property can help distinguish
     * between internally managed and vendor-managed networks within the system.
     */
    private Boolean isVendorNetwork;

    /**
     * Represents a list of products associated with the network.
     * Each product in this collection provides detailed information
     * about the specific offerings or services that are part of the network.
     * This property allows the network to manage and categorize its affiliated
     * products effectively within the system.
     */
    private List<Product> partOfProducts;

    /**
     * Represents a list of locations associated with the network.
     * Each location in the list contains detailed information
     * such as the name, address, city, state, ZIP code, county,
     * and FIPS code. This collection allows the network to
     * manage and reference multiple geographic locations.
     */
    private List<Location> locations;

    /**
     * Represents detailed information about the services and roles associated with a network location.
     * This property contains information such as whether the location is associated with a Primary Care Provider (PCP),
     * if it provides specialist or behavioral health services, panel constraints, and a list of roles
     * and their applicable details. It is used to manage and classify the network's service and role-related
     * functionalities.
     * <p>
     * This object will be populated only when Network is within the context of an Organization and Location.
     */
    private LocationNetworkServiceInfo networkServiceInfo;
}
