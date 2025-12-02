package com.brihaspathee.sapphire.util;

import com.brihaspathee.sapphire.model.*;
import com.brihaspathee.sapphire.model.web.NetworkSearchRequest;
import com.brihaspathee.sapphire.web.response.SapphireAPIResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 30, November 2025
 * Time: 05:45
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.util
 * To change this template use File | Settings | File and Code Template
 */
public class TestUtils {

    /**
     * Retrieves the first {@link NetworkDto} from the response of an API call that is
     * executed using the provided WebTestClient and ObjectMapper.
     *
     * @param webTestClient the WebTestClient instance used to send the API request
     * @param objectMapper the ObjectMapper instance used to convert JSON data to objects
     * @param networkCode the networkCode used as a search parameter in the API request
     * @return the first {@link NetworkDto} from the API response, or null if no networks are found
     */
    public static NetworkDto getNetwork(WebTestClient webTestClient,
                                  ObjectMapper objectMapper,
                                  String networkCode){
        NetworkSearchRequest networkSearchRequest = NetworkSearchRequest.builder()
                .networkCode(networkCode)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<NetworkSearchRequest> httpEntity = new HttpEntity<>(networkSearchRequest, headers);
        String uri = "/api/v1/sapphire/network/_search";
//        ResponseEntity<SapphireAPIResponse<NetworkList>> responseEntity =
//                testRestTemplate.exchange(
//                        uri,
//                        HttpMethod.POST,
//                        httpEntity,
//                        new ParameterizedTypeReference<>() {
//                        }
//                );
        SapphireAPIResponse<NetworkList> apiResponse = webTestClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(networkSearchRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<SapphireAPIResponse<NetworkList>>(){})
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(apiResponse);
        NetworkList networkList = objectMapper.convertValue(apiResponse.getResponse(), NetworkList.class);
        return  networkList.getNetworks().getFirst();
    }

    /**
     * Retrieves a {@link LocationDto} object by making an API call using the given location code.
     * The API response is deserialized into a {@link LocationDto} using the provided {@link ObjectMapper}.
     *
     * @param webTestClient the {@link WebTestClient} instance used to send the API request
     * @param objectMapper the {@link ObjectMapper} instance used to convert JSON data into objects
     * @param locCode the location code used as a parameter in the API request
     * @return the {@link LocationDto} retrieved from the API response
     */
    public static LocationDto getLocationByCode(WebTestClient webTestClient,
                                   ObjectMapper objectMapper,
                                   String locCode){
        String uri = "/api/v1/sapphire/location/code/"+locCode;
        SapphireAPIResponse<LocationDto> apiResponse = webTestClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<SapphireAPIResponse<LocationDto>>(){})
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(apiResponse);
        LocationDto locationDto = objectMapper.convertValue(apiResponse.getResponse(), LocationDto.class);
        return  locationDto;
    }

    /**
     * Retrieves a {@link PractitionerDto} based on the given practitioner code by making
     * an API call using the specified {@link WebTestClient} instance. The response is
     * deserialized using the provided {@link ObjectMapper}.
     *
     * @param webTestClient the {@link WebTestClient} instance used to make the API request
     * @param objectMapper the {@link ObjectMapper} instance used to convert JSON data to objects
     * @param pracCode the practitioner code used as a parameter in the API request
     * @return the {@link PractitionerDto} retrieved from the API response
     */
    public static PractitionerDto getPractitionerByCode(WebTestClient webTestClient,
                                                      ObjectMapper objectMapper,
                                                      String pracCode){
        String uri = "/api/v1/sapphire/practitioner/code/"+pracCode;
        SapphireAPIResponse<PractitionerDto> apiResponse = webTestClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<SapphireAPIResponse<PractitionerDto>>(){})
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(apiResponse);
        PractitionerDto practitionerDto = objectMapper.convertValue(apiResponse.getResponse(), PractitionerDto.class);
        return  practitionerDto;
    }

    /**
     * Retrieves the {@link OrganizationDto} for the given organization code
     * by making a request using the specified {@link WebTestClient} instance.
     * The API response is deserialized using the provided {@link ObjectMapper}.
     *
     * @param webTestClient the {@link WebTestClient} instance used to send the API request
     * @param objectMapper the {@link ObjectMapper} instance used to convert JSON data to objects
     * @param orgCode the organization code used as a parameter in the API request
     * @return the {@link OrganizationDto} obtained from the API response
     */
    public static OrganizationDto getOrganizationByCode(WebTestClient webTestClient,
                                                      ObjectMapper objectMapper,
                                                      String orgCode){
        String uri = "/api/v1/sapphire/organization/code/"+orgCode;
        SapphireAPIResponse<OrganizationDto> apiResponse = webTestClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<SapphireAPIResponse<OrganizationDto>>(){})
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(apiResponse);
        OrganizationDto organizationDto = objectMapper.convertValue(apiResponse.getResponse(), OrganizationDto.class);
        return  organizationDto;
    }
}
