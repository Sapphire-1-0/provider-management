package com.brihaspathee.sapphire.model.web;

import lombok.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11, November 2025
 * Time: 11:53
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.model.web
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdentifierInfo {

    private String identifierType;
    private String identifierValue;

    @Override
    public String toString() {
        return "IdentifierInfo{" +
                "identifierType='" + identifierType + '\'' +
                ", identifierValue='" + identifierValue + '\'' +
                '}';
    }
}
