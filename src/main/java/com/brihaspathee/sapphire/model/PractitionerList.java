package com.brihaspathee.sapphire.model;

import lombok.*;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11/18/25
 * Time: 6:44 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PractitionerList {

    /**
     * Represents the list of practitioners.
     * This variable holds a collection of PractitionerDto objects, where each object
     * contains detailed information about a specific practitioner, such as their personal details,
     * identifiers, and qualifications. The list is used to manage and organize multiple practitioners
     * within the system.
     */
    private List<PractitionerDto> practitioners;

    /**
     * Returns a string representation of the PractitionerList object.
     * The string includes the values of the practitioners property.
     *
     * @return a string representation of the PractitionerList object.
     */
    @Override
    public String toString() {
        return "PractitionerList{" +
                "practitioners=" + practitioners +
                '}';
    }
}
