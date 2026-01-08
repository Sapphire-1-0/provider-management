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
public class ContactDto {

    /**
     * Represents the type or purpose of the contact.
     * This variable is used to classify or define the specific usage or context
     * of the contact, such as home, work, or other relevant categories.
     */
    private String use;

    /**
     * Represents the address information associated with the contact.
     * This variable holds an instance of the AddressDto class, which encapsulates
     * various address-related details such as street address, city, state, ZIP code,
     * and other relevant location data. It is used to define the physical or mailing
     * address information for a contact.
     */
    private AddressDto address;

    /**
     * Represents the telecommunications details associated with the contact.
     * This variable holds an instance of the TelecomDto class, which encapsulates
     * various communication methods such as phone, email, fax, secure email,
     * website, and other related properties. It is used to define the
     * telecommunications or contact channel information associated with a contact.
     */
    private TelecomDto telecom;

    /**
     * Represents the person details associated with the contact.
     * This variable holds an instance of the PersonDto class, which contains
     * information such as the first name, last name, middle name, and title
     * of an individual. It is used to encapsulate and manage personal details
     * related to a contact.
     */
    private PersonDto person;

    /**
     * Generates a string representation of the ContactDto object.
     * The string includes the values of the use, addressDto, telecomDto, and personDto fields
     * in a formatted structure.
     *
     * @return a string describing the state of the ContactDto object.
     */
    @Override
    public String toString() {
        return "ContactDto{" +
                "use='" + use + '\'' +
                ", addressDto=" + address +
                ", telecomDto=" + telecom +
                ", personDto=" + person +
                '}';
    }
}
