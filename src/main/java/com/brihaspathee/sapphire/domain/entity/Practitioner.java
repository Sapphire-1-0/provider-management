package com.brihaspathee.sapphire.domain.entity;

import lombok.*;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11/18/25
 * Time: 6:54 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.entity
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Practitioner {

    private String elementId;

    private String firstName;

    private String lastName;

    private String middleName;

    private String birthDate;

    private String gender;

    private String altFirstName;

    private String altLastName;

    private String altMiddleName;

    private List<Identifier> identifiers;

    private List<Network> networks;

    private List<Location> locations;

    @Override
    public String toString() {
        return "Practitioner{" +
                "altMiddleName='" + altMiddleName + '\'' +
                ", altLastName='" + altLastName + '\'' +
                ", altFirstName='" + altFirstName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", elementId='" + elementId + '\'' +
                '}';
    }
}
