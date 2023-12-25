package com.main.travelApp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.main.travelApp.R;
import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.databinding.ActivityConfirmOrderBinding;
import com.main.travelApp.models.ContactInfo;
import com.main.travelApp.models.Tour;
import com.main.travelApp.models.TourDate;
import com.main.travelApp.repositories.impls.OrderRepositoryImpl;
import com.main.travelApp.request.CreateOrderRequest;
import com.main.travelApp.ui.components.BottomSheet;
import com.main.travelApp.utils.ScreenManager;
import com.main.travelApp.utils.SharedPreferenceKeys;
import com.main.travelApp.viewmodels.HomeViewModel;
import com.main.travelApp.viewmodels.factories.HomeViewModelFactory;
import com.squareup.picasso.Picasso;

public class ConfirmOrderActivity extends AppCompatActivity {
    ActivityConfirmOrderBinding binding;
    HomeViewModel homeViewModel;
    BottomSheet contactInfoBottomSheet;
    BottomSheet specialRequestBottomSheet;

    private CreateOrderRequest createPaymentRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ScreenManager.enableFullScreen(getWindow());
        init();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        Intent intent = getIntent();
        createPaymentRequest = new Gson().fromJson(intent.getStringExtra("payload"), CreateOrderRequest.class);
        Gson gson = new Gson();
        Tour tour = gson.fromJson(intent.getStringExtra("tour"), Tour.class);
        TourDate tourDate = gson.fromJson(intent.getStringExtra("tour-date"), TourDate.class);

        SharedPreferences sharedPreferences = this.getSharedPreferences(SharedPreferenceKeys.USER_SHARED_PREFS, Context.MODE_PRIVATE);
        homeViewModel = new ViewModelProvider(this, new HomeViewModelFactory(sharedPreferences)).get(HomeViewModel.class);
        contactInfoBottomSheet = new BottomSheet(ConfirmOrderActivity.this, getLayoutInflater(), R.layout.fragment_fill_order_contact_info, "Thông tin liên hệ");
        specialRequestBottomSheet = new BottomSheet(ConfirmOrderActivity.this, getLayoutInflater(), R.layout.fragment_special_request, "Yêu cầu đặc biệt");

        setCurrentUserContactInfo();
        renderSpecialRequest();

        contactInfoBottomSheet.setup((dialogWindow, contentView) -> {
            EditText edtFullName = contentView.findViewById(R.id.edt_full_name);
            EditText edtEmail = contentView.findViewById(R.id.edt_email);
            EditText edtPhone = contentView.findViewById(R.id.edt_phone);
            EditText edtAddress = contentView.findViewById(R.id.edt_address);

            contentView.findViewById(R.id.btn_cancel).setOnClickListener(view -> {
                setCurrentUserContactInfo();
                binding.radioGroupUserInfo.check(R.id.item_logged_in_info);
                contactInfoBottomSheet.dismiss();
            });

            contentView.findViewById(R.id.btn_confirm).setOnClickListener(view -> {
                ContactInfo contactInfo = new ContactInfo();
                contactInfo.setCustomerFullName(edtFullName.getText().toString());
                contactInfo.setCustomerEmail(edtEmail.getText().toString());
                contactInfo.setCustomerPhone(edtPhone.getText().toString());
                contactInfo.setCustomerAddress(edtAddress.getText().toString());
                createPaymentRequest.setContactInfo(contactInfo);
                renderContactInfo();
                contactInfoBottomSheet.dismiss();
            });
        });

        specialRequestBottomSheet.setup((dialogWindow, contentView) -> {
            EditText edtSpecialRequest = contentView.findViewById(R.id.edt_special_request);

            contentView.findViewById(R.id.btn_cancel).setOnClickListener(view -> {
                createPaymentRequest.setSpecialRequest("");
                renderSpecialRequest();
                specialRequestBottomSheet.dismiss();
            });

            contentView.findViewById(R.id.btn_confirm).setOnClickListener(view -> {
                createPaymentRequest.setSpecialRequest(edtSpecialRequest.getText().toString());
                renderSpecialRequest();
                specialRequestBottomSheet.dismiss();
            });
        });

        Picasso.get()
                .load(tour.getImg())
                .placeholder(R.color.light_gray)
                .error(R.color.light_gray)
                .into(binding.imgThumbnail);
        Picasso.get()
                .load(homeViewModel.getCurrentUser().getAvatar())
                .placeholder(R.color.light_gray)
                .error(R.color.light_gray)
                .into(binding.imgAvatar);

        binding.txtCurrentUserName.setText(homeViewModel.getCurrentUser().getFullName());
        binding.txtCurrentUserEmail.setText(homeViewModel.getCurrentUser().getEmail() + " + " + homeViewModel.getCurrentUser().getPhone());
        binding.txtTicketRank.setText("Hạng vé: " + tourDate.getType());
        binding.txtAdults.setText("Người lớn: " + createPaymentRequest.getAdults());
        binding.txtChildren.setText("Trẻ em: " + createPaymentRequest.getChildren());
        binding.txtPrice.setText(createPaymentRequest.getStringTotalPrice());
        binding.txtTourName.setText(tour.getName());
        binding.txtStartDate.setText("Khởi hành: " + tourDate.getDepartDate());
        binding.txtEndDate.setText("Kết thúc: " + tourDate.getEndDate());
        binding.txtTotalPrice.setText(createPaymentRequest.getStringTotalPrice());

        binding.radioGroupUserInfo.setOnCheckedChangeListener((radioGroup, i) -> {
            if(i == R.id.item_custom_user_info) {
                contactInfoBottomSheet.show();
            } else if (i == R.id.item_logged_in_info) {
                setCurrentUserContactInfo();
            }
        });

        binding.layoutSpecialRequest.setOnClickListener(view -> {
            specialRequestBottomSheet.show();
        });

        binding.radioGroupPaymentMethod.setOnCheckedChangeListener((radioGroup, i) -> {
            if(i == R.id.item_cash) {
                createPaymentRequest.setPaymentMethod("cash");
            } else if (i == R.id.item_vn_pay) {
                createPaymentRequest.setPaymentMethod("vnpay");
            } else if (i == R.id.item_paypal) {
                createPaymentRequest.setPaymentMethod("paypal");
            }
        });
        binding.btnContinue.setOnClickListener(view -> {
            OrderRepositoryImpl.getInstance().CreateOrder(createPaymentRequest, new ActionCallback<String>() {
                @Override
                public void onSuccess(String url) {
                    Intent paymentIntent = new Intent(Intent.ACTION_VIEW);
                    paymentIntent.setData(Uri.parse(url));
                    startActivity(paymentIntent);
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(ConfirmOrderActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    public void  renderSpecialRequest() {
        binding.txtSpecialRequest.setText(createPaymentRequest.getSpecialRequest());
        if(createPaymentRequest.getSpecialRequest() != null && !createPaymentRequest.getSpecialRequest().isEmpty()) {
            binding.icSpecialRequestStatus.setImageResource(R.drawable.ic_success);
            binding.icSpecialRequestStatus.setImageTintList(ContextCompat.getColorStateList(this, R.color.green));
            binding.txtSpecialRequest.setVisibility(View.VISIBLE);
        } else {
            binding.icSpecialRequestStatus.setImageResource(R.drawable.ic_next_arrow_24);
            binding.icSpecialRequestStatus.setImageTintList(ContextCompat.getColorStateList(this, R.color.colorPrimary));
            binding.txtSpecialRequest.setVisibility(View.GONE);
        }
    }

    @SuppressLint("SetTextI18n")
    public void renderContactInfo() {
        binding.txtName.setText(createPaymentRequest.getContactInfo().getCustomerFullName());
        binding.txtContactInfo.setText(createPaymentRequest.getContactInfo().getCustomerEmail() + " + " + createPaymentRequest.getContactInfo().getCustomerPhone());
    }

    private void setCurrentUserContactInfo() {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setCustomerFullName(homeViewModel.getCurrentUser().getFullName());
        contactInfo.setCustomerEmail(homeViewModel.getCurrentUser().getEmail());
        contactInfo.setCustomerPhone(homeViewModel.getCurrentUser().getPhone());
        contactInfo.setCustomerAddress(homeViewModel.getCurrentUser().getAddress());
        createPaymentRequest.setContactInfo(contactInfo);
        renderContactInfo();
    }
}