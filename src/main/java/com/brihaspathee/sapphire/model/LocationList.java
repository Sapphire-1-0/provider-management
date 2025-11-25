package com.brihaspathee.sapphire.model;

import lombok.*;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 14, November 2025
 * Time: 05:16
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationList {

    /**
     * Represents a list of location data transfer objects.
     * This variable is used to store a collection of {@link LocationDto} objects,
     * each containing specific details about a location, such as identifiers,
     * addresses, and associated networks.
     */
    private List<LocationDto> locations;

    /**
     * Returns a string representation of the LocationList object.
     * The string includes the values of the locations property.
     *
     * @return a string representation of the LocationList object.
     */
    @Override
    public String toString() {
        return "LocationList{" +
                "locations=" + locations +
                '}';
    }
}
