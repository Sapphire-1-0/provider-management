package com.brihaspathee.sapphire.domain.entity;

import lombok.*;

import java.time.LocalDate;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 17, October 2025
 * Time: 16:17
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.entity
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
public abstract class Identifier {

    /**
     * Unique identifier for an element within the system.
     * This property is used to uniquely identify an instance of the class
     * or a related entity in the context of the application, ensuring
     * traceability and consistency across data operations.
     */
    private String elementId;

    /**
     * Represents the value associated with the identifier.
     * This property is used to hold the meaningful or relevant data
     * tied to the identifier within the system's context.
     * It serves as the actual content or representation of
     * the identifier's purpose.
     */
    private String value;

    /**
     * Represents the start date for the validity of the identifier.
     * This property indicates the date from which the identifier
     * is considered active or applicable in the system's context.
     * It is typically used to define the period of validity for
     * time-sensitive data or associations.
     */
    private LocalDate startDate;

    /**
     * Represents the end date for the validity of the identifier.
     * This property indicates the date until which the identifier is considered valid.
     * If null, it implies that the identifier has no defined expiration or is valid indefinitely.
     */
    private LocalDate endDate;

    /**
     * Generates a string representation of the Identifier object.
     * The string includes the values of its properties: elementId,
     * value, startDate, and endDate.
     *
     * @return A string representation of the Identifier object, formatted to display
     *         the elementId, value, startDate, and endDate values enclosed within curly braces.
     */
    @Override
    public String toString() {
        return "Identifier{" +
                "elementId='" + elementId + '\'' +
                ", value='" + value + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
