package com.cloudcomputing.commentsservice.consumers;

import com.cloudcomputing.commentsservice.boundaries.BlogPostBoundary;
import com.cloudcomputing.commentsservice.exceptions.NotFoundException;
import com.cloudcomputing.commentsservice.logic.utils.FILTER_TYPE;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.*;
import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BlogManagementRestService {
    private RestTemplate restTemplate;

    @Value("${blogManagementService.baseUrl}")
    private String url;

    @PostConstruct
    public void init() {
        this.restTemplate = new RestTemplate();
    }

    public BlogPostBoundary getBlogById(String blogId, String bloggerEmail, String productId){

        // Set headers
        HttpHeaders headers = new HttpHeaders();

        // Set path variables
        Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("email", bloggerEmail);

        // Set query params
        URI targetUrl= UriComponentsBuilder.fromUriString(url)
                .path("/byUser/{email}")
                .uriVariables(urlParams)// Add path
                .queryParam("filterType", FILTER_TYPE.BY_PRODUCT.toString())
                .queryParam("filterValue", productId)
                .build()
                .encode()
                .toUri();

        HttpEntity<?> entity = new HttpEntity<>(headers);

        List<BlogPostBoundary> blogs = Arrays.stream(this.restTemplate.exchange(targetUrl,
                HttpMethod.GET,
                entity,
                BlogPostBoundary[].class)
                .getBody())
                .filter(blog->blog.getId().equals(blogId))
                .collect(Collectors.toList());

        // If blog doesn't exist throw exception
        if (blogs.isEmpty()) {
            throw new NotFoundException("No blog found matching: blogId=" + blogId + ", bloggerEmail=" + bloggerEmail + ", productId=" + productId
            + ". Please enter valid blog details");
        }
        // Else return the first in list (and only one, because filtered by id)
        return blogs.get(0);
    }
}