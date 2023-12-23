package com.main.travelApp.ui.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.main.travelApp.R;
import com.main.travelApp.databinding.FragmentProfileBinding;
import com.main.travelApp.models.LoginHistory;
import com.main.travelApp.services.auth.AuthManager;
import com.main.travelApp.ui.activities.EditPersonalInfoActivity;
import com.main.travelApp.ui.activities.LoginActivity;
import com.main.travelApp.ui.activities.LoginHistoryActivity;
import com.main.travelApp.ui.activities.TermAndServiceActivity;
import com.main.travelApp.ui.activities.UserOrdersActivity;
import com.main.travelApp.ui.components.BottomSheet;
import com.main.travelApp.ui.components.MyDialog;
import com.main.travelApp.utils.SharedPreferenceKeys;
import com.main.travelApp.viewmodels.ProfileViewModel;
import com.main.travelApp.viewmodels.UserOrderViewModel;
import com.main.travelApp.viewmodels.factories.ProfileViewModelFactory;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private FragmentProfileBinding profileBinding;
    private ProfileViewModel viewModel;
    private SharedPreferences sharedPreferences;
    private BottomSheet userInfoBottomSheet;
    private static final int UPDATE_INFO_RC = 12001;
    private static final int IMAGE_RC = 101;
    private static final int REQUEST_EXTERNAL_STORAGE = 192374;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        profileBinding = FragmentProfileBinding.inflate(inflater, container, false);
        sharedPreferences = requireActivity().getSharedPreferences(SharedPreferenceKeys.USER_SHARED_PREFS, Context.MODE_PRIVATE);
        ProfileViewModelFactory factory = new ProfileViewModelFactory(sharedPreferences);
        viewModel = new ViewModelProvider(requireActivity(), factory).get(ProfileViewModel.class);
        viewModel.setContext(getContext());
        init();
        return profileBinding.getRoot();
    }

    private void init(){
        userInfoBottomSheet = new BottomSheet(getContext(), getLayoutInflater(), R.layout.frame_personal_info, "Hồ sơ cá nhân");

        viewModel.getIsUserUpdated().observe(getViewLifecycleOwner(), isUserUpdated -> {
            if(isUserUpdated){
                loadUser();
            }
        });
        viewModel.getIsUserAvatarUpdated().observe(getViewLifecycleOwner(), isUserAvatarUpdated -> {
            if(isUserAvatarUpdated){
                loadUser();
            }
        });
        loadUser();
        setEvents();
    }

    private void setEvents(){
        profileBinding.btnSignOut.setOnClickListener(this);
        profileBinding.btnPersonalInfo.setOnClickListener(this);
        profileBinding.btnLoginHistory.setOnClickListener(this);
        profileBinding.btnOrderHistory.setOnClickListener(this);
        profileBinding.btnPasswordAndSecure.setOnClickListener(this);
        profileBinding.btnMainPersonalInfo.setOnClickListener(this);
        profileBinding.btnPushNotification.setOnClickListener(this);
        profileBinding.btnTermsAndServices.setOnClickListener(this);
        profileBinding.userAvatar.setOnClickListener(this);
    }

    private void loadUser(){
        profileBinding.txtUserName.setText(viewModel.getCurrentUser().getFullName());
        if(!viewModel.getCurrentUser().getAvatar().isEmpty() && viewModel.getCurrentUser().getAvatar() != null)
            Picasso.get()
                    .load(viewModel.getCurrentUser().getAvatar())
                    .placeholder(R.color.light_gray)
                    .error(R.color.light_gray)
                    .into(profileBinding.userAvatar);
    }

    private void showUserInfoBottomSheet(){
        userInfoBottomSheet.build((dialog, contentView) -> {
            TextView txtFullName = contentView.findViewById(R.id.txtUserName);
            TextView txtEmail = contentView.findViewById(R.id.txtEmail);
            TextView txtAddress = contentView.findViewById(R.id.txtAddress);
            TextView txtPhone = contentView.findViewById(R.id.txtPhone);

            txtFullName.setText(viewModel.getCurrentUser().getFullName());
            txtEmail.setText(viewModel.getCurrentUser().getEmail());
            txtAddress.setText(viewModel.getCurrentUser().getAddress());
            txtPhone.setText(viewModel.getCurrentUser().getPhone());

            contentView.findViewById(R.id.btnEmail).setOnClickListener(view -> {
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("email", txtEmail.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Đã sao chép vào bộ nhớ tạm!", Toast.LENGTH_SHORT).show();
            });
        });
        userInfoBottomSheet.show();
    }
    @Override
    public void onClick(View view) {
        if(view == profileBinding.btnSignOut){
            MyDialog alertDialog = new MyDialog(getContext(), getLayoutInflater(), R.layout.fragment_confirm_dialog, new MyDialog.Handler() {
                @Override
                public void handle(AlertDialog dialog, View contentView) {
                    ((TextView) contentView.findViewById(R.id.txtMessage)).setText("Bạn có chắc là muốn đăng xuất?");
                    ((Button) contentView.findViewById(R.id.btnCancel)).setText("Quay lại");
                    ((Button) contentView.findViewById(R.id.btnChange)).setText("Đăng xuất");

                    contentView.findViewById(R.id.btnCancel).setOnClickListener(view -> {
                        dialog.dismiss();
                    });
                    contentView.findViewById(R.id.btnChange).setOnClickListener(view -> {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear().apply();
                        Intent intent = new Intent(requireActivity(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    });
                }
            });
            alertDialog.show(getParentFragmentManager(), "SIGN_OUT_ALERT_DIALOG");
        }else if(view == profileBinding.btnPersonalInfo){
            showUserInfoBottomSheet();
        }else if(view == profileBinding.btnMainPersonalInfo){
            Intent intent = new Intent(getContext(), EditPersonalInfoActivity.class);
            intent.putExtra("username", viewModel.getCurrentUser().getFullName());
            intent.putExtra("email", viewModel.getCurrentUser().getEmail());
            intent.putExtra("address", viewModel.getCurrentUser().getAddress());
            intent.putExtra("phone", viewModel.getCurrentUser().getPhone());
            startActivityForResult(intent, UPDATE_INFO_RC);
        }else if(view == profileBinding.btnLoginHistory){
            Intent intent = new Intent(getContext(), LoginHistoryActivity.class);
            startActivity(intent);
        }else if(view == profileBinding.btnOrderHistory){
            Intent intent = new Intent(getContext(), UserOrdersActivity.class);
            startActivity(intent);
        }else if(view == profileBinding.btnPasswordAndSecure){
            MyDialog myDialog = new MyDialog(getContext(), getLayoutInflater(), R.layout.fragment_change_password, new MyDialog.Handler() {
                @Override
                public void handle(AlertDialog dialog, View contentView) {
                    EditText edtOldPassword = contentView.findViewById(R.id.edtOldPassword);
                    EditText edtNewPassword = contentView.findViewById(R.id.edtNewPass);
                    EditText edtReNewPassword = contentView.findViewById(R.id.edtReNewPass);
                    Button btnCancel = contentView.findViewById(R.id.btnCancel);
                    Button btnChange = contentView.findViewById(R.id.btnChange);
                    btnCancel.setOnClickListener(view -> {
                        dialog.dismiss();
                    });
                    btnChange.setOnClickListener(view -> {
                        String oldPasswordStr = edtOldPassword.getText().toString();
                        String newPasswordStr = edtNewPassword.getText().toString();
                        String reNewPasswordStr = edtReNewPassword.getText().toString();
                        
                        if(newPasswordStr.equals(reNewPasswordStr)){
                            viewModel.changePassword(oldPasswordStr, newPasswordStr, dialog);
                        }else
                            Toast.makeText(getContext(), "Xác nhận mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    });
                }
            });
            myDialog.show(getParentFragmentManager(), "CHANGE_PASS_DIALOG");
        }else if(view == profileBinding.btnTermsAndServices){
            Intent intent = new Intent(getContext(), TermAndServiceActivity.class);
            startActivity(intent);
        }else if(view == profileBinding.userAvatar){
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                    ActivityCompat.requestPermissions(requireActivity(), new String[] {
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_EXTERNAL_STORAGE);
                }
                else{
                    ActivityCompat.requestPermissions(requireActivity(), new String[] {
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_EXTERNAL_STORAGE);
                }
            }else{
                getImageFromStorage();
            }
        }
    }

    private void getImageFromStorage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_RC);
    }

    private String getPathFromURI(Uri contentUri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = requireActivity().getContentResolver().query(contentUri, projection, null, null, null);

        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        }

        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_EXTERNAL_STORAGE -> {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getImageFromStorage();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case UPDATE_INFO_RC -> {
                if(resultCode == Activity.RESULT_OK){
                    viewModel.getCurrentUser().setPhone(data.getStringExtra("phone"));
                    viewModel.getCurrentUser().setFullName(data.getStringExtra("username"));
                    viewModel.getCurrentUser().setAddress(data.getStringExtra("address"));
                    viewModel.updateUser();
                }
            }
            case IMAGE_RC -> {
                if(resultCode == Activity.RESULT_OK){
                    Uri selectedImage = data.getData();
                    String imagePath = getPathFromURI(selectedImage);
                    viewModel.updateUserAvatar(imagePath);
                }
            }
        }
    }
}