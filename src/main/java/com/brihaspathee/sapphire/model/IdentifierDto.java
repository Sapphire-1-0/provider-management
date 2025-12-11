package com.brihaspathee.sapphire.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.util.Map;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 04, November 2025
 * Time: 05:47
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
public class IdentifierDto {

    /**
     * Represents a unique identifier for an element.
     * This variable is used to reference or track a specific entity
     * within its respective context or scope.
     */
    private String elementId;

    /**
     * Represents the value of the identifier.
     * This variable is used to hold the actual content or data associated
     * with the identifier. It serves as the core information that uniquely
     * or specifically defines the identifier in a given context.
     */
    private String value;

    /**
     * Represents the type of the identifier.
     * This variable is used to classify or categorize the identifier
     * based on its specific purpose or nature. It provides a way to
     * distinguish among different kinds of identifiers within a given context.
     */
    private String type;

    /**
     * Represents the start date for a specific period or range associated with an identifier.
     * This variable is used to define the beginning of a time interval during which the
     * identifier is considered valid or applicable.
     */
    private LocalDate startDate;

    /**
     * Represents the end date for a specific period or range associated with an identifier.
     * This variable is used to define the conclusion of a time interval during which the
     * identifier is considered valid or applicable.
     */
    private LocalDate endDate;

    /**
     * Represents a map to store additional properties or metadata
     * that are not explicitly defined in the class. This can be used
     * for extending the class with dynamic attributes during runtime.
     * The keys represent the property names, and the values are
     * corresponding attribute values.
     */
    private Map<String, Object> additionalProperties;

    /**
     * Returns a string representation of the IdentifierDto object.
     * The string includes the values of the class attributes such as elementId, value, type,
     * startDate, endDate, and additionalProperties in a formatted manner.
     *
     * @return A string that represents the IdentifierDto object.
     */
    @Override
    public String toString() {
        return "IdentifierDto{" +
                "elementId='" + elementId + '\'' +
                ", value='" + value + '\'' +
                ", type='" + type + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
