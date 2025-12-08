package com.brihaspathee.sapphire.domain.entity;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 07, December 2025
 * Time: 07:02
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.entity
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Qualification {

    /**
     * Unique identifier for an element within the Qualification.
     * This property is used to uniquely identify the specific instance
     * of the Qualification object in the system for effective tracking
     * and reference in data operations.
     */
    private String elementId;

    /**
     * Represents the type of the qualification.
     * The `type` field is used to categorize or classify
     * the qualification, providing context and meaning
     * related to its nature or purpose. This property
     * can hold predefined or custom string values depending
     * on the application's use-case and operational requirements.
     */
    private String type;

    /**
     * Represents the value associated with the qualification.
     * This property is used to capture a specific identifying or descriptive
     * detail about the qualification, such as a unique code or identifier
     * related to it. The exact nature of this value may vary based on the
     * context in which the qualification is used.
     */
    private String value;

    /**
     * Represents the entity or organization that issued the qualification.
     * This property typically identifies the certifying body, institution,
     * or authority responsible for granting the qualification or credential.
     */
    private String issuer;

    /**
     * Represents the start date of the qualification.
     * This property specifies the date from which the qualification
     * or credential becomes valid or applicable.
     */
    private LocalDate startDate;

    /**
     * Represents the end date of the qualification.
     * This property specifies the date on which the qualification
     * or credential ceases to be valid or applicable.
     */
    private LocalDate endDate;

    /**
     * Represents the taxonomy or classification associated with a specialty.
     * This property provides a standardized categorization or identifier
     * that defines the specific area of specialization, often used in
     * healthcare and certification contexts to uniquely identify the type
     * of specialty.
     */
    private String specialtyTaxonomy;

    /**
     * Represents the hierarchy or levels associated with the qualification.
     * This property can hold a list of strings, each string potentially
     * representing a different level of qualification, certification, or
     * responsibility. It provides additional context or granularity about
     * the qualification and its classification.
     */
    private List<String> level;

    /**
     * Represents the state in which a qualification is valid or applicable.
     * This property typically specifies the U.S. state or region associated with
     * the qualification, providing geographic context or compliance details.
     */
    private String state;

    /**
     * Generates a string representation of the Qualification object, including the values
     * of its properties: type, value, issuer, startDate, endDate, specialtyTaxonomy, level, and state.
     *
     * @return A string representation of the Qualification object, formatted to display
     *         the type, value, issuer, startDate, endDate, specialtyTaxonomy, level, and state values
     *         enclosed within curly braces.
     */
    @Override
    public String toString() {
        return "Qualification{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", issuer='" + issuer + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", specialtyTaxonomy='" + specialtyTaxonomy + '\'' +
                ", level=" + level +
                ", state='" + state + '\'' +
                '}';
    }
}
