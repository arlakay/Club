package com.mydrinksclub.ui.sale;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydrinksclub.R;
import com.mydrinksclub.ui.main.MainActivity;
import com.mydrinksclub.utility.SessionManagerScan;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ERD on 11/23/2016.
 */

public class SaleSuccessFragment extends Fragment {
    private SessionManagerScan smScan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale_success, container, false);
        ButterKnife.bind(this, view);

        smScan = new SessionManagerScan(getActivity().getApplicationContext());

        return view;
    }

    @OnClick(R.id.btn_sale_close)
    public void close(View view) {
        smScan.clearScan();
        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }

}
