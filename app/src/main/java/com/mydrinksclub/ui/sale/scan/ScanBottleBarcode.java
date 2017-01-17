package com.mydrinksclub.ui.sale.scan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mydrinksclub.R;
import com.mydrinksclub.ui.sale.SaleFragment;
import com.mydrinksclub.ui.store.CustomScannerActivity;
import com.mydrinksclub.utility.SessionManagerScan;

import butterknife.ButterKnife;

/**
 * Created by ERD on 12/1/2016.
 */

public class ScanBottleBarcode extends Fragment {
    private SessionManagerScan smScan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scan_bottle_barcode, container, false);
        ButterKnife.bind(this, view);

        smScan = new SessionManagerScan(getActivity().getApplicationContext());

        IntentIntegrator.forSupportFragment(this).setOrientationLocked(false)
                .setCaptureActivity(CustomScannerActivity.class).initiateScan();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getActivity(), "Cancelled from fragment", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                SaleFragment saleFragment = new SaleFragment();
                Bundle arguments = new Bundle();
                //                    arguments.putString("Id", txtId);
                saleFragment.setArguments(arguments);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                        .replace(R.id.root_frame, saleFragment, "saleFragment")
                        .commit();
            } else {
                Toast.makeText(getActivity(), "Scanned from fragment: " + result.getContents(), Toast.LENGTH_SHORT).show();
                String code = result.getContents();

                smScan.createBottleBarcode(code);
                SaleFragment saleFragment = new SaleFragment();
                Bundle arguments = new Bundle();
                saleFragment.setArguments(arguments);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                        .replace(R.id.root_frame, saleFragment, "saleFragment")
                        .commit();
            }
        }
    }

}
