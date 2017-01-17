package com.mydrinksclub.api.services;

import com.mydrinksclub.model.BottleBarcodeResponse;
import com.mydrinksclub.model.BottleKeepBottle;
import com.mydrinksclub.model.CustomerResponse;
import com.mydrinksclub.model.LoginResponse;
import com.mydrinksclub.model.NotificationResponse;
import com.mydrinksclub.model.ProductResponse;
import com.mydrinksclub.model.PurchaseResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by ILM on 6/29/2016.
 */
public interface ApiService {
    //Login
    @FormUrlEncoded
    @POST("user/login")
    Call<LoginResponse> login(@Field("username-or-email") String username_or_email,
                              @Field("password") String password);

    //Purchase - Scan Customer
    @FormUrlEncoded
    @POST("action/checkQRCodeCustomer")
    Call<CustomerResponse> purchaseScanCustomer(@Field("customer_code") String customer_code);

    //Purchase - Scan Bottle Barcode
    @FormUrlEncoded
    @POST("action/checkBarcodeBottle")
    Call<BottleBarcodeResponse> purchaseScanBottleBarcode(@Field("product_code") String product_code);

    //Purchase - Get All Product
    @GET("product/getAll")
    Call<ProductResponse> getAllProduct();

    //Purchase - Submit
    @FormUrlEncoded
    @POST("action/purchase")
    Call<PurchaseResponse> purchaseSubmit(@Field("serial_number")String serial_number,
                                          @Field("customer_code")String customer_code,
                                          @Field("purchase_store_code")String purchase_store_code,
                                          @Field("product_code")String product_code,
                                          @Field("product_description")String product_description);

    //Bottle Keep - Scan Bottle QR Code
    @FormUrlEncoded
    @POST("action/checkQRCodeBottle")
    Call<BottleKeepBottle> bottleKeepScanBottleQRCode(@Field("serial_number") String serial_number);

    //Bottle Keep - Submit
    @FormUrlEncoded
    @POST("action/store")
    Call<BottleKeepBottle> bottleKeepSubmit(@Field("latest_volume") String latest_volume,
                                            @Field("serial_number") String serial_number,
                                            @Field("month") String month,
                                            @Field("store_code") String store_code,
                                            @Field("rack_number") String rack_number,
                                            @Field("note") String note);

    //GetMessage
    @GET("message/getAll")
    Call<NotificationResponse> getMessage();

}
