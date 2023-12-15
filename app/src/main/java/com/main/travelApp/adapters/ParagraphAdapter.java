package com.main.travelApp.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.travelApp.R;
import com.main.travelApp.databinding.ItemViewParagraphBinding;
import com.main.travelApp.models.Paragraph;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ParagraphAdapter extends RecyclerView.Adapter<ParagraphAdapter.MyViewHolder>{
    List<Paragraph> paragraphs;
    public ParagraphAdapter(List<Paragraph> paragraphs){
        this.paragraphs = paragraphs;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewParagraphBinding binding = ItemViewParagraphBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        MyViewHolder viewHolder = new MyViewHolder(binding.getRoot(), binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.txtContent.setText(paragraphs.get(position).getContent());
        holder.binding.imgName.setText(paragraphs.get(position).getImgName());
        Picasso.get().load(
                paragraphs.get(position).getImgSrc()
        )
                .placeholder(R.color.light_gray)
                .error(R.color.light_gray)
                .into(holder.binding.imgParagraphImg);
    }

    @Override
    public int getItemCount() {
        return paragraphs.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private final ItemViewParagraphBinding binding;
        public MyViewHolder(@NonNull View itemView, ItemViewParagraphBinding binding) {
            super(itemView);
            this.binding = binding;
        }
    }
}
