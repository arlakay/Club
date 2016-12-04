package com.mydrinksclub.ui.sale;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mydrinksclub.R;
import com.mydrinksclub.ui.sale.scan.ScanBottleBarcode;
import com.mydrinksclub.ui.sale.scan.ScanBottleQRCode;
import com.mydrinksclub.ui.sale.scan.ScanCustomerQRCode;
import com.mydrinksclub.utility.SessionManagerScan;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ERD on 11/23/2016.
 */

public class SaleFragment extends Fragment {
    @BindView(R.id.et_sale_customer_qrcode)EditText et_customer_qr;
    @BindView(R.id.et_sale_bottle_qrcode)EditText et_bottle_qr;
    @BindView(R.id.et_sale_bottle_barcode)EditText et_bottle_barcode;

    private SessionManagerScan smScan;
    private String custQRCode, bottleQRCode, bottleBarcode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale, container, false);
        ButterKnife.bind(this, view);

        smScan = new SessionManagerScan(getActivity().getApplicationContext());

        HashMap<String, String> user = smScan.getScanDetails();
        custQRCode = user.get(smScan.KEY_CUSTOMER_QRCODE);
        bottleQRCode = user.get(smScan.KEY_BOTTLE_QRCODE);
        bottleBarcode = user.get(smScan.KEY_BOTTLE_BARCODE);

        et_customer_qr.setText(custQRCode);
        et_bottle_qr.setText(bottleQRCode);
        et_bottle_barcode.setText(bottleBarcode);

        return view;
    }

    @OnClick(R.id.btn_sale_next)
    public void next(View view) {
        SaleConfirmFragment saleConfirmFragment = new SaleConfirmFragment();
        Bundle arguments = new Bundle();
        //                    arguments.putString("Id", txtId);
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

}
