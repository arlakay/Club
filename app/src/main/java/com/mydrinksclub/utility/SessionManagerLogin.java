package com.mydrinksclub.utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.mydrinksclub.ui.login.LoginActivity;

import java.util.HashMap;

/**
 * Created by ERD on 12/1/2016.
 */

public class SessionManagerLogin {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "Luxe-Storage Club Field Suggestion";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public static final String KEY_USER_ID = "attendance_id";
    public static final String KEY_MERCHANT_CODE = "technician_code";
    public static final String KEY_STORE_CODE = "first_name";
    public static final String KEY_USER_CODE = "last_name";
    public static final String KEY_PRIVILEGE = "job_id";
    public static final String KEY_FIRST_NAME = "password";
    public static final String KEY_LAST_NAME = "barcode";
    public static final String KEY_EMAIL = "start_time";
    public static final String KEY_PICTURE = "start_time";

    public SessionManagerLogin(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String uid, String u_code, String privilege,
                                   String f_name, String l_name, String email, String pic){
        editor.putBoolean(KEY_IS_LOGGEDIN, true);

        editor.putString(KEY_USER_ID, uid);
        editor.putString(KEY_USER_CODE, u_code);
        editor.putString(KEY_PRIVILEGE, privilege);
        editor.putString(KEY_FIRST_NAME, f_name);
        editor.putString(KEY_LAST_NAME, l_name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PICTURE, pic);

        editor.commit();
    }

    public void createStoreCode(String store_code){
        editor.putBoolean(KEY_IS_LOGGEDIN, true);

        editor.putString(KEY_STORE_CODE, store_code);

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

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_USER_ID, pref.getString(KEY_USER_ID, null));
        user.put(KEY_MERCHANT_CODE, pref.getString(KEY_MERCHANT_CODE, null));
        user.put(KEY_STORE_CODE, pref.getString(KEY_STORE_CODE, null));
        user.put(KEY_USER_CODE, pref.getString(KEY_USER_CODE, null));
        user.put(KEY_PRIVILEGE, pref.getString(KEY_PRIVILEGE, null));
        user.put(KEY_FIRST_NAME, pref.getString(KEY_FIRST_NAME, null));
        user.put(KEY_LAST_NAME, pref.getString(KEY_LAST_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_PICTURE, pref.getString(KEY_PICTURE, null));

        return user;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        _context.startActivity(i);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

}
