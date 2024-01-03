package com.main.travelApp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.travelApp.R;
import com.main.travelApp.databinding.ItemViewMessageMeBinding;
import com.main.travelApp.databinding.ItemViewMessageOtherBinding;
import com.main.travelApp.models.Message;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_ME = 1;
    private static final int ITEM_OTHER = 2;

    List<Message> messages;

    public MessageAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_ME) {
            ItemViewMessageMeBinding binding = ItemViewMessageMeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new SelfChatItemViewHolder(binding);
        } else {
            ItemViewMessageOtherBinding binding = ItemViewMessageOtherBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new OtherChatItemViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
        if (Objects.equals(message.getRole(), "ADMIN")) {
            ((OtherChatItemViewHolder) holder).bind(message);
        } else {
            ((SelfChatItemViewHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        return Objects.equals(message.getRole(), "ADMIN") ? ITEM_OTHER : ITEM_ME;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    public void addMessage(Message message) {
        this.messages.add(message);
        notifyDataSetChanged();
    }

    static class OtherChatItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemViewMessageOtherBinding binding;

        public OtherChatItemViewHolder(ItemViewMessageOtherBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Message message) {
            binding.txtSender.setText(message.getName());
            binding.txtMessage.setText(message.getMessage());
            if(message.getAvatar() != null && !message.getAvatar().isEmpty())
                Picasso.get()
                        .load(message.getAvatar())
                        .placeholder(R.color.light_gray)
                        .error(R.color.light_gray)
                        .into(binding.imgAvatar);

        }
    }

    static class SelfChatItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemViewMessageMeBinding binding;

        public SelfChatItemViewHolder(ItemViewMessageMeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Message message) {
            binding.txtSender.setText(message.getName());
            binding.txtMessage.setText(message.getMessage());
            if(message.getAvatar() != null && !message.getAvatar().isEmpty())
                Picasso.get()
                        .load(message.getAvatar())
                        .placeholder(R.color.light_gray)
                        .error(R.color.light_gray)
                        .into(binding.imgAvatar);
        }
    }
}

