package com.main.travelApp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.main.travelApp.R;
import com.main.travelApp.models.GeneralPost;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewestPostsAdapter extends RecyclerView.Adapter<NewestPostsAdapter.MyViewHolder> {
    private List<GeneralPost> posts;
    private ViewPager2 viewPager2;
    private Context context;
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            posts.addAll(posts);
            notifyDataSetChanged();
        }
    };

    public Context getContext() {
        return context;
    }
    public void setViewPager2(ViewPager2 viewPager2){
        this.viewPager2 = viewPager2;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<GeneralPost> getPosts() {
        return posts;
    }

    public void setPosts(List<GeneralPost> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_view_homepost, parent, false)
        );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtTitle.setText(posts.get(position).getTitle());
        holder.txtAuthor.setText("Tác giả: " + posts.get(position).getAuthor());
        holder.txtPostTime.setText(posts.get(position).getTime());
        holder.txtContentDemo.setText(posts.get(position).getDescription());
        Picasso.get().load(posts.get(position).getImg())
                        .placeholder(R.color.light_gray)
                        .error(R.color.light_gray)
                        .into(holder.imgBackground);

        if(viewPager2 != null && position == posts.size() - 2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return posts != null ? posts.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imgBackground;
        TextView txtTitle, txtAuthor, txtPostTime, txtContentDemo;
        Button btnDetails;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBackground = itemView.findViewById(R.id.imgPostThumbnail);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtPostTime = itemView.findViewById(R.id.txtPostTime);
            txtContentDemo = itemView.findViewById(R.id.txtDemoContent);
            btnDetails = itemView.findViewById(R.id.btnDetails);
        }
    }
}
