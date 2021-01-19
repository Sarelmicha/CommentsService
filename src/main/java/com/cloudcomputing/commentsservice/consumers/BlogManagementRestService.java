package com.cloudcomputing.commentsservice.consumers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;

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

    public Map<String,Object> getBlog(String blogId){
        return this.restTemplate.getForObject(this.url + "/{blogId}", Map.class, blogId);
    }
}