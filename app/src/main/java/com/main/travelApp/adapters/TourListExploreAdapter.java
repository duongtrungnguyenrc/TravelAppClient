package com.main.travelApp.adapters;

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
import com.main.travelApp.models.GeneralTour;

import java.util.List;
import java.util.Random;

public class TourListExploreAdapter extends RecyclerView.Adapter<TourListExploreAdapter.MyViewHolder> {
    private List<GeneralTour> generalTours;
    private ViewPager2 viewPager2;

    public ViewPager2 getViewPager2() {
        return viewPager2;
    }

    public void setViewPager2(ViewPager2 viewPager2) {
        this.viewPager2 = viewPager2;
    }

    public List<GeneralTour> getTours() {
        return generalTours;
    }

    public void setTours(List<GeneralTour> generalTours) {
        this.generalTours = generalTours;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_view_tour_explore, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtTourName.setText(generalTours.get(position).getName());
        holder.txtLocation.setText(generalTours.get(position).getLocation());
        holder.txtPrice.setText(String.valueOf(generalTours.get(position).getPrice()));
        holder.rbRating.setRating((float) generalTours.get(position).getRatedStar());
        int[] listImage = new int[] {
                R.drawable.intro,
                R.drawable.pic1,
                R.drawable.pic2,
                R.drawable.pic3
        };
        Random random = new Random();
        holder.imgThumbnail.setImageResource(listImage[random.nextInt(4)]);
    }

    @Override
    public int getItemCount() {
        return generalTours != null ? generalTours.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtTourName, txtPrice, txtLocation;
        RatingBar rbRating;
        ImageView imgThumbnail;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtTourName = itemView.findViewById(R.id.txtTourName);

            rbRating = itemView.findViewById(R.id.rbRating);

            imgThumbnail = itemView.findViewById(R.id.imgTourBackground);
        }
    }
}
