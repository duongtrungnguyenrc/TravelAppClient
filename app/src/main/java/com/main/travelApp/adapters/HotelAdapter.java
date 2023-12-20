package com.main.travelApp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.travelApp.R;
import com.main.travelApp.models.Hotel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {
    private final List<Hotel> hotels;
    private final Context context;

    public HotelAdapter(List<Hotel> hotels, Context context) {
        this.hotels = hotels;
        this.context = context;
    }

    @NonNull
    @Override
    public HotelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view_hotel, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HotelAdapter.ViewHolder holder, int position) {
        Hotel hotel = hotels.get(position);
        holder.txtHotelName.setText(hotel.getName());
        holder.txtHotelAddress.setText(hotel.getAddress());
        Picasso.get()
                .load(hotel.getIllustration())
                .placeholder(R.color.light_gray)
                .error(R.color.light_gray)
                .into(holder.imgHotelThumbnail);
    }

    @Override
    public int getItemCount() {
        return hotels != null ? hotels.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgHotelThumbnail;
        final TextView txtHotelName;
        final TextView txtHotelAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgHotelThumbnail = itemView.findViewById(R.id.img_hotel_thumbnail);
            this.txtHotelName = itemView.findViewById(R.id.txt_hotel_name);
            this.txtHotelAddress = itemView.findViewById(R.id.txt_hotel_address);
        }
    }
}
