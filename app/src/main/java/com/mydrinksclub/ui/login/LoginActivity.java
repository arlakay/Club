package com.mydrinksclub.ui.login;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mydrinksclub.R;
import com.mydrinksclub.ui.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by E.R.D on 4/2/2016.
 */
public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.uid)EditText et_username;
    @BindView(R.id.password)EditText et_pass;
    String URL_LOGIN = "http://192.168.0.101/api/in_mydrinks/index.php/api/user/login";
    //RequestQueue requestQueue = Volley.newRequestQueue(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

//        btnLogin.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View view) {
//                String username = editUserId.getText().toString();
//                String password = editPass.getText().toString();
//                // Check for empty data in the form
//                if (username.trim().length() > 0 && password.trim().length() > 0) {
//
//                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//
//                    //Log.e("Problem", connMgr.toString());
//                    if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
////                        checkLogin(username, password);
//                    }
//                    else{ Toast.makeText(getApplicationContext(),
//                            "No Internet Access Available!", Toast.LENGTH_LONG)
//                            .show();}
//                } else {
//                    // Prompt user to enter credentials
//                    Toast.makeText(getApplicationContext(),
//                            "Please enter Phone Number and Password", Toast.LENGTH_LONG)
//                            .show();
//                }
//            }
//        });

    }

    @OnClick(R.id.buttonlogin)
    public void toMainMenu(View view) {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        String username = et_username.getText().toString();
        String password = et_pass.getText().toString();
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            //checkLogin(username, password);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "No Internet Access Available!", Toast.LENGTH_LONG).show();
        }

    }

    private void checkLogin(final String username, final String password){
        String tag_string_req = "req_login";
        StringRequest strReq = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    String username = jObj.getString("email");
                    String password = jObj.getString("password");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("username",username);
                    Log.d("username = ", username);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error != null) { //NULL DATA GIVEN
                    try {
                        JSONObject errorBody = new JSONObject(new String(error.networkResponse.data));
                        Toast.makeText(LoginActivity.this, errorBody.optString("messages"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    if (error.toString().equals("com.android.volley.ServerError")){
//                        Toast.makeText(getApplicationContext(), "Username and Password did not match", Toast.LENGTH_LONG).show();
//                    } else{
//                        error.printStackTrace();
//                        Toast.makeText(getApplicationContext(), "Please try again later\n"+error.getMessage(), Toast.LENGTH_LONG).show();
//                    }

                }else{ //DATA GIVEN
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError { // isikan parameter2 nya disini
                HashMap<String, String> params = new HashMap<>();
                params.put("username-or-email", username);
                params.put("password", password);
                return params;
            }
        };
//        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        //requestQueue.add(strReq);
    }
}