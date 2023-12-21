package com.main.travelApp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.main.travelApp.R;
import com.main.travelApp.databinding.ItemViewPostCommentBinding;
import com.main.travelApp.models.Rate;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostCommentAdapter extends ListAdapter<Rate, PostCommentAdapter.MyViewHolder> {
    private Context context;
    private static final DiffUtil.ItemCallback<Rate> DIFF_CALLBACK = new DiffUtil.ItemCallback<Rate>() {
        @Override
        public boolean areItemsTheSame(@NonNull Rate oldItem, @NonNull Rate newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Rate oldItem, @NonNull Rate newItem) {
            return oldItem.getAvatar().equals(newItem.getAvatar()) &&
                    oldItem.getContent().equals(newItem.getContent()) &&
                    oldItem.getStar() == newItem.getStar() &&
                    oldItem.getRatedStar() == newItem.getRatedStar() &&
                    oldItem.getRatedDate().equals(newItem.getRatedDate()) &&
                    oldItem.getUsername().equals(newItem.getUsername());
        }
    };
    public PostCommentAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewPostCommentBinding binding = ItemViewPostCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(binding.getRoot(), binding);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.btnMore.setVisibility(
                getItem(position).isActive() ? View.VISIBLE : View.GONE
        );
        Picasso.get()
                .load(getItem(position).getAvatar())
                .placeholder(R.color.light_gray)
                .error(R.color.light_gray)
                .into(holder.binding.imgUserAvatar);
        holder.binding.txtUserName.setText(getItem(position).getUsername());
        holder.binding.txtComment.setText(getItem(position).getContent());
        holder.binding.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.getMenu().add(position, 0, 0, "Sửa");
                popupMenu.getMenu().add(position, 1, 1, "Xóa");

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case 0 -> {
                                Toast.makeText(context, "Sửa " + menuItem.getGroupId(), Toast.LENGTH_SHORT).show();
                            }
                            case 1 -> {
                                Toast.makeText(context, "Xóa " + menuItem.getGroupId(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemViewPostCommentBinding binding;

        public MyViewHolder(@NonNull View itemView, ItemViewPostCommentBinding binding) {
            super(itemView);
            this.binding = binding;
        }
    }
}
