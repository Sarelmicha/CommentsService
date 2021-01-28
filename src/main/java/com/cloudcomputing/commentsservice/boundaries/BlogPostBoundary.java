package com.cloudcomputing.commentsservice.boundaries;


import com.cloudcomputing.commentsservice.utils.Product;
import com.cloudcomputing.commentsservice.utils.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BlogPostBoundary {

    private String id;
    private User user;
    private Product product;
    private Date postingTimeStamp;
    private String language;
    private Map<String,Object> postContent;

    public BlogPostBoundary(){
        this.postContent=new HashMap<>();
    }

    public BlogPostBoundary(User user, Product product, Date postingTimeStamp, String language, Map<String,Object> postContent) {
        this.user = user;
        this.product = product;
        this.postingTimeStamp = postingTimeStamp;
        this.language = language;
        this.postContent = postContent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getPostingTimeStamp() {
        return postingTimeStamp;
    }

    public void setPostingTimeStamp(Date postingTimeStamp) {
        this.postingTimeStamp = postingTimeStamp;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Map<String, Object> getPostContent() {
        return postContent;
    }

    public void setPostContent(Map<String, Object> postContent) {
        this.postContent = postContent;
    }

    @Override
    public String toString() {
        return "blog id: " + this.id + " user: " + this.user.getEmail() + " product id: " + this.product.getId();
    }
}
