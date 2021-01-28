package com.cloudcomputing.commentsservice.producers;

import com.cloudcomputing.commentsservice.boundaries.TicketBoundary;
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
public class SupportManagementRestService {
    private RestTemplate restTemplate;

    @Value("${supportManagementService.baseUrl}")
    private String url;

    @PostConstruct
    public void init() {
        this.restTemplate = new RestTemplate();
    }

    public void createTicket(String email, Long commentId){

        TicketBoundary ticketBoundary = new TicketBoundary();
        ticketBoundary.setEmail(email);
        ticketBoundary.setName(commentId.toString());
        this.restTemplate.postForObject(this.url, ticketBoundary, TicketBoundary.class);

    }
}