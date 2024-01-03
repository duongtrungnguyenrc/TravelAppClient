package com.main.travelApp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.travelApp.R;
import com.main.travelApp.models.Rate;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder> {
    public void setRates(List<Rate> rates) {
        this.rates = rates;
        notifyDataSetChanged();
    }

    private List<Rate> rates;
    private final Context context;
    private boolean isAllScreen = false;

    public void setAllScreen(boolean allScreen) {
        isAllScreen = allScreen;
    }

    public RatingAdapter(List<Rate> rates, Context context) {
        this.rates = rates;
        this.context = context;
    }

    @NonNull
    @Override
    public RatingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view_rating, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RatingAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Rate rate = rates.get(position);
        Log.d("avatar", "==================== " + rate.getUsername() + " ======================");
        Log.d("avatar", "onBindViewHolder: " + rate.isActive());
        Log.d("avatar", "==================== " + rate.getUsername() + " ======================");
        String userName = rate.getUsername();
        if(rate.isActive()){
            userName += " (Bạn)";
            holder.itemView.setEnabled(true);
        }else{
            holder.itemView.setEnabled(false);
        }

        if(!isAllScreen){
            holder.itemView.setEnabled(false);
        }
        holder.txtUserName.setText(userName);
        holder.txtRatedStar.setText(String.valueOf(rate.getStar()));
        holder.txtComment.setText(rate.getContent());
        if(rate.getAvatar() != null && !rate.getAvatar().isEmpty()) {
            Picasso.get()
                    .load(rate.getAvatar())
                    .placeholder(R.color.light_gray)
                    .error(R.drawable.avatar_profile)
                    .into(holder.imgAvatar);
        }else{
            holder.imgAvatar.setImageResource(R.drawable.avatar_profile);
        }
        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(Integer.parseInt(rate.getId()), 0, 0, "Sửa");
                contextMenu.add(Integer.parseInt(rate.getId()),  1, 1, "Xóa");
            }
        });
    }

    @Override
    public int getItemCount() {
        return rates != null ? rates.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtUserName;
        private final TextView txtRatedStar;
        private final TextView txtComment;
        final ImageView imgAvatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtUserName = itemView.findViewById(R.id.txt_user_name);
            this.txtRatedStar = itemView.findViewById(R.id.txt_rated_star);
            this.txtComment = itemView.findViewById(R.id.txt_comment);
            this.imgAvatar = itemView.findViewById(R.id.img_avatar);
        }
    }
}
