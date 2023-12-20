package com.main.travelApp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Toast;

import com.main.travelApp.databinding.ActivitySignUpBinding;
import com.main.travelApp.request.SignUpRequest;
import com.main.travelApp.ui.components.EnterConfirmCodeDialog;
import com.main.travelApp.ui.components.EnterConfirmResetPassCodeDialog;
import com.main.travelApp.ui.components.EnterEmailDialog;
import com.main.travelApp.ui.components.EnterNewPasswordDialog;
import com.main.travelApp.viewmodels.SignUpViewModel;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivitySignUpBinding binding;
    private SignUpViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        viewModel.setContext(this);
        setContentView(view);
        init();
    }

    private void init(){
        String forgotPassStr = "Quên mật khẩu";
        SpannableString spannableString = new SpannableString(forgotPassStr);
        spannableString.setSpan(new UnderlineSpan(), 0, forgotPassStr.length(), 0);
        binding.txtForgotPassword.setText(spannableString);
        binding.txtSignIn.setOnClickListener(this);
        binding.btnSignUp.setOnClickListener(this);
        binding.txtForgotPassword.setOnClickListener(this);
        viewModel.getIsSignUpSuccess().observe(this, isSignUpSuccess -> {
            if(isSignUpSuccess){
                EnterConfirmCodeDialog dialog = new EnterConfirmCodeDialog(SignUpActivity.this, viewModel);
                dialog.show(getSupportFragmentManager(), "ENTER_CODE_DIALOG");
            }
        });
        viewModel.getIsResetPasswordMailSent().observe(this, isResetPassSent -> {
            if(isResetPassSent){
                EnterConfirmResetPassCodeDialog dialog = new EnterConfirmResetPassCodeDialog(SignUpActivity.this, viewModel);
                dialog.show(getSupportFragmentManager(), "ENTER_CODE_DIALOG");
            }
        });

        viewModel.getIsResetPasswordCodeCorrect().observe(this, isResetPassCodeCorrect -> {
            if(isResetPassCodeCorrect){
                EnterNewPasswordDialog dialog = new EnterNewPasswordDialog(this, viewModel);
                dialog.show(getSupportFragmentManager(), "ENTER_PASSWORD_DIALOG");
            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view == binding.txtSignIn){
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }else if (view == binding.btnSignUp){
                String password = binding.edtPassword.getText().toString();
                String confirmPassword = binding.edtRePassword.getText().toString();
                String email = binding.edtEmail.getText().toString();
                String firstName = binding.edtFirstName.getText().toString();
                String lastName = binding.edtLastName.getText().toString();
                String phone = binding.edtPhone.getText().toString();
                if(email.isEmpty() || firstName.isEmpty() ||
                lastName.isEmpty() || phone.isEmpty() || password.isEmpty()
                || confirmPassword.isEmpty()){
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else{
                    if(isValidEmail(email))
                        if(password.equals(confirmPassword)){
                            if(binding.ckbAgreeTerms.isChecked()){
                                SignUpRequest request = new SignUpRequest();
                                request.setAddress(null);
                                request.setEmail(email);
                                request.setPassword(password);
                                request.setFullName(firstName + " " + lastName);
                                request.setPhone(phone);
                                request.setAvatar(null);
                                request.setRole(null);

                                viewModel.signUp(request);
                            }else
                                Toast.makeText(this, "Bạn chưa chấp nhận điều khoản!", Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(this, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "Email không đúng định dạng!", Toast.LENGTH_SHORT).show();
                }
        }else if(view == binding.txtForgotPassword){
            EnterEmailDialog dialog = new EnterEmailDialog(SignUpActivity.this, viewModel);
            dialog.show(getSupportFragmentManager(), "ENTER_EMAIL_DIALOG");
        }
    }
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}