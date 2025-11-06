package com.brihaspathee.sapphire.domain.entity;

import lombok.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 06, November 2025
 * Time: 05:26
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.entity
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Network {

    private String elementId;

    private String code;

    private String name;

    private Boolean isHNETNetwork;

    private Boolean isVendorNetwork;
}
