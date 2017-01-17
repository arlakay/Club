package com.mydrinksclub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ERD on 12/17/2016.
 */

public class LoginResponse {

    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("messages")
    @Expose
    public String messages;
    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("merchant_code")
    @Expose
    public String merchantCode;
    @SerializedName("store_code")
    @Expose
    public String storeCode;
    @SerializedName("user_code")
    @Expose
    public String userCode;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("privilege")
    @Expose
    public String privilege;
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("last_name")
    @Expose
    public String lastName;
    @SerializedName("birth_date")
    @Expose
    public Object birthDate;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("street")
    @Expose
    public Object street;
    @SerializedName("city_id")
    @Expose
    public Object cityId;
    @SerializedName("country_id")
    @Expose
    public String countryId;
    @SerializedName("picture")
    @Expose
    public String picture;
    @SerializedName("record_status")
    @Expose
    public String recordStatus;

}
