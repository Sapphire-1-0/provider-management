package com.brihaspathee.sapphire.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 06, February 2026
 * Time: 15:53
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
public class HealthcareServiceDto {

    /**
     * Represents a unique identifier for an element within the healthcare service data structure.
     * This variable is used to uniquely identify or reference a specific element
     * in the context of the containing object.
     */
    private String elementId;

    /**
     * Represents the type of the healthcare service.
     * This variable is used to specify a classification or categorization
     * of the healthcare service within the context of the containing object.
     */
    private String healthcareServiceType;

    /**
     * Represents the state associated with an entity.
     * This variable is used to store the specific state or condition
     * related to the context of the containing object.
     */
    private String state;

    /**
     * Represents the populations served by the healthcare service.
     * This variable is used to define the targeted groups of individuals
     * or populations, such as age groups, communities, or specific demographics,
     * that a healthcare service is intended to serve.
     */
    private List<String> servicePopulation;

    /**
     * Represents the start date for a healthcare service.
     * This variable is used to specify the initial date when the healthcare service
     * becomes active or valid within the system's context.
     */
    private LocalDate startDate;

    /**
     * Represents the end date for a healthcare service.
     * This variable is used to specify the final date when the healthcare service
     * is considered active or valid within the system's context.
     */
    private LocalDate endDate;

    /**
     * Generates a string representation of the HealthcareServiceDto object.
     * The string includes the values of the elementId, state, servicePopulation,
     * startDate, and endDate fields in a formatted structure.
     *
     * @return a string describing the state of the HealthcareServiceDto object.
     */
    @Override
    public String toString() {
        return "HealthcareServiceDto{" +
                "elementId='" + elementId + '\'' +
                ", state='" + state + '\'' +
                ", servicePopulation=" + servicePopulation +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
