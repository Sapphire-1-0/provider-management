package com.brihaspathee.sapphire.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 04, January 2026
 * Time: 08:15
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
public class SpecialtyDto {

    /**
     * Represents the unique identifier for an element.
     * This variable is used to store a distinct string value that identifies
     * an element within a specific context or system.
     */
    private String elementId;

    /**
     * Represents the taxonomy code of a provider or entity.
     * This variable is used to store the standardized code that identifies
     * the specialty or area of practice associated with a provider or entity.
     * The taxonomy code conforms to a hierarchical classification system.
     */
    private String taxonomyCode;

    /**
     * Represents the specialty of a provider or entity.
     * This variable is used to store the specific area of practice or expertise
     * associated with the provider or entity within the system.
     */
    private String specialty;

    /**
     * Indicates whether the specialty is the primary one.
     * This variable is used to specify a boolean value that determines if the associated
     * specialty is flagged as the primary specialty within the context of the system.
     */
    private Boolean isPrimary;

    /**
     * Generates a string representation of the SpecialtyDto object.
     * The string includes the values of the elementId, taxonomyCode, and specialty
     * fields in a formatted structure.
     *
     * @return a string describing the state of the SpecialtyDto object.
     */
    @Override
    public String toString() {
        return "SpecialtyDto{" +
                "elementId='" + elementId + '\'' +
                ", taxonomyCode='" + taxonomyCode + '\'' +
                ", specialty='" + specialty + '\'' +
                '}';
    }
}
