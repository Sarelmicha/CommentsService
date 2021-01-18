package com.cloudcomputing.commentsservice.boundaries;

import java.util.Date;

public class TicketBoundary {

    private String id;
    private String name;
    private String email;
    private Boolean isOpen;
    private Date createdTimeStamp;
    private Date closingTimeStamp;

    public TicketBoundary() {}

    public TicketBoundary(String name, String email, Date createdTimeStamp, Date closingTimeStamp) {
        this.name = name;
        this.email = email;
        this.createdTimeStamp = createdTimeStamp;
        this.closingTimeStamp = closingTimeStamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
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

    public Date getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(Date createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public Date getClosingTimeStamp() {
        return closingTimeStamp;
    }

    public void setClosingTimeStamp(Date closingTimeStamp) {
        this.closingTimeStamp = closingTimeStamp;
    }
}
