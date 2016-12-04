package com.mydrinksclub.ui.store;

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
 * Created by ERD on 11/28/2016.
 */

public class StoreBottleInfoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_info, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.btn_store_info_next)
    public void storeNext(View view) {
        StoreConfirmFragment storeConfirmFragment = new StoreConfirmFragment();
        Bundle arguments = new Bundle();
        //                    arguments.putString("Id", txtId);
        storeConfirmFragment.setArguments(arguments);
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                .replace(R.id.root_frame, storeConfirmFragment, "storeConfirmFragment")
                .addToBackStack("storeConfirmFragment")
                .commit();
    }

}
