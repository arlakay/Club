package com.mydrinksclub.model;

/**
 * Created by ERD on 12/17/2016.
 */

public class GeneralResponse {
    public boolean status;
    public String messages;

    public GeneralResponse(boolean status, String messages) {
        this.status = status;
        this.messages = messages;
    }
}
