package com.mydrinksclub.ui.store;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mydrinksclub.R;
import com.mydrinksclub.ui.home.HomeFragment;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ERD on 11/28/2016.
 */

public class StoreConfirmFragment extends Fragment   {
    @BindView(R.id.txt_current_date)TextView currentDate;
    @BindView(R.id.txt_volume_store_confirm)TextView txtVolume;
    @BindView(R.id.txt_username)TextView txtUsername;
    @BindView(R.id.txt_brand)TextView txtBrandName;
    @BindView(R.id.txt_rack)TextView txtRackNumber;
    @BindView(R.id.txt_bottleNote)TextView txtBottleNote;
    @BindView(R.id.txt_keep_end_date)TextView txtValid;
    @BindView(R.id.txt_purchase_date)TextView txtPurchaseDate;

    private static final String TAG = StoreConfirmFragment.class.getSimpleName();
    private String volume, customer_code, nama_user, nama_botol, rack, note, currentKeepDate, valid, purchaseDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_confirmation, container, false);
        ButterKnife.bind(this, view);

        getData();


        Calendar c = Calendar.getInstance();
        int d = c.get(Calendar.DATE);
        int m = c.get(Calendar.MONTH);
        int y = c.get(Calendar.YEAR);
        int h = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);

//        currentDate.setText(String.valueOf(d + "-" + (m + 1) + "-" + y + " ; " + h + ":" + minutes));
        currentDate.setText(currentKeepDate);

        txtVolume.setText(volume);
        txtUsername.setText(nama_user);
        txtBrandName.setText(nama_botol);
        txtRackNumber.setText(rack);
        txtBottleNote.setText(note);
        txtValid.setText(valid);
        txtPurchaseDate.setText(purchaseDate);

        return view;
    }

    public void getData(){
        volume = getArguments().getString("volume");
        customer_code = getArguments().getString("customer_code");
        nama_user = getArguments().getString("nama_user");
        nama_botol = getArguments().getString("nama_botol");
        rack = getArguments().getString("rack");
        note = getArguments().getString("note");
        currentKeepDate = getArguments().getString("current_keep_date");
        valid = getArguments().getString("keep_end_date");
        purchaseDate = getArguments().getString("purchase_date");
    }

    @OnClick(R.id.btn_store_confirm_next)
    public void next(View view) {
        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        StoreFragment storeFragment = new StoreFragment();
        Bundle arguments = new Bundle();
        //                    arguments.putString("Id", txtId);
        storeFragment.setArguments(arguments);
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                .replace(R.id.root_frame, storeFragment, "storeFragment")
                .commit();
    }

    @OnClick(R.id.btn_store_confirm_finish)
    public void finish(View view) {
        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        HomeFragment homeFragment = new HomeFragment();
        Bundle arguments = new Bundle();
        //                    arguments.putString("Id", txtId);
        homeFragment.setArguments(arguments);
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                .replace(R.id.root_frame, homeFragment, "homeFragment")
                .commit();
    }

//    private void displayLocation() {
//
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            return;
//        }
//        mLastLocation = LocationServices.FusedLocationApi
//                .getLastLocation(mGoogleApiClient);
//
//        if (mLastLocation != null) {
//            double latitude = mLastLocation.getLatitude();
//            double longitude = mLastLocation.getLongitude();
//            Log.d(TAG, latitude + "," + longitude);
//
//            lat.setText(String.valueOf(latitude));
//            lng.setText(String.valueOf(longitude));
//
//        } else {
//            lat.setText("(Couldn't get the location. Make sure location is enabled on the device)");
//        }
//    }
//
//    private void togglePeriodicLocationUpdates() {
//        if (!mRequestingLocationUpdates) {
//            // Changing the button text
////            btnStartLocationUpdates
////                    .setText(getString(R.string.btn_stop_location_updates));
//
//            mRequestingLocationUpdates = true;
//
//            // Starting the location updates
//            startLocationUpdates();
//
//            Log.d(TAG, "Periodic location updates started!");
//
//        } else {
//            // Changing the button text
////            btnStartLocationUpdates
////                    .setText(getString(R.string.btn_start_location_updates));
//
//            mRequestingLocationUpdates = false;
//
//            // Stopping the location updates
//            stopLocationUpdates();
//
//            Log.d(TAG, "Periodic location updates stopped!");
//        }
//    }
//
//    protected synchronized void buildGoogleApiClient() {
//        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API).build();
//    }
//
//    protected void createLocationRequest() {
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(UPDATE_INTERVAL);
//        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
//    }
//
//    private boolean checkPlayServices() {
//        int resultCode = GooglePlayServicesUtil
//                .isGooglePlayServicesAvailable(getActivity());
//        if (resultCode != ConnectionResult.SUCCESS) {
//            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
//                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
//                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
//            } else {
//                Toast.makeText(getActivity().getApplicationContext(),
//                        "This device is not supported.", Toast.LENGTH_LONG)
//                        .show();
//                getActivity().finish();
//            }
//            return false;
//        }
//        return true;
//    }
//
//    protected void startLocationUpdates() {
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        LocationServices.FusedLocationApi.requestLocationUpdates(
//                mGoogleApiClient, mLocationRequest, this);
//
//    }
//
//    protected void stopLocationUpdates() {
//        LocationServices.FusedLocationApi.removeLocationUpdates(
//                mGoogleApiClient, this);
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        if (mGoogleApiClient != null) {
//            mGoogleApiClient.connect();
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        checkPlayServices();
//        // Resuming the periodic location updates
//        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
//            startLocationUpdates();
//        }
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mGoogleApiClient.isConnected()) {
//            mGoogleApiClient.disconnect();
//        }
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        stopLocationUpdates();
//    }
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        displayLocation();
//
//        if (mRequestingLocationUpdates) {
//            startLocationUpdates();
//        }
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//        mGoogleApiClient.connect();
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
//                + connectionResult.getErrorCode());
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//        // Assign the new location
//        mLastLocation = location;
//
//        Toast.makeText(getActivity(), "Location changed!",
//                Toast.LENGTH_SHORT).show();
//
//        // Displaying the new location on UI
//        displayLocation();
//    }

}
