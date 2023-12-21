package com.main.travelApp.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.main.travelApp.R;
import com.main.travelApp.databinding.FragmentProfileBinding;
import com.main.travelApp.services.auth.AuthManager;
import com.main.travelApp.ui.activities.LoginActivity;
import com.main.travelApp.ui.components.BottomSheet;
import com.main.travelApp.ui.components.MyDialog;
import com.main.travelApp.utils.SharedPreferenceKeys;
import com.main.travelApp.viewmodels.ProfileViewModel;
import com.main.travelApp.viewmodels.factories.ProfileViewModelFactory;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private FragmentProfileBinding profileBinding;
    private ProfileViewModel viewModel;
    private SharedPreferences sharedPreferences;
    private BottomSheet userInfoBottomSheet;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        profileBinding = FragmentProfileBinding.inflate(inflater, container, false);
        sharedPreferences = requireActivity().getSharedPreferences(SharedPreferenceKeys.USER_SHARED_PREFS, Context.MODE_PRIVATE);
        ProfileViewModelFactory factory = new ProfileViewModelFactory(sharedPreferences);
        viewModel = new ViewModelProvider(requireActivity(), factory).get(ProfileViewModel.class);
        init();
        return profileBinding.getRoot();
    }

    private void init(){
        userInfoBottomSheet = new BottomSheet(getContext(), getLayoutInflater(), R.layout.fragment_profile, "Hồ sơ cá nhân");

        profileBinding.txtUserName.setText(viewModel.getCurrentUser().getFullName());
        if(!viewModel.getCurrentUser().getAvatar().isEmpty() && viewModel.getCurrentUser().getAvatar() != null)
            Picasso.get()
                .load(viewModel.getCurrentUser().getAvatar())
                .placeholder(R.color.light_gray)
                .error(R.color.light_gray)
                .into(profileBinding.userAvatar);
        profileBinding.btnSignOut.setOnClickListener(this);
        profileBinding.btnPersonalInfo.setOnClickListener(this);
        profileBinding.btnLoginHistory.setOnClickListener(this);
        profileBinding.btnOrderHistory.setOnClickListener(this);
        profileBinding.btnPasswordAndSecure.setOnClickListener(this);
        profileBinding.btnPersonalInfo1.setOnClickListener(this);
        profileBinding.btnPushNotification.setOnClickListener(this);
        profileBinding.btnTermsAndServices.setOnClickListener(this);
    }

    private void showUserInfoBottomSheet(){
        userInfoBottomSheet.show((dialog, contentView) -> {
            contentView.findViewById(R.id.btnPersonalInfo).setOnClickListener(view -> {
                MyDialog myDialog = new MyDialog(getContext(), getLayoutInflater(), R.layout.fragment_enter_code_dialog, (d, v) -> {
                    EditText edt = v.findViewById(R.id.edtConfirmCode);
                    Button btn = v.findViewById(R.id.btnSend);
                    btn.setOnClickListener(mView -> {

                        });
                    });
                myDialog.show(getParentFragmentManager(), "DIALOG");
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
        }
    }
}