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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mydrinksclub.R;
import com.mydrinksclub.api.RestApi;
import com.mydrinksclub.api.services.ApiService;
import com.mydrinksclub.model.PurchaseResponse;
import com.mydrinksclub.utility.SessionManagerLogin;
import com.mydrinksclub.utility.SessionManagerScan;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ERD on 11/23/2016.
 */

public class SaleConfirmFragment extends Fragment {
    @BindView(R.id.txt_nama_user)TextView txtUser;
    @BindView(R.id.txt_product_name)TextView txtNamaProduk;

    private static final String TAG = SaleConfirmFragment.class.getSimpleName();
    public String namaUser, productName, custQRCode, bottleQRCode, bottleBarcode, storeCode, rack, note;
    private SessionManagerScan smScan;
    private SessionManagerLogin sessionManagerLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale_confirmation, container, false);
        ButterKnife.bind(this, view);

        sessionManagerLogin = new SessionManagerLogin(getActivity().getApplicationContext());
        smScan = new SessionManagerScan(getActivity().getApplicationContext());

        getData();

        HashMap<String, String> login = sessionManagerLogin.getUserDetails();
        storeCode = login.get(SessionManagerLogin.KEY_STORE_CODE);

        HashMap<String, String> scan = smScan.getScanDetails();
        custQRCode = scan.get(smScan.KEY_CUSTOMER_QRCODE);
        bottleQRCode = scan.get(smScan.KEY_BOTTLE_QRCODE);
        bottleBarcode = scan.get(smScan.KEY_BOTTLE_BARCODE);

        Log.e(TAG, "Barcode :::"+ bottleBarcode);
        Log.e(TAG, "Nama Botol :::"+ productName);

        txtUser.setText(namaUser);
        txtNamaProduk.setText(productName);

        return view;
    }

    public void getData(){
        namaUser = getArguments().getString("namaUser");
        productName = getArguments().getString("productName");

    }

    @OnClick(R.id.btn_sale_submit)
    public void submit(View view) {
        purchaseSubmit(bottleQRCode, custQRCode, storeCode, bottleBarcode, productName);
    }

    private void purchaseSubmit(String serial_number, String customer_code, String store_code,
                                String product_code, String product_description) {
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Loading...");

        ApiService apiService =
                RestApi.getClient().create(ApiService.class);

        Call<PurchaseResponse> call = apiService.purchaseSubmit(serial_number, customer_code, store_code, product_code, product_description);
        call.enqueue(new Callback<PurchaseResponse>() {
            @Override
            public void onResponse(Call<PurchaseResponse>call, Response<PurchaseResponse> response) {
                dialog.dismiss();

                Log.d(TAG, "Status Code = " + response.code());
                Log.d(TAG, "Data received: " + new Gson().toJson(response.body()));

                if (response.code() == 200 && response.isSuccessful()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Success ", Toast.LENGTH_SHORT).show();

                    SaleSuccessFragment saleSuccessFragment = new SaleSuccessFragment();
                    Bundle arguments = new Bundle();

                    arguments.putString("message", response.body().messages);
                    arguments.putString("namaUser", namaUser);
                    arguments.putString("productName", productName);

                    saleSuccessFragment.setArguments(arguments);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                            .replace(R.id.root_frame, saleSuccessFragment, "saleSuccessFragment")
                            .addToBackStack("saleSuccessFragment")
                            .commit();

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Failed !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PurchaseResponse> call, Throwable t) {
                dialog.dismiss();

                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("The connection has timed out\n" +
                        "The server is taking too long to respond.");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();

            }
        });
    }

}
