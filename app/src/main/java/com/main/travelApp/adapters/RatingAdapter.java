package com.main.travelApp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.travelApp.R;
import com.main.travelApp.models.Rate;

import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder> {
    private final List<Rate> rates;
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
        holder.getTxtUserName().setText(rate.getUsername());
        holder.getTxtRatedStar().setText(String.valueOf(rate.getStar()));
        holder.getTxtComment().setText(rate.getContent());
    }

    @Override
    public int getItemCount() {
        return rates != null ? rates.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtUserName;
        private final TextView txtRatedStar;
        private final TextView txtComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtUserName = itemView.findViewById(R.id.txt_user_name);
            this.txtRatedStar = itemView.findViewById(R.id.txt_rated_star);
            this.txtComment = itemView.findViewById(R.id.txt_comment);
        }

        public TextView getTxtUserName() {
            return txtUserName;
        }

        public TextView getTxtRatedStar() {
            return txtRatedStar;
        }

        public TextView getTxtComment() {
            return txtComment;
        }
    }
}
