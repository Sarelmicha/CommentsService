package com.cloudcomputing.commentsservice.consumers;

import com.cloudcomputing.commentsservice.boundaries.BlogPostBoundary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Component
public class BlogManagementRestService {
    private RestTemplate restTemplate;
    private String url;
    private int port;
    private String host;
    private String route;

    @Value("${blogManagementService.port:8083}")
    public void setPort(String port) {
        this.port = Integer.parseInt(port);

    }

    @Value("${blogManagementService.host:localhost}")
    public void setHost(String host) {
        this.host = host;
    }

    @Value("${blogManagementService.route:blog}")
    public void setRoute(String route) {
        this.route = route;
    }
    @PostConstruct
    public void init() {
        this.restTemplate = new RestTemplate();
        this.url = "http://" + host + ":" + port + "/" + route;
    }

    public BlogPostBoundary getBlog(String blogId){

        return this.restTemplate.getForObject(this.url + "/{blogId}", BlogPostBoundary.class, blogId);
    }
}