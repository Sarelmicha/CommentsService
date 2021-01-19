package com.cloudcomputing.commentsservice.boundaries;

public class TicketBoundary {

    private String name;
    private String email;
    private String externalServiceType;
    private String externalId;

    public TicketBoundary() {}

    public TicketBoundary(String name, String email) {
        this.name = name;
        this.email = email;
    }


    public String getExternalServiceType() {
        return externalServiceType;
    }

    public void setExternalServiceType(String externalServiceType) {
        this.externalServiceType = externalServiceType;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
