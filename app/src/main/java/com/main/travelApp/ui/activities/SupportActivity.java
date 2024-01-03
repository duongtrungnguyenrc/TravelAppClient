package com.main.travelApp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.main.travelApp.adapters.MessageAdapter;
import com.main.travelApp.databinding.ActivitySupportBinding;
import com.main.travelApp.models.Message;
import com.main.travelApp.ui.services.Chat.ChatService;
import com.main.travelApp.utils.DebounceUtil;
import com.main.travelApp.utils.KeyBoardUtils;
import com.main.travelApp.utils.ScreenManager;
import com.main.travelApp.utils.SharedPreferenceKeys;
import com.main.travelApp.viewmodels.ProfileViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SupportActivity extends AppCompatActivity {

    private ActivitySupportBinding binding;

    private List<Message> messages = new ArrayList<>();
    private ProfileViewModel profileViewModel;
    private MessageAdapter adapter;

    private ChatService chatService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivitySupportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        chatService.disconnect();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        ScreenManager.enableFullScreen(getWindow());
        adapter = new MessageAdapter();
        profileViewModel = new ProfileViewModel(this.getSharedPreferences(SharedPreferenceKeys.USER_SHARED_PREFS, Context.MODE_PRIVATE));
        chatService = new ChatService(profileViewModel.getCurrentUser().getId(), this, new ChatService.MessageListener() {
            @Override
            public void onConnected(List<Message> messages) {;
                SupportActivity.this.messages = messages != null ? messages : new ArrayList<>();
                adapter.setMessages(SupportActivity.this.messages);

                binding.rcvMessage.setAdapter(adapter);
                binding.rcvMessage.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                scrollToBottom();
            }

            @Override
            public void onMessageReceived(Message message) {
                adapter.addMessage(message);

                binding.edtChatInput.setText("");
//                NotificationManager notificationHelper = new NotificationManager(getApplicationContext());
//                notificationHelper.createNotification("Tin nhắn mới từ " + message.getName(), message.getMessage(), SupportActivity.class);
                scrollToBottom();
            }

            @Override
            public void onChange(boolean isChanging) {

            }

            @Override
            public void onStopChange() {

            }
        });
        handleChat();
        binding.btnBack.setOnClickListener(view -> finish());
        binding.txtRoom.setText("Phòng: " + profileViewModel.getCurrentUser().getId());
        binding.rcvMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                KeyBoardUtils.hideKeyboard(SupportActivity.this);
                return true;
            }
        });
    }


    private void handleChat() {

        binding.edtChatInput.addTextChangedListener(new DebounceUtil(new DebounceUtil.OnDebouncedListener() {

            @Override
            public void onBeforeTextChanged(CharSequence text) {
                chatService.sendChange();
            }

            @Override
            public void onDebouncedTextChanged(CharSequence text) {
                chatService.sendStopChange();
            }
        }));

        binding.btnSendMessage.setOnClickListener(view -> {
            JSONObject request = new JSONObject();
            try {
                request.put("room", profileViewModel.getCurrentUser().getId());
                request.put("uid", profileViewModel.getCurrentUser().getId());
                request.put("role", "CUSTOMER");
                request.put("message", binding.edtChatInput.getText().toString());
                chatService.sendMessage(request);

                adapter.addMessage(new Message(
                                -1,
                                binding.edtChatInput.getText().toString(),
                                Long.parseLong(profileViewModel.getCurrentUser().getId()),
                                new Date().toString(),
                                Long.parseLong(profileViewModel.getCurrentUser().getId()),
                                profileViewModel.getCurrentUser().getFullName(),
                                "CUSTOMER",
                                profileViewModel.getCurrentUser().getAvatar()
                        )
                );

                smoothScrollToBottom();
                binding.edtChatInput.setText("");

            } catch (JSONException e) {
                Toast.makeText(this, "Có lỗi đã xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void scrollToBottom() {
        int lastPosition = adapter.getItemCount() - 1;
        binding.rcvMessage.scrollToPosition(lastPosition);
    }

    private void smoothScrollToBottom() {
        int lastPosition = adapter.getItemCount() - 1;
        binding.rcvMessage.smoothScrollToPosition(lastPosition);
    }
}