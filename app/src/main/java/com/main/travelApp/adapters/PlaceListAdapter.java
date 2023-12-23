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
import com.main.travelApp.models.Place;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.MyViewHolder> {
    private List<Place> places;

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
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
        holder.rbPlaceRating.setRating((float) places.get(position).getRatedStar());
        holder.txtTotalBooked.setText(places.get(position).getTotalBooked() + " lượt đặt tour");
        holder.txtPlaceName.setText(places.get(position).getName());
        Picasso.get()
                .load(places.get(position).getBackground())
                .error(R.color.light_gray)
                .placeholder(R.color.light_gray)
                .into(holder.imgBackground);
    }

    @Override
    public int getItemCount() {
        return places != null ? places.size() : 0;
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
