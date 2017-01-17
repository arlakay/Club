package com.mydrinksclub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ERD on 12/28/2016.
 */

public class BottleKeepBottle {
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("messages")
    @Expose
    public String messages;
    @SerializedName("customerproduct_id")
    @Expose
    public String customerproductId;
    @SerializedName("customer_code")
    @Expose
    public String customerCode;
    @SerializedName("serial_number")
    @Expose
    public String serialNumber;
    @SerializedName("product_code")
    @Expose
    public String productCode;
    @SerializedName("purchase_store_code")
    @Expose
    public String purchaseStoreCode;
    @SerializedName("purchase_date")
    @Expose
    public String purchaseDate;
    @SerializedName("keep_store_code")
    @Expose
    public String keepStoreCode;
    @SerializedName("keep_start_date")
    @Expose
    public String keepStartDate;
    @SerializedName("keep_end_date")
    @Expose
    public String keepEndDate;
    @SerializedName("keep_date")
    @Expose
    public String keepDate;
    @SerializedName("volume")
    @Expose
    public String volume;
    @SerializedName("take_date")
    @Expose
    public String takeDate;
    @SerializedName("rack_number")
    @Expose
    public String rackNumber;
    @SerializedName("note")
    @Expose
    public String note;
    @SerializedName("record_status")
    @Expose
    public String recordStatus;

    public BottleKeepBottle(Boolean status, String messages, String customerproductId, String customerCode, String serialNumber, String productCode, String purchaseStoreCode, String purchaseDate, String keepStoreCode, String keepDate, String volume, String takeDate, String rackNumber, String note, String recordStatus) {
        this.status = status;
        this.messages = messages;
        this.customerproductId = customerproductId;
        this.customerCode = customerCode;
        this.serialNumber = serialNumber;
        this.productCode = productCode;
        this.purchaseStoreCode = purchaseStoreCode;
        this.purchaseDate = purchaseDate;
        this.keepStoreCode = keepStoreCode;
        this.keepDate = keepDate;
        this.volume = volume;
        this.takeDate = takeDate;
        this.rackNumber = rackNumber;
        this.note = note;
        this.recordStatus = recordStatus;
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

    public String getCustomerproductId() {
        return customerproductId;
    }

    public void setCustomerproductId(String customerproductId) {
        this.customerproductId = customerproductId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getPurchaseStoreCode() {
        return purchaseStoreCode;
    }

    public void setPurchaseStoreCode(String purchaseStoreCode) {
        this.purchaseStoreCode = purchaseStoreCode;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getKeepStoreCode() {
        return keepStoreCode;
    }

    public void setKeepStoreCode(String keepStoreCode) {
        this.keepStoreCode = keepStoreCode;
    }

    public String getKeepDate() {
        return keepDate;
    }

    public void setKeepDate(String keepDate) {
        this.keepDate = keepDate;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getTakeDate() {
        return takeDate;
    }

    public void setTakeDate(String takeDate) {
        this.takeDate = takeDate;
    }

    public String getRackNumber() {
        return rackNumber;
    }

    public void setRackNumber(String rackNumber) {
        this.rackNumber = rackNumber;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }
}
