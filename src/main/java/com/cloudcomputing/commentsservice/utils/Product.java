package com.cloudcomputing.commentsservice.utils;

public class Product {
    private String id;

    Product(){}

    Product(String id){
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
