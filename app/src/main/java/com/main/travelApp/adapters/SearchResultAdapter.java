package com.main.travelApp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.travelApp.R;
import com.main.travelApp.models.MinimizePost;
import com.main.travelApp.models.MinimizeTour;
import com.main.travelApp.ui.activities.PostDetailActivity;
import com.main.travelApp.ui.activities.TourDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {
    private List<MinimizeTour> tours = null;
    private List<MinimizePost> posts = null;

    private Context context;

    public SearchResultAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public SearchResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view_search_result, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SearchResultAdapter.ViewHolder holder, int position) {
        if(tours != null) {
            MinimizeTour tour = tours.get(position);
            holder.txtName.setText(tour.getName());
            holder.txtDescription.setText("Tour " + tour.getLocation());
            Picasso.get()
                    .load(tour.getImg())
                    .placeholder(R.color.light_gray)
                    .error(R.color.light_gray)
                    .into(holder.imgThumbnail);

            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, TourDetailActivity.class);
                intent.putExtra("tour-id", tour.getId());
                context.startActivity(intent);
            });
        } else if (posts != null) {
            MinimizePost post = posts.get(position);
            holder.txtName.setText(post.getTitle());
            holder.txtDescription.setText(post.getType() + " - " + post.getAuthor());
            Picasso.get()
                    .load(post.getImg())
                    .placeholder(R.color.light_gray)
                    .error(R.color.light_gray)
                    .into(holder.imgThumbnail);

            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, PostDetailActivity.class);
                intent.putExtra("postId", post.getId());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        if(tours != null) {
            return tours.size();
        } else if (posts != null) {
            return posts.size();
        }
        return 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTours(List<MinimizeTour> tours) {
        this.tours = tours;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPosts(List<MinimizePost> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgThumbnail;
        final TextView txtName;
        final TextView txtDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgThumbnail = itemView.findViewById(R.id.img_thumbnail);
            this.txtName = itemView.findViewById(R.id.txt_name);
            this.txtDescription = itemView.findViewById(R.id.txt_description);
        }
    }
}
