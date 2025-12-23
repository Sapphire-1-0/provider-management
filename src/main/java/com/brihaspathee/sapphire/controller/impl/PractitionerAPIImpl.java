package com.brihaspathee.sapphire.controller.impl;

import com.brihaspathee.sapphire.controller.interfaces.PractitionerAPI;
import com.brihaspathee.sapphire.model.PractitionerDto;
import com.brihaspathee.sapphire.model.PractitionerList;
import com.brihaspathee.sapphire.model.web.PractitionerSearchRequest;
import com.brihaspathee.sapphire.service.interfaces.PractitionerService;
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

    /**
     * The {@code practitionerService} is a dependency that provides operations related to
     * practitioner management. It facilitates interactions with practitioner data, including
     * retrieval of a single practitioner's details or a list of practitioners based on
     * search criteria.
     *
     * It is responsible for handling business logic and data access for practitioner-related
     * actions and is used in the implementation of the PractitionerAPI.
     */
    private final PractitionerService practitionerService;

    /**
     * Retrieves the details of a practitioner based on the provided practitioner code.
     *
     * @param practitionerCode the unique code associated with the practitioner to be retrieved
     * @return a ResponseEntity containing a SapphireAPIResponse that encapsulates a
     * PractitionerDto, representing the details of the requested practitioner
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<PractitionerDto>> getPractitionerByCode(String practitionerCode) {
        log.info("Retrieving practitioner with code: {}", practitionerCode);
        PractitionerDto practitionerDto = practitionerService.getPractitionerByCode(practitionerCode);
        SapphireAPIResponse<PractitionerDto> apiResponse =
                SapphireAPIResponse.<PractitionerDto>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Practitioner Retrieved successfully")
                        .response(practitionerDto)
                        .timestamp(LocalDateTime.now())
                        .reason("Practitioner Retrieved successfully")
                        .developerMessage("Practitioner Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves a list of practitioners based on the provided search criteria.
     *
     * @param practitionerSearchRequest the search request containing criteria such as identifiers
     *                                   to filter and retrieve practitioners
     * @return a ResponseEntity containing a SapphireAPIResponse that encapsulates
     *         a PractitionerList, which includes the list of practitioners matching
     *         the search criteria
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<PractitionerList>> getPractitioners(PractitionerSearchRequest practitionerSearchRequest) {
        PractitionerList practitionerList = practitionerService.getPractitioners(practitionerSearchRequest);
        SapphireAPIResponse<PractitionerList> apiResponse =
                SapphireAPIResponse.<PractitionerList>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Practitioner Retrieved successfully")
                        .response(practitionerList)
                        .timestamp(LocalDateTime.now())
                        .reason("Practitioner Retrieved successfully")
                        .developerMessage("Practitioner Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves a practitioner based on the provided practitioner ID.
     *
     * @param practitionerId the unique identifier of the practitioner to be retrieved
     * @return a ResponseEntity containing a SapphireAPIResponse that encapsulates
     * a PractitionerDto, which represents the details of the requested practitioner
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<PractitionerDto>> getPractitionerById(String practitionerId) {
        PractitionerDto practitionerDto = practitionerService.getPractitionerById(practitionerId);
        SapphireAPIResponse<PractitionerDto> apiResponse =
                SapphireAPIResponse.<PractitionerDto>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Practitioner Retrieved successfully")
                        .response(practitionerDto)
                        .timestamp(LocalDateTime.now())
                        .reason("Practitioner Retrieved successfully")
                        .developerMessage("Practitioner Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves the details of a practitioner and associated network based on the provided IDs.
     *
     * @param practitionerId the unique identifier of the practitioner to be retrieved
     * @param netId          the unique identifier of the network associated with the practitioner
     * @return a ResponseEntity containing a SapphireAPIResponse that encapsulates a PractitionerDto,
     * representing the details of the requested practitioner
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<PractitionerDto>> getPractitionerAndNetById(String practitionerId, String netId) {
        PractitionerDto practitionerDto = practitionerService.getPracAndNetByElementId(practitionerId, netId);
        SapphireAPIResponse<PractitionerDto> apiResponse =
                SapphireAPIResponse.<PractitionerDto>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Practitioner Retrieved successfully")
                        .response(practitionerDto)
                        .timestamp(LocalDateTime.now())
                        .reason("Practitioner Retrieved successfully")
                        .developerMessage("Practitioner Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves the details of a practitioner along with the associated location information
     * based on the provided practitioner and location IDs.
     *
     * @param practitionerId the unique identifier of the practitioner to be retrieved
     * @param locId          the unique identifier of the location associated with the practitioner
     * @return a ResponseEntity containing a SapphireAPIResponse that encapsulates
     * a PractitionerDto, representing the details of the requested practitioner
     * along with associated location details
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<PractitionerDto>> getPractitionerAndLocById(String practitionerId, String locId) {
        PractitionerDto practitionerDto = practitionerService.getPracAndLocByElementId(practitionerId, locId);
        SapphireAPIResponse<PractitionerDto> apiResponse =
                SapphireAPIResponse.<PractitionerDto>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Practitioner Retrieved successfully")
                        .response(practitionerDto)
                        .timestamp(LocalDateTime.now())
                        .reason("Practitioner Retrieved successfully")
                        .developerMessage("Practitioner Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves the details of a practitioner, including associated network and location information,
     * based on the provided practitioner, network, and location IDs.
     *
     * @param practitionerId the unique identifier of the practitioner to be retrieved
     * @param netId          the unique identifier of the network associated with the practitioner
     * @param locId          the unique identifier of the location associated with the practitioner
     * @return a ResponseEntity containing a SapphireAPIResponse that encapsulates
     * a PractitionerDto, representing the details of the requested practitioner
     * along with associated network and location details
     */
    @Override
    public ResponseEntity<SapphireAPIResponse<PractitionerDto>> getPractitionerAndNetAndLocById(String practitionerId, String netId, String locId) {
        PractitionerDto practitionerDto = practitionerService.getPracAndNetAndLocByElementId(practitionerId, netId, locId);
        SapphireAPIResponse<PractitionerDto> apiResponse =
                SapphireAPIResponse.<PractitionerDto>builder()
                        .statusCode(200)
                        .status(HttpStatus.OK)
                        .message("Practitioner Retrieved successfully")
                        .response(practitionerDto)
                        .timestamp(LocalDateTime.now())
                        .reason("Practitioner Retrieved successfully")
                        .developerMessage("Practitioner Retrieved successfully")
                        .build();
        return ResponseEntity.ok(apiResponse);
    }
}
