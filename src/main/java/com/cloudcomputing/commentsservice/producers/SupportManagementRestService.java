package com.cloudcomputing.commentsservice.producers;

import com.cloudcomputing.commentsservice.boundaries.TicketBoundary;
import com.cloudcomputing.commentsservice.boundaries.UserBoundary;
import com.cloudcomputing.commentsservice.utils.Constants;
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
    private String url;
    private int port;
    private String host;
    private String route;


    @Value("${supportManagementService.port:8082}")
    public void setPort(String port) {
        this.port = Integer.parseInt(port);
    }

    @Value("${supportManagementService.host:localhost}")
    public void setHost(String host) {
        this.host = host;
    }

    @Value("${supportManagementService.route:ticket}")
    public void setRoute(String route) {
        this.route = route;
    }


    @PostConstruct
    public void init() {
        this.restTemplate = new RestTemplate();
        this.url = "http://" + host + ":" + port + "/" + route;
    }

    public void createTicket(String email, Long commentId){

        TicketBoundary ticketBoundary = new TicketBoundary();
        ticketBoundary.setEmail(email);
        ticketBoundary.setName(commentId.toString());
        ticketBoundary.setExternalId(commentId.toString());
        ticketBoundary.setExternalServiceType(Constants.BLOG_COMMENTS_SERVICE);
        this.restTemplate.postForObject(this.url, ticketBoundary, TicketBoundary.class);

    }
}