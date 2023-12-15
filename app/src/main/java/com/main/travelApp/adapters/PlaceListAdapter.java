package com.main.travelApp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.travelApp.R;
import com.main.travelApp.models.GeneralTour;

import java.util.List;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.MyViewHolder> {
    private List<GeneralTour> generalTours;

    public List<GeneralTour> getTours() {
        return generalTours;
    }

    public void setTours(List<GeneralTour> generalTours) {
        this.generalTours = generalTours;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_place, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.rbPlaceRating.setRating((float) generalTours.get(position).getRatedStar());
        holder.txtTotalBooked.setText(generalTours.get(position).getMaxPeople() + " lượt đặt tour");
        holder.txtPlaceName.setText(generalTours.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return generalTours != null ? generalTours.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtPlaceName, txtTotalBooked;
        RatingBar rbPlaceRating;
        ImageView imgBackground;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPlaceName = itemView.findViewById(R.id.txtPlaceName);
            txtTotalBooked = itemView.findViewById(R.id.txtBookedTotal);
            rbPlaceRating = itemView.findViewById(R.id.rbPlaceRating);
            imgBackground = itemView.findViewById(R.id.imgTourBackground);
        }
    }
}
