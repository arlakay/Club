package com.mydrinksclub.ui.store;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mydrinksclub.R;
import com.mydrinksclub.api.RestApi;
import com.mydrinksclub.api.services.ApiService;
import com.mydrinksclub.model.BottleKeepBottle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ERD on 11/23/2016.
 */

public class StoreFragment extends Fragment {
    @BindView(R.id.et_store_bottle_qrcode)EditText bottle_qr;
    @BindView(R.id.img_bottleqr_keep_valid)ImageView imgKeepValid;
    @BindView(R.id.img_bottleqr_keep_wrong)ImageView imgKeepWrong;

    private static final String TAG = StoreFragment.class.getSimpleName();
    private String toast;
    private String status_cek = "wrong";
    BottleKeepBottle bottle;
    boolean skipOnChange = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        ButterKnife.bind(this, view);

        bottle_qr.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        // Identifier of the action. This will be either the identifier you supplied,
                        // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                        if (actionId == EditorInfo.IME_ACTION_DONE
                                || event.getAction() == KeyEvent.ACTION_DOWN
                                || event.getAction() == KeyEvent.ACTION_UP
                                || event.getKeyCode() == KeyEvent.KEYCODE_BACK ) {
                            checkBottleQRCode(v.getText().toString());
                            return true;
                        }
                        // Return true if you have consumed the action, else false.
                        return false;
                    }
                });

        return view;
    }

    @OnClick(R.id.btn_store_next)
    public void storeNext(View view) {
        if (status_cek.contains("valid")) {
            StoreSliderFragment storeSliderFragment = new StoreSliderFragment();
            Bundle arguments = new Bundle();

            arguments.putString("customerproduct_id", bottle.getCustomerproductId());
            arguments.putString("customer_code", bottle.customerCode);
            arguments.putString("serial_number", bottle.serialNumber);
            arguments.putString("product_code", bottle.productCode);
            arguments.putString("purchase_store_code", bottle.purchaseStoreCode);
            arguments.putString("purchase_date", bottle.purchaseDate);
            arguments.putString("keep_store_code", bottle.keepStoreCode);
//            arguments.putString("keep_start_date", bottle.keepStartDate);
//            arguments.putString("keep_end_date", bottle.keepEndDate);
            arguments.putString("volume", bottle.volume);
            arguments.putString("take_date", bottle.takeDate);
            arguments.putString("rack_number", bottle.rackNumber);
            arguments.putString("note", bottle.note);

            storeSliderFragment.setArguments(arguments);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                    .replace(R.id.root_frame, storeSliderFragment, "storeSliderFragment")
                    .addToBackStack("storeSliderFragment")
                    .commit();
        }else{
            Toast.makeText(getActivity(), "QR Code tidak valid, harap ulangi!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_store_scan_bottle_qrcode)
    public void scan(View view) {
        IntentIntegrator.forSupportFragment(this).setOrientationLocked(false).setCaptureActivity(CustomScannerActivity.class).initiateScan();
    }

    private void displayToast() {
        if(getActivity() != null && toast != null) {
            Toast.makeText(getActivity(), toast, Toast.LENGTH_LONG).show();
            toast = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                toast = "Cancelled from fragment";
            } else {
                toast = "Scanned from fragment: " + result.getContents();
                bottle_qr.setFocusable(false);
                bottle_qr.setText(result.getContents());
                checkBottleQRCode(bottle_qr.getText().toString());
            }
            displayToast();
        }
    }

    private void checkBottleQRCode(String serialnumber) {
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Loading...");

        ApiService apiService =
                RestApi.getClient().create(ApiService.class);

        Call<BottleKeepBottle> call = apiService.bottleKeepScanBottleQRCode(serialnumber);
        call.enqueue(new Callback<BottleKeepBottle>() {
            @Override
            public void onResponse(Call<BottleKeepBottle>call, Response<BottleKeepBottle> response) {
                dialog.dismiss();

                Log.d(TAG, "Status Code = " + response.code());
                Log.d(TAG, "Data received: " + new Gson().toJson(response.body()));

                if (response.code() == 200 && response.isSuccessful() && response.body().status) {
//                    Toast.makeText(getActivity().getApplicationContext(), "QR Valid ", Toast.LENGTH_SHORT).show();
                    bottle = new BottleKeepBottle(response.body().status, response.body().messages,
                            response.body().customerproductId, response.body().customerCode, response.body().serialNumber,
                            response.body().productCode, response.body().purchaseStoreCode, response.body().purchaseDate,
                            response.body().keepStoreCode, response.body().keepDate, response.body().volume,
                            response.body().takeDate, response.body().rackNumber, response.body().note,
                            response.body().recordStatus);

                    status_cek = "valid";

                    imgKeepValid.setVisibility(View.VISIBLE);
                    imgKeepWrong.setVisibility(View.GONE);
                } else {
//                    Toast.makeText(getActivity().getApplicationContext(), "QR Tidak Valid !", Toast.LENGTH_SHORT).show();
                    status_cek = "wrong";

                    imgKeepValid.setVisibility(View.GONE);
                    imgKeepWrong.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<BottleKeepBottle>call, Throwable t) {
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
