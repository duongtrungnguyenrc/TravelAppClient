package com.main.travelApp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.travelApp.R;
import com.main.travelApp.databinding.ItemViewTopPostBinding;
import com.main.travelApp.models.MinimizePost;
import com.main.travelApp.ui.activities.PostDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopPostsAdapter extends RecyclerView.Adapter<TopPostsAdapter.MyViewHolder> {
    private List<MinimizePost> posts;
    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setPosts(List<MinimizePost> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    public List<MinimizePost> getPosts() {
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get()
                .load(posts.get(position).getImg())
                .placeholder(R.color.light_gray)
                .error(R.color.light_gray)
                .into(holder.binding.imgPostThumbnail);
        holder.binding.txtPostTitle.setText(posts.get(position).getTitle());
        holder.binding.txtViews.setText(String.valueOf(posts.get(position).getViews()));
        holder.binding.txtPostType.setText(posts.get(position).getType());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostDetailActivity.class);
                intent.putExtra("postId", posts.get(position).getId());
                Log.d("PostID", "onClick: " + posts.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts != null ? posts.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private final ItemViewTopPostBinding binding;
        public MyViewHolder(@NonNull View itemView, ItemViewTopPostBinding binding) {
            super(itemView);
            this.binding = binding;
        }
    }
}
