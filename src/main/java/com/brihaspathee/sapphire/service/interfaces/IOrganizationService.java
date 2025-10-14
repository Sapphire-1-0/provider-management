package com.brihaspathee.sapphire.service.interfaces;

import com.brihaspathee.sapphire.domain.entity.Organization;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 27, August 2025
 * Time: 05:20
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface IOrganizationService {

    List<Organization> getAllOrganizations();
}
