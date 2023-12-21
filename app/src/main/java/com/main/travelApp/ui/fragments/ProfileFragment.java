package com.main.travelApp.ui.fragments;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
import com.main.travelApp.ui.activities.UserOrdersActivity;
import com.main.travelApp.ui.components.BottomSheet;
import com.main.travelApp.ui.components.MyDialog;
import com.main.travelApp.utils.SharedPreferenceKeys;
import com.main.travelApp.viewmodels.ProfileViewModel;
import com.main.travelApp.viewmodels.UserOrderViewModel;
import com.main.travelApp.viewmodels.factories.ProfileViewModelFactory;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private FragmentProfileBinding profileBinding;
    private ProfileViewModel viewModel;
    private SharedPreferences sharedPreferences;
    private BottomSheet userInfoBottomSheet;
    private static final int UPDATE_INTO_RC = 12001;
    private ActivityResultLauncher<Intent> getNewUserInfo =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent intent = result.getData();
                    viewModel.getCurrentUser().setPhone(intent.getStringExtra("phone"));
                    viewModel.getCurrentUser().setFullName(intent.getStringExtra("username"));
                    viewModel.getCurrentUser().setAddress(intent.getStringExtra("address"));
                    viewModel.updateUser();
                }
            });
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
        loadUser();
        setEvents();
    }

    private void setEvents(){
        profileBinding.btnSignOut.setOnClickListener(this);
        profileBinding.btnPersonalInfo.setOnClickListener(this);
        profileBinding.btnLoginHistory.setOnClickListener(this);
        profileBinding.btnOrderHistory.setOnClickListener(this);
        profileBinding.btnPasswordAndSecure.setOnClickListener(this);
        profileBinding.btnPersonalInfo1.setOnClickListener(this);
        profileBinding.btnPushNotification.setOnClickListener(this);
        profileBinding.btnTermsAndServices.setOnClickListener(this);
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
        userInfoBottomSheet.show((dialog, contentView) -> {
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
    }
    @Override
    public void onClick(View view) {
        if(view == profileBinding.btnSignOut){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear().apply();
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else if(view == profileBinding.btnPersonalInfo){
            showUserInfoBottomSheet();
        }else if(view == profileBinding.btnPersonalInfo1){
            Intent intent = new Intent(getContext(), EditPersonalInfoActivity.class);
            intent.putExtra("username", viewModel.getCurrentUser().getFullName());
            intent.putExtra("email", viewModel.getCurrentUser().getEmail());
            intent.putExtra("address", viewModel.getCurrentUser().getAddress());
            intent.putExtra("phone", viewModel.getCurrentUser().getPhone());
            startActivityForResult(intent, UPDATE_INTO_RC);
        }else if(view == profileBinding.btnLoginHistory){
            Intent intent = new Intent(getContext(), LoginHistoryActivity.class);
            startActivity(intent);
        }else if(view == profileBinding.btnOrderHistory){
            Intent intent = new Intent(getContext(), UserOrdersActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case UPDATE_INTO_RC -> {
                if(resultCode == Activity.RESULT_OK){
                    viewModel.getCurrentUser().setPhone(data.getStringExtra("phone"));
                    viewModel.getCurrentUser().setFullName(data.getStringExtra("username"));
                    viewModel.getCurrentUser().setAddress(data.getStringExtra("address"));
                    viewModel.updateUser();
                }
            }
        }
    }
}