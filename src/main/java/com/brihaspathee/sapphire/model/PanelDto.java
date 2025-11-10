package com.brihaspathee.sapphire.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 09, November 2025
 * Time: 17:06
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PanelDto {

    /**
     * Represents the highest age in years.
     * This variable is used to specify the maximum age in years
     * as part of an age-related range or criteria within the system.
     */
    private Integer highestAgeYears;

    /**
     * Represents the lowest age in years.
     * This variable is used to specify the minimum age in years
     * as part of an age-related range or criteria within the system.
     */
    private Integer lowestAgeYears;

    /**
     * Represents the highest age in months.
     * This variable is used to specify the maximum age in months
     * as part of an age-related range or criteria within the system.
     */
    private Integer highestAgeMonths;

    /**
     * Represents the lowest age in months.
     * This variable is used to specify the minimum age in months
     * as part of an age-related range or criteria within the system.
     */
    private Integer lowestAgeMonths;

    /**
     * Represents gender-specific limitations or conditions.
     * This variable is used to define any restrictions or requirements
     * based on gender within the system's context.
     */
    private String genderLimitation;

    /**
     * Specifies any limitations or conditions related to age.
     */
    private String ageLimitation;

    /**
     * Represents the status of the PanelDto entity.
     * This variable is used to indicate the current state or condition
     * of the entity as managed within the system.
     * The status could reflect operational, functional, or any
     * context-specific state as required.
     */
    private String status;
}
