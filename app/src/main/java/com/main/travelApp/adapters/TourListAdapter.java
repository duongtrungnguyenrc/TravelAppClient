package com.main.travelApp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.main.travelApp.R;
import com.main.travelApp.models.MinimizeTour;
import com.main.travelApp.ui.activities.TourDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TourListAdapter extends RecyclerView.Adapter<TourListAdapter.ViewHolder> {
    private List<MinimizeTour> generalTours;
    private ViewPager2 viewPager2;
    private final Context context;

    public TourListAdapter(Context context) {
        this.context = context;
    }

    public ViewPager2 getViewPager2() {
        return viewPager2;
    }

    public void setViewPager2(ViewPager2 viewPager2) {
        this.viewPager2 = viewPager2;
    }

    public List<MinimizeTour> getTours() {
        return generalTours;
    }

    public void setTours(List<MinimizeTour> generalTours) {
        this.generalTours = generalTours;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_view_tour_home, parent, false)
        );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTourName.setText(generalTours.get(position).getName());
        holder.txtPrice.setText(generalTours.get(position).getStringPrice());
        holder.txtLocation.setText(generalTours.get(position).getLocation());
        holder.rbRating.setRating((float) generalTours.get(position).getRatedStar());
        Picasso.get()
                .load(generalTours.get(position).getImg())
                .placeholder(R.color.light_gray)
                .error(R.color.light_gray)
                .into(holder.imgThumbnail);


        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(TourListAdapter.this.context, TourDetailActivity.class);
            intent.putExtra("tour-id", generalTours.get(position).getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return generalTours != null ? generalTours.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtLocation, txtTourName, txtPrice;
        RatingBar rbRating;
        ImageView imgThumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtTourName = itemView.findViewById(R.id.txtTourName);

            rbRating = itemView.findViewById(R.id.rbRating);
            imgThumbnail = itemView.findViewById(R.id.imgTourBackground);
        }
    }
}
