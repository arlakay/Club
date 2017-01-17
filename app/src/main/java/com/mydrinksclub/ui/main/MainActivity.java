package com.mydrinksclub.ui.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.mydrinksclub.R;
import com.mydrinksclub.ui.home.HomeFragment;
import com.mydrinksclub.ui.notifications.NotificationFragment;
import com.mydrinksclub.ui.sale.SaleFragment;
import com.mydrinksclub.ui.store.StoreFragment;
import com.mydrinksclub.utility.SessionManagerLogin;
import com.mydrinksclub.utility.SessionManagerScan;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.view_pager)AHBottomNavigationViewPager viewPager;
    @BindView(R.id.bottom_navigation)AHBottomNavigation bottomNavigation;
    @BindView(R.id.txt_name)TextView txtName;

    private MainPagerAdapter2 mPagerAdapter;
    private AHBottomNavigationAdapter navigationAdapter;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private boolean useMenuResource = true;
    private int[] tabColors;
    private SessionManagerLogin sessionManagerLogin;
    private SessionManagerScan smScan;

    String spinner_item;
    private ArrayAdapter<CharSequence> adapterCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        smScan = new SessionManagerScan(getApplicationContext());
        sessionManagerLogin = new SessionManagerLogin(getApplicationContext());
        sessionManagerLogin.checkLogin();

        if (!sessionManagerLogin.isLoggedIn()) {
            sessionManagerLogin.setLogin(false);
            sessionManagerLogin.logoutUser();
            finish();
        }

        HashMap<String, String> user = sessionManagerLogin.getUserDetails();
        String firstName = user.get(SessionManagerLogin.KEY_FIRST_NAME);
        String lastName = user.get(SessionManagerLogin.KEY_LAST_NAME);
        String storeCode = user.get(SessionManagerLogin.KEY_STORE_CODE);

        if(storeCode == null){
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.row_spinner_storecode);
            dialog.setCancelable(true);

            final Spinner spinnerStoreCode = (Spinner) dialog.findViewById(R.id.spinner1);
            Button button = (Button) dialog.findViewById(R.id.button1);

            adapterCat = ArrayAdapter.createFromResource(this, R.array.store_code,
                    android.R.layout.simple_spinner_item);
            adapterCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerStoreCode.setAdapter(adapterCat);
            spinnerStoreCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent,
                                           View view, int position, long id) {
                    spinner_item = getSortByPosition(position);
                    Log.d("TAG", spinner_item);
                    sessionManagerLogin.createStoreCode(spinner_item);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();
//                    Toast.makeText(getApplicationContext(), spinner_item , Toast.LENGTH_LONG).show();
                }
            });

            dialog.show();
        }else{
//            Toast.makeText(getApplicationContext(), storeCode, Toast.LENGTH_SHORT).show();
        }

        txtName.setText(firstName + " " + lastName);

        initUI();

        viewPager = (AHBottomNavigationViewPager) findViewById(R.id.view_pager);
        mPagerAdapter = new MainPagerAdapter2(getSupportFragmentManager());
        viewPager.setAdapter(mPagerAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
//                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    private void setupToolbar() {
//        setSupportActionBar(toolbar);
//
//        if (getSupportActionBar() == null) {
//            throw new IllegalStateException("Activity must implement toolbar");
//        }
//    }

    private String getSortByPosition(int position) {
        switch (position) {
            case 0:
                return "20000030";
            case 1:
                return "20000031";
            default:
                return "20000030";
        }
    }

    private void initUI() {

        if (useMenuResource) {
            tabColors = getApplicationContext().getResources().getIntArray(R.array.tab_colors);
            navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.bottom_navigation_menu_5);
            navigationAdapter.setupWithBottomNavigation(bottomNavigation, tabColors);

            bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));
            bottomNavigation.setAccentColor(Color.parseColor("#333333"));
            bottomNavigation.setInactiveColor(Color.parseColor("#999999"));
            bottomNavigation.setForceTint(true);
            bottomNavigation.setForceTitlesDisplay(true);
        }

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                if (position == 0){
                    HomeFragment homeFragment = new HomeFragment();
                    Bundle arguments = new Bundle();
    //                    arguments.putString("Id", txtId);
                    homeFragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                            .replace(R.id.root_frame, homeFragment, "homeFragment")
                            .commit();
                }
                if (position == 1) {
                    smScan.clearScan();

                    SaleFragment saleFragment = new SaleFragment();
                    Bundle arguments = new Bundle();
    //                    arguments.putString("Id", txtId);
                    saleFragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                            .replace(R.id.root_frame, saleFragment, "saleFragment")
                            .commit();

                }
                if (position == 2){
                    StoreFragment storeFragment = new StoreFragment();
                    Bundle arguments = new Bundle();
    //                    arguments.putString("Id", txtId);
                    storeFragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                            .replace(R.id.root_frame, storeFragment, "storeFragment")
                            .commit();
                }
                if (position == 3){
                    NotificationFragment notificationFragment = new NotificationFragment();
                    Bundle arguments = new Bundle();
    //                    arguments.putString("Id", txtId);
                    notificationFragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                            .replace(R.id.root_frame, notificationFragment, "notificationFragment")
                            .commit();
                }
                else {

                }
            return true;
            }
        });

    }


    public void confirmExit(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setTitle("Exit");
        alertDialogBuilder.setMessage("Are you sure you want to exit?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                doubleBackToExitPressedOnce = false;
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doubleBackToExitPressedOnce = false;
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        //Checking for fragment count on backstack
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            confirmExit();
//            Toast.makeText(this,"Please click BACK again to exit.", Toast.LENGTH_SHORT).show();
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    doubleBackToExitPressedOnce = false;
//                }
//            }, 2000);
        } else {
            super.onBackPressed();
            return;
        }
    }


}
