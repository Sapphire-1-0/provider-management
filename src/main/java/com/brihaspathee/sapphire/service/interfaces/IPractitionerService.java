package com.brihaspathee.sapphire.service.interfaces;

import com.brihaspathee.sapphire.model.PractitionerList;
import com.brihaspathee.sapphire.model.web.PractitionerSearchRequest;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11/18/25
 * Time: 7:07 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface IPractitionerService {

    PractitionerList getPractitioners(PractitionerSearchRequest practitionerSearchRequest);
}
