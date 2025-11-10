package com.brihaspathee.sapphire.domain.entity.relationships;

import lombok.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 09, November 2025
 * Time: 04:55
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.entity.relationships
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HasPanel {

    /**
     * Represents the lowest age in years applicable to the entity.
     * This variable is used to define the minimum age criteria
     * within a specific context or operation.
     */
    private Integer lowestAgeYears;

    /**
     * Represents the highest age in years applicable to the entity.
     * This variable is used to define the maximum age criteria
     * within a specific context or operation.
     */
    private Integer highestAgeYears;

    /**
     * Represents the lowest age in months applicable to the entity.
     * This variable is used to define the minimum age criteria
     * within a specific context or operation.
     */
    private Integer lowestAgeMonths;

    /**
     * Represents the highest age in months applicable to the entity.
     * This variable is typically used in conjunction with other age-related
     * attributes to define age boundaries or criteria within a specific context.
     */
    private Integer highestAgeMonths;

    /**
     * Specifies the limits or restrictions related to age for the entity.
     * This variable generally defines the age-related criteria or boundaries
     * applicable within a certain context or operation.
     */
    private String ageLimitation;

    /**
     * Specifies any gender-based restrictions or criteria applicable to the entity.
     * This variable typically indicates if there are limitations or considerations
     * based on gender for a specific context or operation.
     */
    private String genderLimitation;

    /**
     * Represents the status of the instance or entity. The value of the status
     * may signify the current state or condition, potentially used for tracking,
     * validation, or processing purposes.
     */
    private String status;
}
