package com.mydrinksclub.ui.store;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydrinksclub.R;

import butterknife.ButterKnife;

/**
 * Created by ERD on 11/28/2016.
 */

public class StoreConfirmFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_confirmation, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
