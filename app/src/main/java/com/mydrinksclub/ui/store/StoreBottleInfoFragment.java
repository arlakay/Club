package com.mydrinksclub.ui.store;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mydrinksclub.R;
import com.mydrinksclub.api.RestApi;
import com.mydrinksclub.api.services.ApiService;
import com.mydrinksclub.model.BottleBarcodeResponse;
import com.mydrinksclub.model.BottleKeepBottle;
import com.mydrinksclub.utility.SessionManagerLogin;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ERD on 11/28/2016.
 */

public class StoreBottleInfoFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    @BindView(R.id.spin_valid_until)Spinner spin_validUntil;
    @BindView(R.id.txt_username)TextView txtUsername;
    @BindView(R.id.et_bottle_name)EditText etBottleName;
    @BindView(R.id.et_rack)EditText etRack;
    @BindView(R.id.et_bottle_note)EditText etBottleNote;

    private SessionManagerLogin sessionManagerLogin;
    private static final String TAG = StoreBottleInfoFragment.class.getSimpleName();
    public String customerproduct_id, customer_code, serial_number, product_code, purchase_store_code,
            purchase_date, keep_store_code, keep_start_date, keep_end_date, volume, take_date,
            rack_number, note, valueValidUntil, nama_user, nama_botol, storeCode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_info, container, false);
        ButterKnife.bind(this, view);

        sessionManagerLogin = new SessionManagerLogin(getActivity().getApplicationContext());

        HashMap<String, String> user = sessionManagerLogin.getUserDetails();
        storeCode = user.get(SessionManagerLogin.KEY_STORE_CODE);
        Log.d(TAG, storeCode);

        getData();
        checkBottleBarcode(product_code);
        setupSpinner();

        txtUsername.setText(nama_user);
        etRack.setText(rack_number);
        etBottleNote.setText(note);

        return view;
    }

    public void getData(){
        customerproduct_id = getArguments().getString("customerproduct_id");
        customer_code = getArguments().getString("customer_code");
        serial_number = getArguments().getString("serial_number");
        product_code = getArguments().getString("product_code");
        purchase_store_code = getArguments().getString("purchase_store_code");
        purchase_date = getArguments().getString("purchase_date");
        keep_store_code = getArguments().getString("keep_store_code");
        keep_start_date = getArguments().getString("keep_start_date");
        keep_end_date = getArguments().getString("keep_end_date");
        volume = getArguments().getString("volume");
        take_date = getArguments().getString("take_date");
        rack_number = getArguments().getString("rack_number");
        note = getArguments().getString("note");
        nama_user = getArguments().getString("nama_user");

    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(getActivity(),
                        R.array.valid_until,
                        R.layout.support_simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spin_validUntil.setAdapter(adapter);
        spin_validUntil.setOnItemSelectedListener(this);
    }

    @OnClick(R.id.btn_store_info_next)
    public void storeNext(View view) {
        rack_number = etRack.getText().toString();
        note = etBottleNote.getText().toString();

        bottleKeepSubmit(volume, serial_number, valueValidUntil, storeCode, rack_number, note);

//        StoreConfirmFragment storeConfirmFragment = new StoreConfirmFragment();
//        Bundle arguments = new Bundle();
//        arguments.putString("volume", volume);
//        storeConfirmFragment.setArguments(arguments);
//        getActivity().getSupportFragmentManager().beginTransaction()
//                .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
//                .replace(R.id.root_frame, storeConfirmFragment, "storeConfirmFragment")
//                .addToBackStack("storeConfirmFragment")
//                .commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        valueValidUntil = adapterView.getItemAtPosition(i).toString();
        if(valueValidUntil.contains("1")){
            valueValidUntil = "1";
        }if(valueValidUntil.contains("2")){
            valueValidUntil = "2";
        }if(valueValidUntil.contains("3")){
            valueValidUntil = "3";
        }
        Log.e("TAG", valueValidUntil);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void bottleKeepSubmit(String lastest_volume, String serialnumber, String month, String store_code, String rackNumb, String note) {
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Loading...");

        ApiService apiService =
                RestApi.getClient().create(ApiService.class);

        Call<BottleKeepBottle> call = apiService.bottleKeepSubmit(lastest_volume, serialnumber, month, store_code, rackNumb, note);
        call.enqueue(new Callback<BottleKeepBottle>() {
            @Override
            public void onResponse(Call<BottleKeepBottle>call, Response<BottleKeepBottle> response) {
                dialog.dismiss();

                Log.d(TAG, "Status Code = " + response.code());
                Log.d(TAG, "Data received: " + new Gson().toJson(response.body()));

                if (response.code() == 200 && response.isSuccessful()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Success ", Toast.LENGTH_SHORT).show();

                    StoreConfirmFragment storeConfirmFragment = new StoreConfirmFragment();
                    Bundle arguments = new Bundle();
                    arguments.putString("volume", volume);
                    arguments.putString("customer_code", customer_code);
                    arguments.putString("nama_user", nama_user);
                    arguments.putString("nama_botol", nama_botol);
                    arguments.putString("rack", etRack.getText().toString());
                    arguments.putString("note", etBottleNote.getText().toString());
                    arguments.putString("current_keep_date", response.body().keepStartDate);
                    arguments.putString("keep_end_date", response.body().keepEndDate);
                    arguments.putString("purchase_date", purchase_date);

                    storeConfirmFragment.setArguments(arguments);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                            .replace(R.id.root_frame, storeConfirmFragment, "storeConfirmFragment")
                            .addToBackStack("storeConfirmFragment")
                            .commit();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(LoginActivity.this, LoginConfirmationActivity.class);
//                    startActivity(intent);
//                    finish();
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

    private void checkBottleBarcode(String product_code) {
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
                    nama_botol = response.body().product_description;
                    etBottleName.setText(nama_botol);
                } else {

                }
            }

            @Override
            public void onFailure(Call<BottleBarcodeResponse>call, Throwable t) {
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
