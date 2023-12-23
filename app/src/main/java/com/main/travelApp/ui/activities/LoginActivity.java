package com.main.travelApp.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.main.travelApp.R;
import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.databinding.ActivityLoginBinding;
import com.main.travelApp.models.AuthInstance;
import com.main.travelApp.repositories.impls.AuthRepositoryImpl;
import com.main.travelApp.ui.components.EnterConfirmCodeDialog;
import com.main.travelApp.ui.components.EnterConfirmResetPassCodeDialog;
import com.main.travelApp.ui.components.EnterEmailDialog;
import com.main.travelApp.ui.components.EnterNewPasswordDialog;
import com.main.travelApp.utils.ScreenManager;
import com.main.travelApp.utils.SharedPreferenceKeys;
import com.main.travelApp.viewmodels.SignUpViewModel;

import java.util.HashSet;
import java.util.Set;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private static final int REQ_ONE_TAP = 2;
    private static final int REQ_GG_SIGN_IN = 3;
    private boolean showOneTapUI = true;
    private AuthRepositoryImpl authRepository;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private SharedPreferences sharedPreferences;
    private SignUpViewModel viewModel;
    private GoogleSignInOptions gso;
    private GoogleSignInClient googleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        sharedPreferences = getSharedPreferences(SharedPreferenceKeys.USER_SHARED_PREFS, MODE_PRIVATE);
        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        viewModel.setContext(this);
        loggedInFilter();
        setContentView(view);
        ScreenManager.enableFullScreen(getWindow());
        init();
    }

    private void init(){
        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build())
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(getString(R.string.default_web_client))
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        String forgotPassword = "Quên mật khẩu";
        authRepository = AuthRepositoryImpl.getInstance();
        SpannableString mSpannableString = new SpannableString(forgotPassword);
        mSpannableString.setSpan(new UnderlineSpan(), 0, forgotPassword.length(), 0);
        binding.txtForgotPassword.setText(mSpannableString);

        binding.edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        binding.btnInputType.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                binding.edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                binding.btnInputType.setBackgroundDrawable(getDrawable(R.drawable.baseline_visibility_off_16));
            }
            else{
                binding.edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.btnInputType.setBackgroundDrawable(getDrawable(R.drawable.baseline_visibility_16));
            }
        });

        binding.btnSignIn.setOnClickListener(view -> {
                ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Chờ một xíu...");
                progressDialog.show();
                authRepository.authentication(
                        binding.edtEmail.getText().toString(),
                        binding.edtPassword.getText().toString(),
                        new ActionCallback<>() {
                            @Override
                            public void onSuccess(AuthInstance result) {
                                progressDialog.dismiss();
                                saveUserToSharedPref(result);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            @Override
                            public void onFailure(Integer status, String message) {
                                progressDialog.dismiss();
                                if(status == AuthInstance.NOT_ACTIVATED_CODE){
                                    viewModel.setConfirmToken(message);
                                    EnterConfirmCodeDialog dialog = new EnterConfirmCodeDialog(LoginActivity.this, viewModel, true);
                                    dialog.show(getSupportFragmentManager(), "CONFIRM_CODE_DIALOG");
                                    Toast.makeText(getApplicationContext(), "Tài khoản của bạn đã bị vô hiệu hóa", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(String message) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                }
        );

        binding.btnGoogle.setOnClickListener(view -> {
            ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Chờ một xíu...");
            progressDialog.show();
            oneTapClient.beginSignIn(signInRequest)
                    .addOnSuccessListener(this, new OnSuccessListener<BeginSignInResult>() {
                        @Override
                        public void onSuccess(BeginSignInResult result) {
                            try {
                                startIntentSenderForResult(
                                        result.getPendingIntent().getIntentSender(), REQ_ONE_TAP,
                                        null, 0, 0, 0);
                            } catch (IntentSender.SendIntentException e) {
                                Log.e("Google-Auth", "Couldn't start One Tap UI: " + e.getLocalizedMessage());
                            }finally {
                                progressDialog.dismiss();
                            }
                        }
                    })
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Intent signInIntent = googleSignInClient.getSignInIntent();
                            startActivityForResult(signInIntent, REQ_GG_SIGN_IN);
                            Log.d("Google-Auth", e.getLocalizedMessage());
                        }
                    });
        });

        binding.txtForgotPassword.setOnClickListener(view -> {
            EnterEmailDialog dialog = new EnterEmailDialog(LoginActivity.this, viewModel);
            dialog.show(getSupportFragmentManager(), "ENTER_EMAIL_DIALOG");
        });

        viewModel.getIsResetPasswordMailSent().observe(this, isResetPassSent -> {
            if(isResetPassSent){
                EnterConfirmResetPassCodeDialog dialog = new EnterConfirmResetPassCodeDialog(LoginActivity.this, viewModel);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_ONE_TAP:
                try {
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                    String idToken = credential.getGoogleIdToken();
                    if (idToken !=  null) {
                        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                        progressDialog.setMessage("Chờ một xíu...");
                        progressDialog.show();
                        authRepository.authenticationWithGoogleToken(idToken, new ActionCallback<AuthInstance>() {
                            @Override
                            public void onSuccess(AuthInstance result) {
                                progressDialog.dismiss();
                                saveUserToSharedPref(result);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Integer status, String message) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (ApiException e) {
                    switch (e.getStatusCode()) {
                        case CommonStatusCodes.CANCELED:
                            Log.d("Google-Auth-Result", "One-tap dialog was closed.");
                            showOneTapUI = false;
                            break;
                        case CommonStatusCodes.NETWORK_ERROR:
                            Log.d("Google-Auth-Result", "One-tap encountered a network error.");
                            break;
                        default:
                            Log.d("Google-Auth-Result", "Couldn't get credential from result."
                                    + e.getLocalizedMessage());
                            break;
                    }
                }
                break;
            case REQ_GG_SIGN_IN:
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task){
        try {
            ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Chờ một xíu...");
            progressDialog.show();
            GoogleSignInAccount account = task.getResult(ApiException.class);
            String idToken = account.getIdToken();
            authRepository.authenticationWithGoogleToken(idToken, new ActionCallback<AuthInstance>() {
                @Override
                public void onSuccess(AuthInstance result) {
                    progressDialog.dismiss();
                    saveUserToSharedPref(result);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Integer status, String message) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            });

        } catch (ApiException e) {
            Log.w("Google-Auth-Result", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, "signInResult:failed code=" + e.getStatusCode(), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserToSharedPref(AuthInstance authInstance){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> rolesSet = new HashSet<>();
        authInstance.getRoles().forEach(role -> {
            rolesSet.add(role);
        });

        editor.putString(SharedPreferenceKeys.USER_ACCESS_TOKEN, getString(R.string.token_type) + " " + authInstance.getAccessToken());
        editor.putString(SharedPreferenceKeys.USER_ID, authInstance.getId());
        editor.putString(SharedPreferenceKeys.USER_EMAIL, authInstance.getEmail());
        editor.putString(SharedPreferenceKeys.USER_AVATAR, authInstance.getAvatar());
        editor.putString(SharedPreferenceKeys.USER_ADDRESS, authInstance.getAddress());
        editor.putString(SharedPreferenceKeys.USER_FULL_NAME, authInstance.getFullName());
        editor.putString(SharedPreferenceKeys.USER_PHONE, authInstance.getPhone());
        editor.putBoolean(SharedPreferenceKeys.USER_ACTIVE, authInstance.isActive());
        editor.putStringSet(SharedPreferenceKeys.USER_ROLES, rolesSet);

        editor.apply();
    }

    private void loggedInFilter(){
        if(sharedPreferences.getString("accessToken", null) != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }
}