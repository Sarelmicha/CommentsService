package com.cloudcomputing.commentsservice.utils;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class User {
    @NotNull
    private String email;

    @NotNull
    private String password;

    public User(){

    }

    User(String email){
        this.email=email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) { this.email = email;}
}
