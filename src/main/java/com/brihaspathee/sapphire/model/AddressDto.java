package com.brihaspathee.sapphire.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 03, January 2026
 * Time: 10:33
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDto {

    /**
     * Represents the primary street address associated with an address.
     * This variable is used to store the main address information,
     * such as house number and street name.
     */
    private String streetAddress;

    /**
     * Represents the secondary address associated with an address.
     * This variable is used to store additional address information,
     * such as an apartment number, suite, or any other secondary location detail.
     */
    private String secondaryAddress;

    /**
     * Represents the city associated with the address.
     * This variable is used to store the specific city
     * where the address is located.
     */
    private String city;

    /**
     * Represents the state or province associated with the address.
     * This variable is used to store the specific state or province
     * where the address is located.
     */
    private String state;

    /**
     * Represents the ZIP code of an address.
     * This variable is used to store the postal code
     * associated with the address, which is typically
     * used to identify a specific location within a region.
     */
    private String zipCode;

    /**
     * Represents the name of the county associated with the address.
     * This variable is used to store the specific county within
     * which the address is located.
     */
    private String county;

    /**
     * Represents the Federal Information Processing Standards (FIPS) code for a county.
     * This variable is used to uniquely identify a county within the United States,
     * following the standardized FIPS coding system.
     */
    private String countyFIPS;

    /**
     * Generates a string representation of the AddressDto object.
     * The string includes the values of the streetAddress, secondaryAddress,
     * city, state, zipCode, county, and countyFIPS fields in a formatted structure.
     *
     * @return a string describing the state of the AddressDto object.
     */
    @Override
    public String toString() {
        return "AddressDto{" +
                "streetAddress='" + streetAddress + '\'' +
                ", secondaryAddress='" + secondaryAddress + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", county='" + county + '\'' +
                ", countyFIPS='" + countyFIPS + '\'' +
                '}';
    }
}
