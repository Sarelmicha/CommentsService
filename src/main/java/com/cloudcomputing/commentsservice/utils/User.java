package com.cloudcomputing.commentsservice.utils;

import javax.persistence.Embeddable;

@Embeddable
public class User {

    private String email;

    public User(){

    }

    User(String email){
        this.email=email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) { this.email = email;}
}
