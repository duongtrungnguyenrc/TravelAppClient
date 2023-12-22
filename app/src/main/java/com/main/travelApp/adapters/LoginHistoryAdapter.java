package com.main.travelApp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.travelApp.R;
import com.main.travelApp.databinding.ItemViewLoginHistoryBinding;
import com.main.travelApp.models.LoginHistory;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LoginHistoryAdapter extends RecyclerView.Adapter<LoginHistoryAdapter.MyViewHolder> {
    private List<LoginHistory> loginHistories;
    private Context context;

    public List<LoginHistory> getLoginHistories() {
        return loginHistories;
    }

    public void setLoginHistories(List<LoginHistory> loginHistories) {
        this.loginHistories = loginHistories;
        notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewLoginHistoryBinding binding = ItemViewLoginHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        MyViewHolder viewHolder = new MyViewHolder(binding.getRoot(), binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(loginHistories.get(position).getAvatar() != null && !loginHistories.get(position).getAvatar().isEmpty()){
            Picasso.get()
                    .load(loginHistories.get(position).getAvatar())
                    .error(R.color.light_gray)
                    .placeholder(R.color.light_gray)
                    .into(holder.binding.imgAvatar);
        }
        String[] date = loginHistories.get(position).getLoggedDate().split(" ");
        holder.binding.txtLoggedTime.setText("Đã đăng nhập lúc " + date[0] + ", ngày " + date[1]);
        holder.binding.txtDevice.setText("Thiết bị: " + loginHistories.get(position).getUserDevice());
        holder.binding.txtIpAddress.setText("Địa chỉ IP: " + loginHistories.get(position).getIpAddress());
    }

    @Override
    public int getItemCount() {
        return loginHistories != null ? loginHistories.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ItemViewLoginHistoryBinding binding;
        public MyViewHolder(@NonNull View itemView, ItemViewLoginHistoryBinding binding) {
            super(itemView);
            this.binding = binding;
        }
    }
}
