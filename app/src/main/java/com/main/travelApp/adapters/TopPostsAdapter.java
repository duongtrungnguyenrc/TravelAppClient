package com.main.travelApp.adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.travelApp.R;
import com.main.travelApp.databinding.ItemViewTopPostBinding;
import com.main.travelApp.models.Post;

import java.util.List;

import lombok.val;

public class TopPostsAdapter extends RecyclerView.Adapter<TopPostsAdapter.MyViewHolder> {
    private List<Post> posts;

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemViewTopPostBinding binding = ItemViewTopPostBinding.inflate(inflater, parent, false);

        return new MyViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.imgPostThumbnail.setImageResource(R.drawable.pic1);
        holder.binding.txtPostTitle.setText(posts.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private final ItemViewTopPostBinding binding;
        public MyViewHolder(@NonNull View itemView, ItemViewTopPostBinding binding) {
            super(itemView);
            this.binding = binding;
        }
    }
}
