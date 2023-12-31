package com.main.travelApp.ui.components;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.main.travelApp.R;
import com.main.travelApp.viewmodels.SignUpViewModel;

public class EnterEmailDialog extends DialogFragment {
    private Context context;
    private SignUpViewModel viewModel;
    public EnterEmailDialog(Context context, SignUpViewModel viewModel){
        this.context = context;
        this.viewModel = viewModel;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_enter_email_for_resetpass, null);

        AlertDialog dialog = builder
                .setView(dialogView)
                .setCancelable(false)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        EditText email = dialogView.findViewById(R.id.edtEmail);
        Button btnSend = dialogView.findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailStr = email.getText().toString();
                viewModel.sendForgotPasswordMail(emailStr);
            }
        });

        return dialog;
    }

}
