package com.mydrinksclub.ui.store;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mydrinksclub.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ERD on 11/23/2016.
 */

public class StoreFragment extends Fragment {
    @BindView(R.id.et_store_bottle_qrcode)EditText bottle_qr;
    private String toast;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.btn_store_next)
    public void storeNext(View view) {
        if (bottle_qr.length()>-1) {
            StoreSliderFragment storeSliderFragment = new StoreSliderFragment();
            Bundle arguments = new Bundle();
            //                    arguments.putString("Id", txtId);
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
            }

            // At this point we may or may not have a reference to the activity
            displayToast();
        }
    }

}
