package com.mydrinksclub.ui.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mydrinksclub.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_confirmation);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_login_confirmation_close)
    public void closeToWelcome(View view) { 
        finish();
    }

}
