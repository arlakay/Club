package com.mydrinksclub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ERD on 1/15/2017.
 */

public class ProductResponse {
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("messages")
    @Expose
    public String messages;
    @SerializedName("bottles")
    @Expose
    public List<Product> bottles = null;

    public ProductResponse(Boolean status, String messages, List<Product> bottles) {
        this.status = status;
        this.messages = messages;
        this.bottles = bottles;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public List<Product> getBottles() {
        return bottles;
    }

    public void setBottles(List<Product> bottles) {
        this.bottles = bottles;
    }
}
