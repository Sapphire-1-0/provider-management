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

    /**
     * Unique identifier for an element within the Practitioner.
     * This property is used to distinctly identify an instance of Practitioner
     * or a related entity in the application, ensuring traceability and consistency
     * during data operations.
     */
    private String elementId;

    /**
     * Represents the first name of a practitioner.
     * This property typically holds the given name of the individual, which
     * is used for identification and personalization within the system.
     * It may also be used in combination with other name-related fields
     * to provide a complete representation of the practitioner's identity.
     */
    private String firstName;

    /**
     * Represents the last name of the practitioner.
     * This property is used to store the family or surname of the individual,
     * which is commonly used for identification and official records.
     */
    private String lastName;

    /**
     * Represents the middle name of the practitioner.
     * This field holds optional information that may be provided
     * as part of the practitioner's full name for identification or formal purposes.
     */
    private String middleName;

    /**
     * Represents the birth date of a practitioner.
     * This field stores the date of birth as a string,
     * which may be used for identification, record-keeping,
     * or demographic purposes within the system.
     */
    private String birthDate;

    /**
     * Represents the gender of the practitioner.
     * This property is used to store information about
     * the gender of the practitioner, which can be used
     * for identification or reporting purposes in the system.
     */
    private String gender;

    /**
     * Represents an alternative first name for the practitioner.
     * This property is used to store an alias or an alternative version
     * of the first name, which may be used for identification or reference
     * in specific contexts where the primary first name might not apply.
     */
    private String altFirstName;

    /**
     * Represents an alternative last name for the practitioner.
     * This field is used to store an additional or former last name,
     * which can be useful for identification purposes, record-keeping,
     * or handling name changes (e.g., due to marriage or legal updates).
     */
    private String altLastName;

    /**
     * Represents an alternate middle name for a Practitioner.
     * This property is used to store a middle name that is different
     * from the practitioner's primary middle name. It can be used to
     * handle variations in naming conventions, aliases, or other alternate
     * identifiers specific to the middle name.
     */
    private String altMiddleName;

    /**
     * Represents the list of identifiers associated with the practitioner.
     * An identifier provides unique attributes like elementId, value,
     * startDate, and endDate, which can be used for identification and
     * management purposes. Examples of identifiers could include NPI
     * (National Provider Identifier) or TIN (Taxpayer Identification Number).
     * This property allows the practitioner to manage multiple unique
     * identifiers relevant to their practice or role within the system.
     */
    private List<Identifier> identifiers;

    /**
     * Represents the list of qualifications associated with the practitioner.
     * Each qualification in the list provides detailed information about
     * the practitioner's credentials, certifications, licenses, or other
     * qualifications relevant to their role or specialty.
     */
    private List<Qualification> qualifications;

    /**
     * Represents the list of networks associated with the practitioner.
     * Each network in the list contains specific information such as
     * unique identifier, network code, network name, and type
     * (e.g., HNET network or vendor network). This property facilitates
     * the management and classification of healthcare or service
     * networks relevant to the practitioner within the application.
     */
    private List<Network> networks;

    /**
     * Represents the list of locations associated with a practitioner.
     * Each location in the list provides detailed information such as
     * name, address, city, state, ZIP code, county, county FIPS, and
     * associated networks or network service information. This property
     * allows for managing and tracking multiple practice locations for
     * a practitioner within the application.
     */
    private List<Location> locations;

    /**
     * Provides a string representation of the Practitioner object, including
     * the values of its properties such as altMiddleName, altLastName,
     * altFirstName, gender, birthDate, middleName, lastName, firstName,
     * and elementId.
     *
     * @return A string representation of the Practitioner object, formatted
     *         with property names and their corresponding values enclosed
     *         within curly braces.
     */
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
