package com.cloudcomputing.commentsservice.consumers;

import com.cloudcomputing.commentsservice.boundaries.BlogPostBoundary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.annotation.PostConstruct;

@Service
public class BlogManagementRestService {
    private RestTemplate restTemplate;
    private String url;
    private int port;
    private String host;
    private String blogRoute;

    @Value("${blogManagementService.port:8081}")
    public void setPort(String port) {
        this.port = Integer.parseInt(port);

    }

    @Value("${blogManagementService.host:localhost}")
    public void setHost(String host) {
        this.host = host;
    }

    @Value("${blogManagementService.blogRoute:blogs}")
    public void setRoute(String blogRoute) {
        this.blogRoute = blogRoute;
    }

    @PostConstruct
    public void init() {
        this.restTemplate = new RestTemplate();
        this.url = "http://" + host + ":" + port + "/" + blogRoute;
    }

    public BlogPostBoundary getBlog(String blogId){
        return this.restTemplate.getForObject(this.url + "/{blogId}", BlogPostBoundary.class, blogId);
    }
}