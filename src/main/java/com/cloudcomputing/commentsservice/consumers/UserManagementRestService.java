package com.cloudcomputing.commentsservice.consumers;

import com.cloudcomputing.commentsservice.boundaries.UserBoundary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserManagementRestService {
    private RestTemplate restTemplate;

    @Value("${userManagementService.baseUrl}")
    private String url;

    @PostConstruct
    public void init() {
        this.restTemplate = new RestTemplate();
    }

    public UserBoundary login(String email, String password){

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        // Set path variables
        Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("email", email);

        // Set query params
        URI targetUrl= UriComponentsBuilder.fromUriString(url)
                .path("/login/{email}")
                .uriVariables(urlParams)// Add path
                .queryParam("password", password)
                .build()
                .encode()
                .toUri();

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<UserBoundary> response =  restTemplate.exchange(
                targetUrl,
                HttpMethod.GET,
                entity,
                UserBoundary.class);

            return response.getBody();
    }
}