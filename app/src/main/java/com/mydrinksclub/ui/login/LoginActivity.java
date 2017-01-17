package com.mydrinksclub.ui.login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mydrinksclub.R;
import com.mydrinksclub.api.RestApi;
import com.mydrinksclub.api.services.ApiService;
import com.mydrinksclub.model.LoginResponse;
import com.mydrinksclub.ui.main.MainActivity;
import com.mydrinksclub.utility.SessionManagerLogin;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by E.R.D on 4/2/2016.
 */
public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.uid)EditText et_username;
    @BindView(R.id.password)EditText et_pass;

    private static final String TAG = LoginActivity.class.getSimpleName();
    private SessionManagerLogin sessionManagerLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        sessionManagerLogin = new SessionManagerLogin(getApplicationContext());

        if (sessionManagerLogin.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @OnClick(R.id.buttonlogin)
    public void toMainMenu(View view) {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        String username = et_username.getText().toString();
        String password = et_pass.getText().toString();
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            loginAuth(username, password);
        }else{
            Toast.makeText(getApplicationContext(), "No Internet Access Available!", Toast.LENGTH_LONG).show();
        }
    }

    private void loginAuth(String uname, String pass) {
        final ProgressDialog dialog = ProgressDialog.show(this, "", "Loading...");

        ApiService apiService =
                RestApi.getClient().create(ApiService.class);

        Call<LoginResponse> call = apiService.login(uname, pass);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse>call, Response<LoginResponse> response) {
                dialog.dismiss();

                Log.d(TAG, "Status Code = " + response.code());
                Log.d(TAG, "Data received: " + new Gson().toJson(response.body()));

                if (response.code() == 200 && response.body().status && response.body().messages.contains("Success")) {
                    String uid = response.body().userId;
                    String uCode = response.body().userCode;
                    String privilege = response.body().privilege;
                    String fName = response.body().firstName;
                    String lName = response.body().lastName;
                    String email = response.body().email;
                    String picture = response.body().picture;

                    sessionManagerLogin.setLogin(true);
                    sessionManagerLogin.createLoginSession(uid, uCode, privilege, fName, lName, email, picture);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(LoginActivity.this, LoginConfirmationActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse>call, Throwable t) {
                dialog.dismiss();

                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("The connection has timed out\n" +
                        "The server is taking too long to respond.");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();

            }
        });
    }

}