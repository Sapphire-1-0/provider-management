package com.brihaspathee.sapphire.model.web;

import lombok.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 14, November 2025
 * Time: 03:23
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.model.web
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NetworkSearchRequest {

    private String productCode;

    private String networkCode;

    private String networkName;
}
