package com.brihaspathee.sapphire.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 12, November 2025
 * Time: 12:48
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
public class NetworkList {

    /**
     * Represents a collection of network data objects.
     * Each network in the list is encapsulated as a {@link NetworkDto} object,
     * which contains details such as the network's name, code, and additional
     * attributes related to its type and associated locations.
     */
    private List<NetworkDto> networks;

    /**
     * Provides a string representation of the NetworkList object.
     * The output includes the list of networks represented as a string.
     *
     * @return a string representation of the NetworkList object, including its networks.
     */
    @Override
    public String toString() {
        return "NetworkList{" +
                "networks=" + networks +
                '}';
    }
}
