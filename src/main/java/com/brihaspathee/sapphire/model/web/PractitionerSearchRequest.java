package com.brihaspathee.sapphire.model.web;

import lombok.*;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11/18/25
 * Time: 6:40 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.model.web
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PractitionerSearchRequest {

    private List<IdentifierInfo> identifiers;

    @Override
    public String toString() {
        return "OrganizationSearchRequest{" +
                "identifiers=" + identifiers +
                '}';
    }
}
