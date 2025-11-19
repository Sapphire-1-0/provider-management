package com.brihaspathee.sapphire.controller.interfaces;

import com.brihaspathee.sapphire.model.OrganizationList;
import com.brihaspathee.sapphire.model.PractitionerList;
import com.brihaspathee.sapphire.model.web.OrganizationSearchRequest;
import com.brihaspathee.sapphire.model.web.PractitionerSearchRequest;
import com.brihaspathee.sapphire.web.response.SapphireAPIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11/18/25
 * Time: 5:21 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.controller.interfaces
 * To change this template use File | Settings | File and Code Template
 */
@RequestMapping("/api/v1/sapphire/practitioner")
public interface PractitionerAPI {

    @PostMapping("/_search")
    ResponseEntity<SapphireAPIResponse<PractitionerList>> getPractitioners(
            @RequestBody PractitionerSearchRequest practitionerSearchRequest);
}
