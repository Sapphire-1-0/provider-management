package com.brihaspathee.sapphire.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 07, December 2025
 * Time: 07:17
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
public class QualificationDto {

    /**
     * Represents a unique identifier for an element within the system.
     * This variable is used to uniquely identify and reference a specific
     * entity or component, ensuring traceability and differentiation
     * across related data or processes.
     */
    private String elementId;

    /**
     * Represents the classification or category of the qualification.
     * This variable is used to specify the type of qualification,
     * which may pertain to a specific role, skill, or professional designation.
     */
    private String type;

    /**
     * Represents the issuing entity or organization related to the qualification.
     * This variable is used to store the name or identifier of the issuer
     * responsible for certifying or accrediting the referenced qualification.
     */
    private String issuer;

    /**
     * Represents a value associated with the qualification details.
     * This variable is used to store any additional data, such as
     * metadata or a specific descriptor, that provides further context
     * or information regarding the qualification.
     */
    private String value;

    /**
     * Represents the taxonomy of a specific specialty.
     * This variable is used to store the taxonomy code or identifier
     * which categorizes or describes a particular specialty in the system.
     */
    private String specialtyTaxonomy;

    /**
     * Represents the state associated with a qualification.
     * This variable is used to store the name or abbreviation of a state
     * relevant to a particular qualification within the system.
     */
    private String state;

    /**
     * Represents the level of qualification.
     * This variable is used to specify the degree, rank, or standard
     * associated with a particular qualification within the system.
     */
    private List<String> level;

    /**
     * Represents the start date of a qualification period.
     * This variable is used to track the initial date from which
     * a specific qualification's validity or applicability begins
     * within the system.
     */
    private LocalDate startDate;

    /**
     * Represents the end date of a qualification period.
     * This variable is used to track the conclusion date of a specific
     * qualification's validity or applicability within the system.
     */
    private LocalDate endDate;

    /**
     * Returns a string representation of the QualificationDto object.
     * The string includes the values of the elementId, type, issuer,
     * specialtyTaxonomy, state, level, startDate, and endDate properties.
     *
     * @return a string representation of the QualificationDto object.
     */
    @Override
    public String toString() {
        return "QualificationDto{" +
                "elementId='" + elementId + '\'' +
                ", type='" + type + '\'' +
                ", issuer='" + issuer + '\'' +
                ", value='" + value + '\'' +
                ", specialtyTaxonomy='" + specialtyTaxonomy + '\'' +
                ", state='" + state + '\'' +
                ", level='" + level + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
