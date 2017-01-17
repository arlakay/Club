package com.mydrinksclub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ERD on 1/17/2017.
 */

public class NotificationResponse {
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("messages")
    @Expose
    public List<Notification> messages = null;
}
