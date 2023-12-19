package com.main.travelApp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.main.travelApp.R;
import com.main.travelApp.models.Paragraph;

import java.util.List;

public class TourGalleryAdapter extends RecyclerView.Adapter<TourGalleryAdapter.ViewHolder> {

    private final List<Paragraph> paragraphs;

    private final Context context;

    public TourGalleryAdapter(List<Paragraph> paragraphs, Context context) {
        this.paragraphs = paragraphs;
        this.context = context;
    }

    @NonNull
    @Override
    public TourGalleryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view_tour_gallery, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TourGalleryAdapter.ViewHolder holder, int position) {
        Picasso.get().load(paragraphs.get(position).getImage().getSrc())
            .placeholder(R.color.light_gray)
            .error(R.color.light_gray)
            .into(holder.getImgGalleryItem());
    }

    @Override
    public int getItemCount() {
        return paragraphs != null ? paragraphs.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgGalleryItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgGalleryItem = itemView.findViewById(R.id.img_gallery_item);
        }

        public ImageView getImgGalleryItem() {
            return imgGalleryItem;
        }

    }
}
