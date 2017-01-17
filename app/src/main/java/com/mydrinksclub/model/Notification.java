package com.mydrinksclub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ERD on 1/17/2017.
 */

public class Notification {
    @SerializedName("message_id")
    @Expose
    public String messageId;
    @SerializedName("merchant_code")
    @Expose
    public Object merchantCode;
    @SerializedName("store_code")
    @Expose
    public Object storeCode;
    @SerializedName("user_code")
    @Expose
    public Object userCode;
    @SerializedName("title")
    @Expose
    public Object title;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("record_status")
    @Expose
    public Object recordStatus;

}
