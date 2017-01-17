package com.mydrinksclub.ui.sale;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.mydrinksclub.R;
import com.mydrinksclub.api.RestApi;
import com.mydrinksclub.api.services.ApiService;
import com.mydrinksclub.model.BottleBarcodeResponse;
import com.mydrinksclub.model.CustomerResponse;
import com.mydrinksclub.model.Product;
import com.mydrinksclub.model.ProductResponse;
import com.mydrinksclub.ui.sale.scan.ScanBottleBarcode;
import com.mydrinksclub.ui.sale.scan.ScanBottleQRCode;
import com.mydrinksclub.ui.sale.scan.ScanCustomerQRCode;
import com.mydrinksclub.utility.SessionManagerScan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

/**
 * Created by ERD on 11/23/2016.
 */

public class SaleFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    @BindView(R.id.et_sale_customer_qrcode)EditText et_customer_qr;
    @BindView(R.id.et_sale_bottle_qrcode)EditText et_bottle_qr;
    @BindView(R.id.et_sale_bottle_barcode)EditText et_bottle_barcode;

    @BindView(R.id.img_customer_valid)ImageView imgCustomerValid;
    @BindView(R.id.img_customer_wrong)ImageView imgCustomerWrong;
    @BindView(R.id.img_bottlebarcode_valid)ImageView imgBottleBarcodeValid;
    @BindView(R.id.img_bottlebarcode_wrong)ImageView imgBottleBarcodeWrong;
    @BindView(R.id.img_bottleqr_valid)ImageView imgBottleQRValid;
    @BindView(R.id.img_bottleqr_wrong)ImageView imgBottleQRWrong;

    @BindView(R.id.img_bottlename_valid)ImageView imgBottleNameValid;
    @BindView(R.id.img_bottlename_wrong)ImageView imgBottleNameWrong;
    @BindView(R.id.spin_bottle_name)Spinner spinBootleName;
    @BindView(R.id.layout_spinner_bottle_name)LinearLayout layoutSpinNamaBotol;

    private static final String TAG = SaleFragment.class.getSimpleName();
    private SessionManagerScan smScan;
    private String custQRCode, bottleQRCode, bottleBarcode, valueBottleName, namaUser, productName;

    private ArrayAdapter<String> adapterCustomer;
    private ArrayList<String> custAr, codeAr;
    private List<Product> custmrList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale, container, false);
        ButterKnife.bind(this, view);

        smScan = new SessionManagerScan(getActivity().getApplicationContext());

        setupSpinner();

        custAr = new ArrayList<String>();
        codeAr = new ArrayList<String>();

        if (custAr != null) {
            setupSpinner();
        }

        HashMap<String, String> user = smScan.getScanDetails();
        custQRCode = user.get(smScan.KEY_CUSTOMER_QRCODE);
        bottleQRCode = user.get(smScan.KEY_BOTTLE_QRCODE);
        bottleBarcode = user.get(smScan.KEY_BOTTLE_BARCODE);

        et_customer_qr.setText(custQRCode);
        et_bottle_qr.setText(bottleQRCode);
        et_bottle_barcode.setText(bottleBarcode);

        if(et_customer_qr.length()>0){
            checkCustomer(custQRCode);
        }if(et_bottle_barcode.length()>0){
            checkBottleBarcode(bottleBarcode);
        }if(et_bottle_qr.length()>0){
            checkBottleQR(bottleQRCode);
        }

        return view;
    }

    private void setupSpinner() {
        spinBootleName.setOnItemSelectedListener(this);
    }

    @OnClick(R.id.btn_sale_next)
    public void next(View view) {
        SaleConfirmFragment saleConfirmFragment = new SaleConfirmFragment();
        Bundle arguments = new Bundle();

        arguments.putString("namaUser", namaUser);
        arguments.putString("productName", valueBottleName);

        saleConfirmFragment.setArguments(arguments);
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                .replace(R.id.root_frame, saleConfirmFragment, "saleConfirmFragment")
                .addToBackStack("saleConfirmFragment")
                .commit();
    }

    @OnClick(R.id.btn_sale_customer_qrcode)
    public void scan1(View view) {
        ScanCustomerQRCode scanCustomerQRCode = new ScanCustomerQRCode();
        Bundle arguments = new Bundle();
        //arguments.putString("Id", txtId);
        scanCustomerQRCode.setArguments(arguments);
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                .replace(R.id.root_frame, scanCustomerQRCode, "scanCustomerQRCode")
                .commit();
    }

    @OnClick(R.id.btn_sale_bottle_qrcode)
    public void scan2(View view) {
        ScanBottleQRCode scanBottleQRCode = new ScanBottleQRCode();
        Bundle arguments = new Bundle();
        //arguments.putString("Id", txtId);
        scanBottleQRCode.setArguments(arguments);
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                .replace(R.id.root_frame, scanBottleQRCode, "scanBottleQRCode")
                .commit();
    }

    @OnClick(R.id.btn_sale_bottle_barcode)
    public void scan3(View view) {
        ScanBottleBarcode scanBottleBarcode = new ScanBottleBarcode();
        Bundle arguments = new Bundle();
        //arguments.putString("Id", txtId);
        scanBottleBarcode.setArguments(arguments);
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                .replace(R.id.root_frame, scanBottleBarcode, "scanBottleBarcode")
                .commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.spin_bottle_name:
                valueBottleName = adapterView.getSelectedItem().toString();

                Log.e(TAG, valueBottleName);

                if(valueBottleName != null){
                    checkBottleName(valueBottleName);
                }

                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void checkBottleQR(String bottle_qr) {
        if(bottle_qr.contains("PC2016-")){
            imgBottleQRValid.setVisibility(View.VISIBLE);
            imgBottleQRWrong.setVisibility(GONE);
        }else{
            imgBottleQRValid.setVisibility(GONE);
            imgBottleQRWrong.setVisibility(View.VISIBLE);
        }
    }

    private void checkCustomer(String customer_code) {
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Loading...");

        ApiService apiService =
                RestApi.getClient().create(ApiService.class);

        Call<CustomerResponse> call = apiService.purchaseScanCustomer(customer_code);
        call.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse>call, Response<CustomerResponse> response) {
                dialog.dismiss();

                Log.d(TAG, "Status Code = " + response.code());
                Log.d(TAG, "Data received: " + new Gson().toJson(response.body()));

                if (response.code() == 200 && response.isSuccessful()) {
                    String message = response.body().messages;
                    namaUser = response.body().first_name + " " + response.body().last_name;

                    imgCustomerValid.setVisibility(View.VISIBLE);
                    imgCustomerWrong.setVisibility(GONE);
                } else {
                    imgCustomerValid.setVisibility(GONE);
                    imgCustomerWrong.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<CustomerResponse>call, Throwable t) {
                dialog.dismiss();

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("The connection has timed out\n" +
                        "The server is taking too long to respond."+
                t.getMessage());
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();

            }
        });
    }

    private void checkBottleBarcode(final String product_code) {
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Loading...");

        ApiService apiService =
                RestApi.getClient().create(ApiService.class);

        Call<BottleBarcodeResponse> call = apiService.purchaseScanBottleBarcode(product_code);
        call.enqueue(new Callback<BottleBarcodeResponse>() {
            @Override
            public void onResponse(Call<BottleBarcodeResponse>call, Response<BottleBarcodeResponse> response) {
                dialog.dismiss();

                Log.d(TAG, "Status Code = " + response.code());
                Log.d(TAG, "Data received: " + new Gson().toJson(response.body()));

                if (response.code() == 200 && response.isSuccessful() && response.body().status) {
                    valueBottleName = response.body().product_description;

                    imgBottleBarcodeValid.setVisibility(View.VISIBLE);
                    imgBottleBarcodeWrong.setVisibility(GONE);
                    layoutSpinNamaBotol.setVisibility(View.GONE);
                } else {
                    imgBottleBarcodeValid.setVisibility(GONE);
                    imgBottleBarcodeWrong.setVisibility(View.VISIBLE);
                    layoutSpinNamaBotol.setVisibility(View.VISIBLE);
                    smScan.clearBottleBarcode();
                    Log.e(TAG, "Barcode Abis Check =" + bottleBarcode);

                    getProduct();
                }
            }

            @Override
            public void onFailure(Call<BottleBarcodeResponse>call, Throwable t) {
                dialog.dismiss();

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("The connection has timed out\n" +
                        "The server is taking too long to respond."+
                        t.getMessage());
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();

            }
        });
    }

    private void checkBottleName(String bottle_name) {
        if(bottle_name.contains("Not Listed")){
            imgBottleNameValid.setVisibility(View.GONE);
            imgBottleNameWrong.setVisibility(View.VISIBLE);
        }
        if(bottle_name.contains("Default")){
            imgBottleNameValid.setVisibility(View.GONE);
            imgBottleNameWrong.setVisibility(View.GONE);
        }
        if(!bottle_name.contains("Default") && !bottle_name.contains("Not Listed")){
            imgBottleNameValid.setVisibility(View.VISIBLE);
            imgBottleNameWrong.setVisibility(GONE);
        }
    }

    private void getProduct() {
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "loading...");

        ApiService apiService =
                RestApi.getClient().create(ApiService.class);

        Call<ProductResponse> call = apiService.getAllProduct();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse>call, Response<ProductResponse> response) {
                dialog.dismiss();

                custmrList = response.body().getBottles();
//                Log.d(TAG, "Status Code = " + response.code());
//                Log.d(TAG, "Data received: " + new Gson().toJson(funcList));

                if (response.code() == 200 && response.body().getStatus()){

                    if(custmrList != null) {
                        for (int i = 0; i < custmrList.size(); i++) {
                            custAr.add(custmrList.get(i).getProductDescription());
//                            + "-" + custmrList.get(i).getProductCode());
                        }

                        adapterCustomer = new ArrayAdapter<>(getActivity(),
                                android.R.layout.simple_spinner_dropdown_item, custAr);

                        adapterCustomer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinBootleName.setAdapter(adapterCustomer);
                    }

                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Product Tidak Ada");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse>call, Throwable t) {
                dialog.dismiss();

                Log.e(TAG, t.toString());

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Network Error");
                alertDialog.setMessage("Failed to connect to the server");
                //alertDialog.setIcon(R.drawable.tick);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                        startActivity(getActivity().getIntent());
                    }
                });
                alertDialog.show();
            }
        });
    }

}
