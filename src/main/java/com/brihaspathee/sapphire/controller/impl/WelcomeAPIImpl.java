package com.brihaspathee.sapphire.controller.impl;

import com.brihaspathee.sapphire.controller.interfaces.WelcomeAPI;
import com.brihaspathee.sapphire.web.dto.WelcomeDto;
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
 * Date: 07, May 2025
 * Time: 15:25
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.controller.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class WelcomeAPIImpl implements WelcomeAPI {

    @Override
    public ResponseEntity<SapphireAPIResponse<WelcomeDto>> welcome() {
        SapphireAPIResponse<WelcomeDto> apiResponse = SapphireAPIResponse.<WelcomeDto>builder()
                .statusCode(200)
                .status(HttpStatus.OK)
                .message("Welcome to Sapphire")
                .response(WelcomeDto.builder()
                        .welcomeMessage("Welcome to Sapphire's Provider management service")
                        .build())
                .timestamp(LocalDateTime.now())
                .reason("Connection of Sapphire's Provider management service is good")
                .developerMessage("Connection of Sapphire's Provider management service is good")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
