package com.main.travelApp.ui.components;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.main.travelApp.R;
import com.main.travelApp.viewmodels.SignUpViewModel;

public class EnterNewPasswordDialog extends DialogFragment {
    private Context context;
    private SignUpViewModel viewModel;
    public EnterNewPasswordDialog(Context context, SignUpViewModel viewModel){
        this.context = context;
        this.viewModel = viewModel;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_enter_newpass_dialog, null);

        AlertDialog dialog = builder
                .setView(dialogView)
                .setCancelable(false)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        EditText newPass = dialogView.findViewById(R.id.edtNewPass);
        EditText reNewPass = dialogView.findViewById(R.id.edtReNewPass);
        Button btnSend = dialogView.findViewById(R.id.btnSend);
        ToggleButton btnInputType = dialogView.findViewById(R.id.btnInputType);

        newPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        reNewPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

        btnInputType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    newPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    btnInputType.setBackgroundDrawable(context.getDrawable(R.drawable.baseline_visibility_off_16));
                }
                else{
                    newPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    btnInputType.setBackgroundDrawable(context.getDrawable(R.drawable.baseline_visibility_16));
                }
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassStr = newPass.getText().toString();
                String reNewPassStr = reNewPass.getText().toString();
                if(newPassStr.equals(reNewPassStr)){
                    viewModel.resetPassword(newPassStr);
                }else{
                    Toast.makeText(context, "Xác nhận mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return dialog;
    }

}
