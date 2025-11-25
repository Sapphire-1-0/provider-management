package com.brihaspathee.sapphire.model;

import lombok.*;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11/18/25
 * Time: 6:44 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PractitionerList {

    private List<PractitionerDto> practitioners;

    @Override
    public String toString() {
        return "PractitionerList{" +
                "practitioners=" + practitioners +
                '}';
    }
}
