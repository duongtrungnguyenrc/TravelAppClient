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

public class EnterConfirmCodeDialog extends DialogFragment {
    private Context context;
    private SignUpViewModel viewModel;
    private boolean isExistingAccount;
    public EnterConfirmCodeDialog(Context context, SignUpViewModel viewModel, boolean isExistingAccount){
        this.context = context;
        this.viewModel = viewModel;
        this.isExistingAccount = isExistingAccount;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_enter_code_dialog, null);

        AlertDialog dialog = builder
                .setView(dialogView)
                .setCancelable(false)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        EditText edtConfirmCode = dialogView.findViewById(R.id.edtConfirmCode);
        Button btnSend = dialogView.findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String confirmCode = edtConfirmCode.getText().toString();
                if(isExistingAccount)
                    viewModel.validateConfirmCode(confirmCode, dialog);
                else{
                    viewModel.validateConfirmCode(confirmCode);
                }
            }
        });

        return dialog;
    }

}
