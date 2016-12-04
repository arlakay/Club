package com.mydrinksclub.ui.sale;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydrinksclub.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ERD on 11/23/2016.
 */

public class SaleConfirmFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale_confirmation, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.btn_sale_submit)
    public void submit(View view) {
        SaleSuccessFragment saleSuccessFragment = new SaleSuccessFragment();
        Bundle arguments = new Bundle();
        //                    arguments.putString("Id", txtId);
        saleSuccessFragment.setArguments(arguments);
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                .replace(R.id.root_frame, saleSuccessFragment, "saleSuccessFragment")
                .addToBackStack("saleSuccessFragment")
                .commit();
    }

}
