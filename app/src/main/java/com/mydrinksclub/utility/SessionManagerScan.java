package com.mydrinksclub.utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.mydrinksclub.ui.login.LoginActivity;

import java.util.HashMap;

/**
 * Created by ERD on 12/1/2016.
 */

public class SessionManagerScan {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "MyDrinksClubScan";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public static final String KEY_CUSTOMER_QRCODE = "customer_qr_code";
    public static final String KEY_BOTTLE_QRCODE = "bottle_qr_code";
    public static final String KEY_BOTTLE_BARCODE = "bottle_barcode";

    public SessionManagerScan(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createCustQRCode(String barcode){
        editor.putBoolean(KEY_IS_LOGGEDIN, true);

        editor.putString(KEY_CUSTOMER_QRCODE, barcode);

        editor.commit();
    }

    public void createBottleQRCode(String barcode){
        editor.putBoolean(KEY_IS_LOGGEDIN, true);

        editor.putString(KEY_BOTTLE_QRCODE, barcode);

        editor.commit();
    }

    public void createBottleBarcode(String barcode){
        editor.putBoolean(KEY_IS_LOGGEDIN, true);

        editor.putString(KEY_BOTTLE_BARCODE, barcode);

        editor.commit();
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.commit();
    }

    public HashMap<String, String> getScanDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_CUSTOMER_QRCODE, pref.getString(KEY_CUSTOMER_QRCODE, null));
        user.put(KEY_BOTTLE_QRCODE, pref.getString(KEY_BOTTLE_QRCODE, null));
        user.put(KEY_BOTTLE_BARCODE, pref.getString(KEY_BOTTLE_BARCODE, null));

        return user;
    }

    public void clearScan(){
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

}
