package com.brihaspathee.sapphire.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 06, February 2026
 * Time: 15:50
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class DisorderDto {

    /**
     * Represents a unique identifier for an element.
     * This variable is used to distinguish and reference a specific
     * entity or object within the system.
     */
    private String elementId;

    /**
     * Represents the type of disorder associated with an entity.
     * This variable is used to specify a particular category or classification
     * of a disorder in the system's context.
     */
    private String disorderType;

    /**
     * Generates a string representation of the DisorderDto object.
     * The string includes the values of the elementId and disorderType
     * fields in a formatted structure.
     *
     * @return a string describing the state of the DisorderDto object.
     */
    @Override
    public String toString() {
        return "DisorderDto{" +
                "elementId='" + elementId + '\'' +
                ", disorderType='" + disorderType + '\'' +
                '}';
    }
}
