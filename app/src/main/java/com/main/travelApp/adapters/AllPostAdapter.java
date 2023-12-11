package com.main.travelApp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.travelApp.R;
import com.main.travelApp.databinding.ItemViewPostBinding;
import com.main.travelApp.models.Post;

import java.util.List;

public class AllPostAdapter extends RecyclerView.Adapter<AllPostAdapter.MyViewHolder> {
    private List<Post> posts;

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemViewPostBinding binding = ItemViewPostBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.txtPostTitle.setText(posts.get(position).getTitle());
        holder.binding.txtAuthor.setText(posts.get(position).getAuthor());
        holder.binding.imgPostThumbnail.setImageResource(R.drawable.pic3);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private final ItemViewPostBinding binding;

        public ItemViewPostBinding getBinding() {
            return binding;
        }

        public MyViewHolder(@NonNull View itemView, ItemViewPostBinding binding) {
            super(itemView);
            this.binding = binding;
        }
    }
}
