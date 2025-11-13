package com.brihaspathee.sapphire.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 07, November 2025
 * Time: 04:58
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NetworkDto {

    /**
     * Represents a unique identifier for an element.
     * This variable is used to reference or track a specific entity
     * within its respective context or scope.
     */
    private String elementId;
    /**
     * Represents the name of the network.
     * This variable is used to store the name of a network
     * within the system.
     */
    private String name;

    /**
     * Represents the code associated with the network.
     * This variable is used to store a specific code that identifies
     * or categorizes the network within the system.
     */
    private String code;

    /**
     * Indicates whether the network is an HNET network.
     * This variable is used to differentiate networks that are
     * categorized as HNET-specific from other types of networks
     * within the system.
     */
    private Boolean isHNETNetwork;

    /**
     * Indicates whether the network is categorized as a vendor network.
     * This variable is used to specify if the network is associated with or
     * provided by a vendor as opposed to other types of networks.
     */
    private Boolean isVendorNetwork;

    /**
     * Represents a list of products that the network is part of.
     * This variable is used to store a collection of {@link ProductDto} objects
     * that detail the products associated with the network.
     */
    private List<ProductDto> partOfProducts;


    /**
     * Represents a list of location details.
     * This variable is used to store a collection of location-related data
     * objects, encapsulated within the {@link LocationDto} class.
     * Each object in the list represents a specific location, containing
     * attributes such as address, city, state, and other geographic details.
     */
    private List<LocationDto> locations;

    /**
     * Represents the network details associated with a specific location.
     * This variable is used to store information encapsulated in the {@link LocationNetworkDto} class.
     * It provides details such as panel information, categorization of the network as a primary care
     * provider, specialist, or behavioral health, and any associated spans that define
     * durations or additional attributes related to the location's network.
     */
    private LocationNetworkDto locationNetwork;
}
