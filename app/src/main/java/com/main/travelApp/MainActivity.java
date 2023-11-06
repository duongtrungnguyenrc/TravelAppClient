package com.main.travelApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.main.travelApp.databinding.ActivityLoginBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        init();
    }

    private void init(){
        String forgotPassword = "Quên mật khẩu";

        SpannableString mSpannableString = new SpannableString(forgotPassword);
        mSpannableString.setSpan(new UnderlineSpan(), 0, forgotPassword.length(), 0);
        binding.txtForgotPassword.setText(mSpannableString);

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}