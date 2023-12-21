package com.main.travelApp.adapters;

import android.content.Context;
import android.util.Log;
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
    public void onBindViewHolder(@NonNull RatingAdapter.ViewHolder holder, int position) {
        final Rate rate = rates.get(position);
        holder.txtUserName.setText(rate.getUsername());
        holder.txtRatedStar.setText(String.valueOf(rate.getStar()));
        holder.txtComment.setText(rate.getContent());
        if(rate.getAvatar() != null && !rate.getAvatar().isEmpty()) {
            Picasso.get()
                    .load(rate.getAvatar())
                    .placeholder(R.color.light_gray)
                    .error(R.drawable.avatar_profile)
                    .into(holder.imgAvatar);
        }
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
