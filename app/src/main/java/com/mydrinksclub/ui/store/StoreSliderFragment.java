package com.mydrinksclub.ui.store;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mydrinksclub.R;
import com.mydrinksclub.api.RestApi;
import com.mydrinksclub.api.services.ApiService;
import com.mydrinksclub.model.CustomerResponse;
import com.mydrinksclub.widget.VerticalSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ERD on 11/28/2016.
 */

public class StoreSliderFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    @BindView(R.id.bottle_slider)VerticalSeekBar slider;
    @BindView(R.id.txt_slider)TextView txtSlider;
    @BindView(R.id.txt_username)TextView txtUsername;

    int stepSize = 10;
    public String customerproduct_id, customer_code, serial_number, product_code, purchase_store_code,
            purchase_date, keep_store_code, keep_start_date, keep_end_date, volume, take_date,
            rack_number, note, nama_user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_slider, container, false);
        ButterKnife.bind(this, view);

        getData();
        checkCustomer(customer_code);

        slider.setOnSeekBarChangeListener(this);
        slider.incrementProgressBy(10);

//        txtUsername.setText(customer_code);

        if(volume == null) {
            volume = "0";
        }

        slider.setProgress(Integer.parseInt(volume));

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

        if(volume != null) {
            volume = volume.replaceAll("%", "");
        }

    }

    @OnClick(R.id.btn_store_submit)
    public void storeNext(View view) {
        if(txtSlider.getText().toString().equalsIgnoreCase("0%")){
            Toast.makeText(getActivity(), "Bottle can't be empty.", Toast.LENGTH_SHORT).show();

        }else{
            String volume_ubah = String.valueOf(slider.getProgress());

            StoreBottleInfoFragment storeBottleInfoFragment = new StoreBottleInfoFragment();
            Bundle arguments = new Bundle();
            arguments.putString("volume", volume_ubah);

            arguments.putString("customerproduct_id", customerproduct_id);
            arguments.putString("customer_code", customer_code);
            arguments.putString("serial_number", serial_number);
            arguments.putString("product_code", product_code);
            arguments.putString("purchase_store_code", purchase_store_code);
            arguments.putString("purchase_date", purchase_date);
            arguments.putString("keep_store_code", keep_store_code);
            arguments.putString("keep_start_date", keep_start_date);
            arguments.putString("keep_end_date", keep_end_date);
            arguments.putString("take_date", take_date);
            arguments.putString("rack_number", rack_number);
            arguments.putString("note", note);
            arguments.putString("nama_user", nama_user);

            storeBottleInfoFragment.setArguments(arguments);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                    .replace(R.id.root_frame, storeBottleInfoFragment, "storeBottleInfoFragment")
                    .addToBackStack("storeBottleInfoFragment")
                    .commit();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        i = ((int)Math.round(i/stepSize))*stepSize;
        seekBar.setProgress(i);
        txtSlider.setText(i + "%");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

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

//                Log.d(TAG, "Status Code = " + response.code());
//                Log.d(TAG, "Data received: " + new Gson().toJson(response.body()));

                if (response.code() == 200 && response.isSuccessful()) {
                    String message = response.body().messages;

                    nama_user = response.body().first_name + " " + response.body().last_name;
                    txtUsername.setText(nama_user);

                } else {

                    txtUsername.setText("No Username");
                }
            }

            @Override
            public void onFailure(Call<CustomerResponse>call, Throwable t) {
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
