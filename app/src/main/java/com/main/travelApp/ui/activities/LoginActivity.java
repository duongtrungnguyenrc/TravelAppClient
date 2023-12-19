package com.main.travelApp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.databinding.ActivityLoginBinding;
import com.main.travelApp.models.AuthInstance;
import com.main.travelApp.repositories.impls.AuthRepositoryImpl;
import com.main.travelApp.repositories.interfaces.AuthRepository;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    private AuthRepositoryImpl authRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        enableFullScreen();
        init();
    }

    private void init(){
        String forgotPassword = "Quên mật khẩu";
        authRepository = AuthRepositoryImpl.getInstance();
        SpannableString mSpannableString = new SpannableString(forgotPassword);
        mSpannableString.setSpan(new UnderlineSpan(), 0, forgotPassword.length(), 0);
        binding.txtForgotPassword.setText(mSpannableString);

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        binding.btnSignIn.setOnClickListener(view -> authRepository.authentication(
            binding.edtEmail.getText().toString(),
            binding.edtPassword.getText().toString(),
            new ActionCallback<>() {
                @Override
                public void onSuccess(AuthInstance result) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                    finish();
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(getApplicationContext(), "Đăng nhập thất bại: " + message, Toast.LENGTH_LONG).show();
                }
            })
        );

    }

    private void enableFullScreen() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }
}