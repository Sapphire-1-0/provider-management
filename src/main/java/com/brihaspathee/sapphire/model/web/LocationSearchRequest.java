package com.brihaspathee.sapphire.model.web;

import lombok.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 14, November 2025
 * Time: 05:19
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.model.web
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationSearchRequest {

    /**
     * Represents the name of a location to be used in a search request.
     * The location name typically identifies the place being queried.
     */
    private String locationName;

    /**
     * Represents the primary street address for the location being searched for.
     * Typically includes the house number, street name, and other relevant details.
     */
    private String streetAddress;

    /**
     * Represents the secondary address for the location being searched for.
     * This could include additional address details such as apartment, suite,
     * or floor information to complement the primary street address.
     */
    private String secondaryAddress;

    /**
     * Represents the city for the location being searched for.
     * Typically used to specify the city where the location exists.
     */
    private String city;

    /**
     * Represents the state for the location being searched for.
     * Typically used to specify the state within the United States where
     * the location exists.
     */
    private String state;

    /**
     * Represents the Federal Information Processing Standards (FIPS) code
     * associated with the county for the location being searched for.
     */
    private String countyFIPS;

    /**
     * Represents the zip code of the location being searched for.
     */
    private String zipCode;
}
