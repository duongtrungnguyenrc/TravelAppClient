package com.main.travelApp.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.main.travelApp.R;
import com.main.travelApp.databinding.ItemViewPostCommentBinding;
import com.main.travelApp.models.Rate;
import com.main.travelApp.request.UpdateRateRequest;
import com.main.travelApp.ui.components.MyDialog;
import com.main.travelApp.viewmodels.BlogDetailViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostCommentAdapter extends ListAdapter<Rate, PostCommentAdapter.MyViewHolder> {
    private Context context;
    private BlogDetailViewModel viewModel;
    private LayoutInflater layoutInflater;
    private FragmentManager fragmentManager;

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void setViewModel(BlogDetailViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

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
        Long currentId = Long.valueOf(getItem(position).getId());
        String currentContent = getItem(position).getContent();
        holder.binding.btnMore.setVisibility(
                getItem(position).isActive() ? View.VISIBLE : View.GONE
        );
        Picasso.get()
                .load(getItem(position).getAvatar())
                .placeholder(R.color.light_gray)
                .error(R.color.light_gray)
                .into(holder.binding.imgUserAvatar);
        holder.binding.txtUserName.setText(getItem(position).getUsername());
        holder.binding.txtComment.setText(currentContent);
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
                                UpdateRateRequest request = new UpdateRateRequest();
                                request.setId(currentId);

                                MyDialog dialog = new MyDialog(context, layoutInflater, R.layout.fragment_enter_info_dialog, new MyDialog.Handler() {
                                    @Override
                                    public void handle(AlertDialog dialog, View contentView) {
                                        TextView txtTitle = (TextView) contentView.findViewById(R.id.txtTitle);
                                        EditText edtContent = (EditText) contentView.findViewById(R.id.edtContent);
                                        Button btnSave = (Button) contentView.findViewById(R.id.btnAgree);
                                        Button btnCancel = (Button) contentView.findViewById(R.id.btnCancel);

                                        btnSave.setText("Gửi");
                                        txtTitle.setText("Nội dung bình luận");
                                        edtContent.setHint("Nhập nội dung bình luận...");
                                        edtContent.setText(currentContent);

                                        btnCancel.setOnClickListener(view -> {
                                            dialog.dismiss();
                                        });
                                        btnSave.setOnClickListener(view -> {
                                            request.setComment(edtContent.getText().toString());
                                            viewModel.updateRate(request, dialog);
                                        });
                                    }
                                });
                                dialog.show(fragmentManager, "UPDATE_COMMENT_DIALOG");
                            }
                            case 1 -> {
                                MyDialog dialog = new MyDialog(context, layoutInflater, R.layout.fragment_confirm_dialog, new MyDialog.Handler() {
                                    @Override
                                    public void handle(AlertDialog dialog, View contentView) {
                                        TextView txtMessage = (TextView) contentView.findViewById(R.id.txtMessage);
                                        Button btnSave = (Button) contentView.findViewById(R.id.btnChange);
                                        Button btnCancel = (Button) contentView.findViewById(R.id.btnCancel);

                                        btnSave.setText("Xóa");
                                        txtMessage.setText("Bạn có chắc là muốn xóa bình luận này?");

                                        btnCancel.setOnClickListener(view -> {
                                            dialog.dismiss();
                                        });
                                        btnSave.setOnClickListener(view -> {
                                            viewModel.deleteRate(currentId);
                                            dialog.dismiss();
                                        });
                                    }
                                });
                                dialog.show(fragmentManager, "DELETE_COMMENT_DIALOG");
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
