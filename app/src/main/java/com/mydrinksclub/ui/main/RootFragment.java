package com.mydrinksclub.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydrinksclub.R;
import com.mydrinksclub.ui.home.HomeFragment;

public class RootFragment extends Fragment {
	private static final String TAG = "RootFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.root_fragment, container, false);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.root_frame, new HomeFragment());
		transaction.commit();

		return view;
	}

}
