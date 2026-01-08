package com.brihaspathee.sapphire.model;

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
public class PersonDto {

    /**
     * Represents the first name of a person.
     * This variable is used to store the given name or personal name
     * that identifies an individual.
     */
    private String firstName;

    /**
     * Represents the last name of a person.
     * This variable is used to store the family name or surname
     * associated with the individual.
     */
    private String lastName;

    /**
     * Represents the middle name of a person.
     * This variable is used to store the middle name, if any,
     * associated with the individual.
     */
    private String middleName;

    /**
     * Represents the title associated with the person.
     * This variable is used to store any honorary, professional, or ceremonial
     * designation or title relevant to the person.
     */
    private String title;

    /**
     * Generates a string representation of the PersonDto object.
     * The string includes the values of the firstName, lastName, middleName, and title
     * fields in a formatted structure.
     *
     * @return a string describing the state of the PersonDto object.
     */
    @Override
    public String toString() {
        return "PersonDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
