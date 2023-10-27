package com.main.travelApp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView txtForgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init(){
        String forgotPassword = "Quên mật khẩu";
        txtForgotPassword = findViewById(R.id.txtForgotPassword);

        SpannableString mSpannableString = new SpannableString(forgotPassword);
        mSpannableString.setSpan(new UnderlineSpan(), 0, forgotPassword.length(), 0);
        txtForgotPassword.setText(mSpannableString);
    }
}