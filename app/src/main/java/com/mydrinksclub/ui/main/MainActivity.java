package com.mydrinksclub.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.view_pager)AHBottomNavigationViewPager viewPager;
    @BindView(R.id.bottom_navigation)AHBottomNavigation bottomNavigation;
    @BindView(R.id.txt_name)TextView txtName;

//    private FragmentProfile currentFragment;
    private MainPagerAdapter2 mPagerAdapter;
    private AHBottomNavigationAdapter navigationAdapter;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private boolean useMenuResource = true;
    private int[] tabColors;
    private FloatingActionButton floatingActionButton;
//    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        sessionManager = new SessionManager(getApplicationContext());
//        sessionManager.checkLogin();

//        if (!sessionManager.isLoggedIn()) {
//            sessionManager.setLogin(false);
//            sessionManager.logoutUser();
//            finish();
//        }

//        HashMap<String, String> user = sessionManager.getUserDetails();
//        String firstName = user.get(SessionManager.KEY_FIRST_NAME);
//        String lastName = user.get(SessionManager.KEY_LAST_NAME);
//        String point = user.get(SessionManager.KEY_POINT_BALANCE);

//        txtName.setText(firstName + " " + lastName);
//        txtPoint.setText(point + " pts");

        initUI();

        viewPager = (AHBottomNavigationViewPager) findViewById(R.id.view_pager);
        mPagerAdapter = new MainPagerAdapter2(getSupportFragmentManager());
        viewPager.setAdapter(mPagerAdapter);

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

//        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
//            @Override public void onPositionChange(int y) {
//                Log.d("DemoActivity", "BottomNavigation Position: " + y);
//            }
//        });

//        viewPager.setOffscreenPageLimit(4);
//        adapter = new MainViewPagerAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(adapter);

//        currentFragment = adapter.getCurrentFragment();

//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Setting custom colors for notification
//                AHNotification notification = new AHNotification.Builder()
//                        .setText(":)")
//                        .setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.color_notification_back))
//                        .setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color_notification_text))
//                        .build();
//                bottomNavigation.setNotification(notification, 1);
//                Snackbar.make(bottomNavigation, "Snackbar with bottom navigation",
//                        Snackbar.LENGTH_SHORT).show();
//            }
//        }, 3000);

//        bottomNavigation.setDefaultBackgroundResource(R.drawable.bottom_navigation_background);
    }


}
