package com.brihaspathee.sapphire.controller.interfaces;

import com.brihaspathee.sapphire.web.dto.WelcomeDto;
import com.brihaspathee.sapphire.web.response.SapphireAPIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 07, May 2025
 * Time: 15:19
 * Project: sapphire
 * Package Name: com.brihaspathee.template.controller.interfaces
 * To change this template use File | Settings | File and Code Template
 */
@RequestMapping("/api/v1/sapphire/provider/public")
public interface WelcomeAPI {


    @GetMapping("/welcome")
    ResponseEntity<SapphireAPIResponse<WelcomeDto>> welcome();
}
