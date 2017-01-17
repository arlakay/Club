package com.mydrinksclub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ERD on 1/15/2017.
 */

public class Product {
    @SerializedName("product_id")
    @Expose
    public String productId;
    @SerializedName("product_code")
    @Expose
    public String productCode;
    @SerializedName("category_code")
    @Expose
    public String categoryCode;
    @SerializedName("brand_code")
    @Expose
    public String brandCode;
    @SerializedName("product_description")
    @Expose
    public String productDescription;
    @SerializedName("volume")
    @Expose
    public String volume;
    @SerializedName("price")
    @Expose
    public String price;
    @SerializedName("point")
    @Expose
    public String point;
    @SerializedName("picture")
    @Expose
    public String picture;
    @SerializedName("record_status")
    @Expose
    public String recordStatus;

    public Product(String productId, String productCode, String categoryCode, String brandCode, String productDescription, String volume, String price, String point, String picture, String recordStatus) {
        this.productId = productId;
        this.productCode = productCode;
        this.categoryCode = categoryCode;
        this.brandCode = brandCode;
        this.productDescription = productDescription;
        this.volume = volume;
        this.price = price;
        this.point = point;
        this.picture = picture;
        this.recordStatus = recordStatus;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }
}
