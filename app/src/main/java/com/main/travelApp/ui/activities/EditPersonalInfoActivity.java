package com.main.travelApp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.main.travelApp.R;
import com.main.travelApp.databinding.ActivityEditPersonalInfoBinding;
import com.main.travelApp.ui.components.MyDialog;

public class EditPersonalInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityEditPersonalInfoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditPersonalInfoBinding.inflate(getLayoutInflater());
        setupActionBar();
        setContentView(binding.getRoot());
        init();
    }

    private void init(){
        loadUser();
        setEvents();
    }

    private void loadUser(){
        Intent intent = getIntent();
        binding.txtEmail.setText(intent.getStringExtra("email"));
        binding.txtAddress.setText(intent.getStringExtra("address"));
        binding.txtUserName.setText(intent.getStringExtra("username"));
        binding.txtPhone.setText(intent.getStringExtra("phone"));
    }
    private void setEvents(){
        binding.btnUserName.setOnClickListener(this);
        binding.btnEmail.setOnClickListener(this);
        binding.btnAddress.setOnClickListener(this);
        binding.btnPhone.setOnClickListener(this);
        binding.btnSave.setOnClickListener(this);
    }

    private void setupActionBar(){
        Toolbar toolbar = binding.tbEditPersonalInfo;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view == binding.btnUserName){
            MyDialog editUserNameDialog = new MyDialog(this, getLayoutInflater(), R.layout.fragment_enter_info_dialog, new MyDialog.Handler() {
                @Override
                public void handle(AlertDialog dialog, View contentView) {
                    Button btnCancel = contentView.findViewById(R.id.btnCancel);
                    Button btnAgree = contentView.findViewById(R.id.btnAgree);
                    EditText edtUserName = contentView.findViewById(R.id.edtContent);

                    edtUserName.setText(binding.txtUserName.getText());

                    btnCancel.setOnClickListener(view -> dialog.dismiss());
                    btnAgree.setOnClickListener(view -> {
                        String userName = edtUserName.getText().toString();
                        if(!userName.isEmpty()){
                            binding.txtUserName.setText(userName);
                        }
                        dialog.dismiss();
                    });
                }
            });
            editUserNameDialog.show(getSupportFragmentManager(), "EDIT_NAME_DIALOG");

        }else if(view == binding.btnEmail){
            TextView txtEmail = binding.txtEmail;
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("email", txtEmail.getText());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "Đã sao chép vào bộ nhớ tạm!", Toast.LENGTH_SHORT).show();

        }else if(view == binding.btnAddress){
            MyDialog editUserNameDialog = new MyDialog(this, getLayoutInflater(), R.layout.fragment_enter_info_dialog, new MyDialog.Handler() {
                @Override
                public void handle(AlertDialog dialog, View contentView) {
                    TextView txtTitle = contentView.findViewById(R.id.txtTitle);
                    Button btnCancel = contentView.findViewById(R.id.btnCancel);
                    Button btnAgree = contentView.findViewById(R.id.btnAgree);
                    EditText edtAddress = contentView.findViewById(R.id.edtContent);

                    txtTitle.setText("Nhập địa chỉ");
                    edtAddress.setText(binding.txtAddress.getText());

                    btnCancel.setOnClickListener(view -> dialog.dismiss());
                    btnAgree.setOnClickListener(view -> {
                        String userName = edtAddress.getText().toString();
                        if(!userName.isEmpty()){
                            binding.txtAddress.setText(userName);
                        }
                        dialog.dismiss();
                    });
                }
            });
            editUserNameDialog.show(getSupportFragmentManager(), "EDIT_ADDRESS_DIALOG");

        }else if(view == binding.btnPhone){
            MyDialog editUserNameDialog = new MyDialog(this, getLayoutInflater(), R.layout.fragment_enter_info_dialog, new MyDialog.Handler() {
                @Override
                public void handle(AlertDialog dialog, View contentView) {
                    TextView txtTitle = contentView.findViewById(R.id.txtTitle);
                    Button btnCancel = contentView.findViewById(R.id.btnCancel);
                    Button btnAgree = contentView.findViewById(R.id.btnAgree);
                    EditText edtPhone = contentView.findViewById(R.id.edtContent);

                    txtTitle.setText("Nhập số điện thoại");
                    edtPhone.setText(binding.txtPhone.getText());

                    btnCancel.setOnClickListener(view -> dialog.dismiss());
                    btnAgree.setOnClickListener(view -> {
                        String userName = edtPhone.getText().toString();
                        if(!userName.isEmpty()){
                            binding.txtPhone.setText(userName);
                        }
                        dialog.dismiss();
                    });
                }
            });
            editUserNameDialog.show(getSupportFragmentManager(), "EDIT_ADDRESS_DIALOG");

        }else if(view == binding.btnSave){
            Intent intent = new Intent();
            intent.putExtra("username", binding.txtUserName.getText());
            intent.putExtra("phone", binding.txtPhone.getText());
            intent.putExtra("address", binding.txtAddress.getText());
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}