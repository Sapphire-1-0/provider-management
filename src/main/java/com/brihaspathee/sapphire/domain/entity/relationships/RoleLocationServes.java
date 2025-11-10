package com.brihaspathee.sapphire.domain.entity.relationships;

import lombok.*;

import java.time.LocalDate;

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
public class RoleLocationServes {

    /**
     * Represents the start date associated with the entity.
     * This variable typically marks the commencement or initiation
     * of a specific time period or activity within a defined context.
     */
    private LocalDate startDate;

    /**
     * Represents the end date associated with the entity.
     * This variable typically signifies the conclusion or termination
     * of a specific time period or activity within a defined context.
     */
    private LocalDate endDate;

    /**
     * Specifies the reason for termination associated with the entity.
     * This variable typically provides a descriptive explanation or code
     * that identifies why the termination occurred within a particular context.
     */
    private String termReason;
}
