package com.main.travelApp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.main.travelApp.R;
import com.main.travelApp.callbacks.OnRecyclerViewItemClickListener;
import com.main.travelApp.models.TourDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHolder> {

    private final List<TourDate> tourDates;
    private final Context context;
    private long selectedId;

    private OnRecyclerViewItemClickListener<TourDate> listener;

    public DateAdapter(List<TourDate> tourDates, Context context) {
        this.tourDates = tourDates;
        this.context = context;
    }

    @NonNull
    @Override
    public DateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view_date, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DateAdapter.ViewHolder holder, int position) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");


        if(tourDates.get(position).getId() == selectedId) {
            holder.itemView.setBackgroundResource(R.drawable.bg_active_date);
            holder.txtDate.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            holder.txtYear.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }
        else {
            holder.itemView.setBackgroundResource(R.drawable.bg_grey_border);
            holder.txtDate.setTextColor(ContextCompat.getColor(context, R.color.gray));
            holder.txtYear.setTextColor(ContextCompat.getColor(context, R.color.gray));
        }

        holder.itemView.setOnClickListener(view -> {
            TourDate clickedDate = tourDates.get(position);
            setSelectedId(clickedDate.getId());
            this.listener.onClick(clickedDate, position);
        });

        try {
            Date date = dateFormat.parse(tourDates.get(position).getDepartDate());
            assert date != null;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            holder.txtYear.setText(calendar.get(Calendar.YEAR) + "");
            holder.txtDate.setText(calendar.get(Calendar.DAY_OF_MONTH) + " Th " + (calendar.get(Calendar.MONTH) + 1));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return tourDates != null ? tourDates.size() : 0;
    }

    public void setSelectedId(long selectedId) {
        this.selectedId = selectedId;
        notifyDataSetChanged();
    }

    public void setOnDateSelectedChange(OnRecyclerViewItemClickListener<TourDate> listener) {
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView txtYear;
        final TextView txtDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtYear = itemView.findViewById(R.id.txt_year);
            this.txtDate = itemView.findViewById(R.id.txt_date);
        }
    }
}
