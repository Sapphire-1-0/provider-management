package com.brihaspathee.sapphire.domain.repository.interfaces;

import com.brihaspathee.sapphire.domain.entity.Practitioner;
import com.brihaspathee.sapphire.model.web.PractitionerSearchRequest;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11/18/25
 * Time: 7:10 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.domain.repository.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface PractitionerRepository {

    List<Practitioner> findPractitioners(PractitionerSearchRequest practitionerSearchRequest);
}
