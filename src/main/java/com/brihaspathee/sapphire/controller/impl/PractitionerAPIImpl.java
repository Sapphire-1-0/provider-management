package com.brihaspathee.sapphire.controller.impl;

import com.brihaspathee.sapphire.controller.interfaces.PractitionerAPI;
import com.brihaspathee.sapphire.model.OrganizationList;
import com.brihaspathee.sapphire.model.PractitionerList;
import com.brihaspathee.sapphire.model.web.PractitionerSearchRequest;
import com.brihaspathee.sapphire.service.impl.PractitionerService;
import com.brihaspathee.sapphire.web.response.SapphireAPIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11/18/25
 * Time: 7:06 PM
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.controller.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class PractitionerAPIImpl implements PractitionerAPI {

    private final PractitionerService practitionerService;

    @Override
    public ResponseEntity<SapphireAPIResponse<PractitionerList>> getPractitioners(PractitionerSearchRequest practitionerSearchRequest) {
        PractitionerList practitionerList = practitionerService.getPractitioners(practitionerSearchRequest);
        SapphireAPIResponse<PractitionerList> apiResponse =
                SapphireAPIResponse.<PractitionerList>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Organization Retrieved successfully")
                        .response(practitionerList)
                        .timestamp(LocalDateTime.now())
                        .reason("Organization Retrieved successfully")
                        .developerMessage("Organization Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }
}
