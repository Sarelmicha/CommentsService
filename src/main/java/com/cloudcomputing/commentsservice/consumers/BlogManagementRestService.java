package com.cloudcomputing.commentsservice.consumers;

import com.cloudcomputing.commentsservice.boundaries.BlogPostBoundary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Component
public class BlogManagementRestService {
    private WebClient webClient;
    private int port;
    private String host;
    private String route;

    @Value("${blogManagementService.port:8081}")
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
        this.webClient = WebClient.create("http://" + host + ":" + port + "/" + route);
    }

    public Mono<BlogPostBoundary> getBlog(String blogId){
        return webClient.get()
                .uri("/{blogId}", blogId)
                .retrieve()
                .bodyToMono(BlogPostBoundary.class).log(" Blog with id " + blogId + " fetched.");
    }
}