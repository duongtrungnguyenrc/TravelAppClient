package com.main.travelApp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.travelApp.R;
import com.main.travelApp.callbacks.OnRecyclerViewItemClickListener;
import com.main.travelApp.models.HotelRoom;

import java.util.List;
import java.util.Objects;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private final List<HotelRoom> rooms;
    private final Context context;

    private HotelRoom selectedRoom;

    private OnRecyclerViewItemClickListener<HotelRoom> listener;

    public RoomAdapter(List<HotelRoom> rooms, Context context) {
        this.rooms = rooms;
        this.context = context;
    }


    @NonNull
    @Override
    public RoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view_hotel_room, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RoomAdapter.ViewHolder holder, int position) {
        HotelRoom room = rooms.get(position);
        holder.txtRank.setText("Hạng vé: " + room.getType());
        holder.txtPrice.setText("Giá (1 đêm): " + room.getStringPrice());

        holder.radioSelected.setChecked(selectedRoom != null && Objects.equals(room.getId(), selectedRoom.getId()));

        holder.itemView.setOnClickListener(view -> {
            if(selectedRoom != null) listener.onBlur(selectedRoom);
            setSelectedRoom(room);
            listener.onClick(room, position);
        });
    }

    @Override
    public int getItemCount() {
        return rooms != null ? rooms.size() : 0;
    }

    public void setSelectedRoom(HotelRoom selectedRoom) {
        this.selectedRoom = selectedRoom;
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnRecyclerViewItemClickListener<HotelRoom> listener) {
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final RadioButton radioSelected;
        final TextView txtRank;
        final TextView txtPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.radioSelected = itemView.findViewById(R.id.radio_selected);
            this.txtRank = itemView.findViewById(R.id.txt_rank);
            this.txtPrice = itemView.findViewById(R.id.txt_price);
        }
    }
}
