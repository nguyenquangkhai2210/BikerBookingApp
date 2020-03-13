package com.example.bikebookingapp.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class OrderModel implements Serializable {
    private String id, email, startTime, endTime;

    public OrderModel() {
    }

    public OrderModel(String id, String email, String startTime, String endTime) {
        this.id = id;
        this.email = email;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
