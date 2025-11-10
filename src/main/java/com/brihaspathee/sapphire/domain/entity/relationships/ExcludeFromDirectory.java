package com.brihaspathee.sapphire.domain.entity.relationships;

import lombok.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 09, November 2025
 * Time: 04:59
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.entity.relationships
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcludeFromDirectory {

    /**
     * Indicates whether the entity should be excluded from a directory.
     * This variable is used to determine if the entity is visible or included
     * in directory-related listings or processing within a specific context.
     */
    private Boolean excludeFromDirectory;

    /**
     * Specifies the reason for excluding the entity from the directory.
     * This variable provides additional context or explanation
     * regarding why the exclusion was applied within a specific context.
     */
    private String excludeReason;
}
