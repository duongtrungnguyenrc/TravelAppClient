package com.main.travelApp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.main.travelApp.R;
import com.main.travelApp.models.Tour;
import com.main.travelApp.models.TourDate;
import com.main.travelApp.ui.activities.SelectTicketActivity;

import java.util.List;

public class TourDateAdapter extends RecyclerView.Adapter<TourDateAdapter.ViewHolder> {

    private List<TourDate> dates;
    private final Context context;
    private final String tourName;

    private final Tour tour;

    public TourDateAdapter(List<TourDate> dates, String tourName, Context context, Tour tour) {
        this.dates = dates;
        this.context = context;
        this.tourName = tourName;
        this.tour = tour;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtDateName;
        private final TextView txtStartDate;
        private final TextView txtDatePrice;
        private final Button btnSelectDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtDateName = itemView.findViewById(R.id.txt_date_name);
            this.txtStartDate = itemView.findViewById(R.id.txt_start_date);
            this.txtDatePrice = itemView.findViewById(R.id.txt_date_price);
            this.btnSelectDate = itemView.findViewById(R.id.btn_select_date);
        }

        public TextView getTxtDateName() {
            return txtDateName;
        }

        public TextView getTxtStartDate() {
            return txtStartDate;
        }

        public TextView getTxtDatePrice() {
            return txtDatePrice;
        }

        public Button getBtnSelectDate() {
            return btnSelectDate;
        }
    }

    public void setDates(List<TourDate> dates) {
        this.dates = dates;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TourDateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_view_tour_date, parent, false)
        );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TourDateAdapter.ViewHolder holder, int position) {
        TourDate tourDate = dates.get(position);
            holder.getTxtDateName().setText(tourName + " hạng vé " + tourDate.getType());
            holder.getTxtStartDate().setText(tourDate.getDepartDate());
            holder.getTxtDatePrice().setText(tourDate.getAdultPrice() + " VND");

            holder.getBtnSelectDate().setOnClickListener(view -> {
                Intent intent = new Intent(context, SelectTicketActivity.class);
                intent.putExtra("tour", new Gson().toJson(tour));
                intent.putExtra("date-id", tourDate.getId());
                context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dates != null ? dates.size() : 0;
    }
}
