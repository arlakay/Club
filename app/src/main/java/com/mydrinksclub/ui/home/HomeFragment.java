package com.mydrinksclub.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mydrinksclub.R;
import com.mydrinksclub.ui.login.LoginActivity;
import com.mydrinksclub.ui.sale.SaleFragment;
import com.mydrinksclub.ui.store.StoreFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends Fragment {
	@BindView(R.id.btn_home_logout)ImageView btnLogout;
	private static final String TAG = "HomeFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		ButterKnife.bind(this, view);

		return view;
	}

	@OnClick(R.id.btn_home_sale)
	public void viewSale(View view) {
		SaleFragment saleFragment = new SaleFragment();
		Bundle arguments = new Bundle();
//		arguments.putString("phone", phoneStore);
		saleFragment.setArguments(arguments);
		getActivity().getSupportFragmentManager().beginTransaction()
				.setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
				.replace(R.id.root_frame, saleFragment, "saleFragment")
				.addToBackStack("saleFragment")
				.commit();
	}

	@OnClick(R.id.btn_home_store)
	public void viewStore(View view) {
		StoreFragment storeFragment = new StoreFragment();
		Bundle arguments = new Bundle();
//		arguments.putString("phone", phoneStore);
		storeFragment.setArguments(arguments);
		getActivity().getSupportFragmentManager().beginTransaction()
				.setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
				.replace(R.id.root_frame, storeFragment, "storeFragment")
				.addToBackStack("storeFragment")
				.commit();
	}

	@OnClick(R.id.btn_home_logout)
	public void logout(View view) {
		Intent i = new Intent(getActivity(), LoginActivity.class);
		startActivity(i);
		getActivity().finish();
	}

}
