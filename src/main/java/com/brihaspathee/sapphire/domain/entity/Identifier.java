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
@Data
public abstract class Identifier {

    private String elementId;
    private String value;
    private LocalDate startDate;
    private LocalDate endDate;
}
