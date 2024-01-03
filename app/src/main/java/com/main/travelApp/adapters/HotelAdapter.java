package com.main.travelApp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.main.travelApp.R;
import com.main.travelApp.callbacks.OnRecyclerViewHasNestedItemClickListener;
import com.main.travelApp.callbacks.OnRecyclerViewItemClickListener;
import com.main.travelApp.models.Hotel;
import com.main.travelApp.models.HotelRoom;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {
    private List<Hotel> hotels;
    private Hotel selectedHotel;
    private HotelRoom selectedRoom;
    private final Context context;
    private OnRecyclerViewHasNestedItemClickListener<Hotel, HotelRoom> listener;
    private OnRecyclerViewItemClickListener<HotelRoom> nestedListener;

    public HotelAdapter(Context context) {
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

        RoomAdapter roomAdapter = new RoomAdapter(hotel.getRooms(), context);
        roomAdapter.setItemClickListener(new OnRecyclerViewItemClickListener<>() {
            @Override
            public void onClick(HotelRoom item, long position) {
                nestedListener.onClick(item, position);
                selectedRoom = item;
            }

            @Override
            public void onBlur(HotelRoom item) {
                nestedListener.onBlur(item);
                selectedRoom = null;
            }
        });
        holder.rcvRooms.setLayoutManager(new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        holder.rcvRooms.setAdapter(roomAdapter);


        if(selectedHotel != null && Objects.equals(hotel.getId(), selectedHotel.getId())) {
            holder.itemView.setBackgroundResource(R.drawable.bg_active_item);
            holder.txtHotelName.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            holder.txtHotelAddress.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            holder.rcvRooms.setVisibility(View.VISIBLE);
        }
        else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, com.balysv.materialripple.R.color.transparent));
            holder.txtHotelName.setTextColor(ContextCompat.getColor(context, R.color.black));
            holder.txtHotelAddress.setTextColor(ContextCompat.getColor(context, R.color.gray));
            holder.rcvRooms.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(view -> {
            if(selectedHotel != null && selectedRoom != null) listener.onBlur(selectedHotel, selectedRoom);
            if(selectedHotel != null && Objects.equals(selectedHotel.getId(), hotel.getId())) {
               setSelectedHotel(null);
               selectedRoom = null;
            }
            else {
                setSelectedHotel(hotel);
                selectedRoom = null;
            }
            listener.onClick(hotel, position);
        });
    }

    @Override
    public int getItemCount() {
        return hotels != null ? hotels.size() : 0;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
        notifyDataSetChanged();
    }

    public void setSelectedHotel(Hotel selectedHotel) {
        this.selectedHotel = selectedHotel;
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnRecyclerViewHasNestedItemClickListener<Hotel, HotelRoom> listener) {
        this.listener = listener;
    }

    public void setNestedItemClickListener(OnRecyclerViewItemClickListener<HotelRoom> nestedListener) {
        this.nestedListener = nestedListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgHotelThumbnail;
        final TextView txtHotelName;
        final TextView txtHotelAddress;

        final RecyclerView rcvRooms;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgHotelThumbnail = itemView.findViewById(R.id.img_hotel_thumbnail);
            this.txtHotelName = itemView.findViewById(R.id.txt_hotel_name);
            this.txtHotelAddress = itemView.findViewById(R.id.txt_hotel_address);
            this.rcvRooms = itemView.findViewById(R.id.rcv_rooms);
        }
    }
}
