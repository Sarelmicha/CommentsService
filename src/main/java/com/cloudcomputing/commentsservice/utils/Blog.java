package com.cloudcomputing.commentsservice.utils;

import javax.persistence.Embeddable;

@Embeddable
public class Blog {
    private String bloggerEmail;
    private String productId;
    private String blogId;

    public Blog(){}

    Blog(String bloggerEmail, String productId, String blogId){

        this.bloggerEmail=bloggerEmail;
        this.productId=productId;
        this.blogId=blogId;
    }

    public String getBloggerEmail() {
        return bloggerEmail;
    }

    public void setBloggerEmail(String bloggerEmail) {
        this.bloggerEmail = bloggerEmail;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }
}
