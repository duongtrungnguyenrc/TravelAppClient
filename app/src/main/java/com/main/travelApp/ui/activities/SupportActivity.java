package com.main.travelApp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.ScrollView;

import com.main.travelApp.adapters.MessageAdapter;
import com.main.travelApp.databinding.ActivitySupportBinding;
import com.main.travelApp.models.Message;
import com.main.travelApp.utils.ScreenManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SupportActivity extends AppCompatActivity {

    private ActivitySupportBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivitySupportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        ScreenManager.enableFullScreen(getWindow());
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(1, "hello", (long) 1, new Date(), (long) 1, "Nguyen dep trai", "ADMIN", ""));
        messages.add(new Message(1, "hello", (long) 1, new Date(), (long) 1, "Nguyen dep trai", "CUSTOMER", ""));
        messages.add(new Message(1, "hello", (long) 1, new Date(), (long) 1, "Nguyen dep trai", "ADMIN", ""));
        messages.add(new Message(1, "Để sửa lỗi này, hãy xác định chính xác kiểu ViewHolder bạn đang sử dụng trong MessageAdapter và đảm bảo rằng bạn thực sự cast chúng đúng cách trong phương thức onBindViewHolder", (long) 1, new Date(), (long) 1, "Nguyen dep trai", "CUSTOMER", ""));
        messages.add(new Message(1, "Lý do chính có thể là việc truy cập không chính xác vào ViewHolder tương ứng trong phương thức onBindViewHolder hoặc có thể có sự nhầm lẫn về kiểu dữ liệu mà ViewHolder đang sử dụng.", (long) 1, new Date(), (long) 1, "Nguyen dep trai", "ADMIN", ""));
        messages.add(new Message(1, "hello", (long) 1, new Date(), (long) 1, "Nguyen dep trai", "CUSTOMER", ""));
        messages.add(new Message(1, "hello", (long) 1, new Date(), (long) 1, "Nguyen dep trai", "ADMIN", ""));
        messages.add(new Message(1, "hello", (long) 1, new Date(), (long) 1, "Nguyen dep trai", "CUSTOMER", ""));

        MessageAdapter adapter = new MessageAdapter(messages);
        binding.rcvMessage.setAdapter(adapter);
        binding.rcvMessage.setLayoutManager(new LinearLayoutManager(this));
        binding.btnBack.setOnClickListener(view -> finish());
    }
}