package com.brihaspathee.sapphire.model;

import com.brihaspathee.sapphire.model.web.IdentifierInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11/18/25
 * Time: 6:53 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PractitionerDto {

    /**
     * Represents a unique identifier for an element.
     * This variable is used to reference or track a specific entity
     * within its respective context in the application.
     */
    private String elementId;

    /**
     * Represents the first name of the practitioner.
     * This variable is used to store the given name or personal name
     * of the practitioner as part of their personal identification details.
     */
    private String firstName;

    /**
     * Represents the last name of the practitioner.
     * This variable is used to store the surname or family name
     * of the practitioner as part of their personal identification details.
     */
    private String lastName;

    /**
     * Represents the middle name of the practitioner.
     * This variable is used to store the practitioner's middle name
     * if available, as part of their personal identification details.
     */
    private String middleName;

    /**
     * Represents the date of birth of the practitioner.
     * This variable is used to store the birth date information
     * relevant to the practitioner, typically formatted as a string.
     */
    private String birthDate;

    /**
     * Represents the gender of the practitioner.
     * This variable is used to store the gender information
     * which could include values such as male, female, or other
     * designations as required by the system.
     */
    private String gender;

    /**
     * Represents an alternative first name for the practitioner.
     * This variable is used to store another first name that the practitioner
     * might be known by, or as an alternative to their primary first name.
     */
    private String altFirstName;

    /**
     * Represents an alternative last name for the practitioner.
     * This variable is used to store a secondary or additional last name
     * that the practitioner might be known by, or as an alternative
     * to their primary last name.
     */
    private String altLastName;

    /**
     * Represents an alternative middle name for the practitioner.
     * This variable is used to store an additional or secondary middle name
     * that the practitioner may be known by, or as an alternative to the primary middle name.
     */
    private String altMiddleName;

    /**
     * Represents a list of identifier objects.
     * This variable is used to store multiple `IdentifierDto` instances,
     * each of which encapsulates information such as element ID, type, value,
     * and additional metadata. It serves to manage and organize
     * identifiers associated with a specific context or entity.
     */
    private List<IdentifierDto> identifiers;


}
