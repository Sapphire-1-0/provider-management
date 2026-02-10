package com.brihaspathee.sapphire.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
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
     * Represents the code associated with the practitioner.
     * This variable is used to uniquely identify or categorize the practitioner
     * within the system or a specific context.
     */
    private String code;

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
    private LocalDate birthDate;

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

    /**
     * Represents a list of qualifications associated with a practitioner.
     * Each entry in the list is an instance of {@link QualificationDto}, containing detailed
     * information about a specific qualification, such as its type, issuer, level, and validity period.
     * This variable is used to capture and track the qualifications of a practitioner
     * within the system for professional or regulatory purposes.
     */
    private List<QualificationDto> qualifications;

    /**
     * Represents a list of organizations with which the practitioner is contracted.
     * This variable is used to store and manage details about the organizations
     * that maintain a formal contractual relationship with the practitioner.
     * Each organization in the list provides information such as name, type,
     * identifiers, qualifications, networks, and associated practitioners or locations.
     */
    private List<OrganizationDto> contractedOrganizations;

    /**
     * Represents a list of credentialing details associated with the practitioner.
     * This variable is used to manage and store information related to the
     * credentialing processes, such as type, geography, and relevant dates.
     */
    private List<CredentialingDto> credentials;

    /**
     * Represents the list of languages associated with a practitioner.
     * This variable is used to store multiple instances of LanguageDto,
     * each of which provides information about a language including its
     * unique identifier and value (e.g., code or name).
     */
    private List<LanguageDto> languages;

    /**
     * Represents the list of hospital privileges associated with the practitioner.
     * Each hospital privilege is encapsulated as an instance of the HospitalPrivilegeDto class
     * and details the specific types of privileges granted by hospitals, such as admitting,
     * attending, or consulting privileges.
     */
    private List<HospitalPrivilegeDto> hospitalPrivileges;

    /**
     * Returns a string representation of the PractitionerDto object.
     * The string includes the values of the properties such as elementId,
     * firstName, lastName, middleName, birthDate, gender, altFirstName,
     * altLastName, and altMiddleName.
     *
     * @return a string representation of the PractitionerDto object.
     */
    @Override
    public String toString() {
        return "PractitionerDto{" +
                "elementId='" + elementId + '\'' +
                ", code='" + code + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", gender='" + gender + '\'' +
                ", altFirstName='" + altFirstName + '\'' +
                ", altLastName='" + altLastName + '\'' +
                ", altMiddleName='" + altMiddleName + '\'' +
                '}';
    }
}
