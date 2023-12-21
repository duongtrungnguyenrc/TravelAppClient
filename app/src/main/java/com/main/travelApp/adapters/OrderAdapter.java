package com.main.travelApp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.main.travelApp.R;
import com.main.travelApp.databinding.ItemViewOrderBinding;
import com.main.travelApp.models.Order;
import com.main.travelApp.ui.components.MyDialog;
import com.main.travelApp.viewmodels.UserOrderViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private List<Order> orders;
    private Context context;
    private LayoutInflater layoutInflater;
    private FragmentManager fragmentManager;
    private UserOrderViewModel viewModel;

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public UserOrderViewModel getViewModel() {
        return viewModel;
    }

    public void setViewModel(UserOrderViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewOrderBinding binding = ItemViewOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        MyViewHolder holder = new MyViewHolder(binding.getRoot(), binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Order currentOrder = orders.get(position);
        if(currentOrder.getTour().getImg() != null && !currentOrder.getTour().getImg().isEmpty()){
            Picasso.get()
                    .load(orders.get(position).getTour().getImg())
                    .placeholder(R.color.light_gray)
                    .error(R.color.light_gray)
                    .into(holder.binding.imgTourBackground);
        }
        holder.binding.txtTourName.setText(currentOrder.getTour().getName());
        holder.binding.txtAdults.setText("Người lớn: " + currentOrder.getAdults());
        holder.binding.txtChildren.setText("Trẻ em: " + currentOrder.getChildren());
        holder.binding.txtOrderId.setText("Mã đơn hàng: " + currentOrder.getId());
        holder.binding.txtOrderedDate.setText("Ngày đặt: " + currentOrder.getOrderDate());
        holder.binding.txtEndDate.setText("Kết thúc: " + currentOrder.getEndDate());
        holder.binding.txtStartDate.setText("Khởi hành: " + currentOrder.getDepartDate());
        holder.binding.txtOrderPrice.setText(currentOrder.getTotalPrice() + " VNĐ");
        holder.binding.txtStatus.setText(currentOrder.getStatus());
        switch (currentOrder.getStatus()){
            case "Đã thanh toán" -> {
                holder.binding.txtStatus.setBackground(context.getDrawable(R.color.green));
            }
            case "Đã hủy" -> {
                holder.binding.txtStatus.setBackground(context.getDrawable(R.color.vertical_stepper_form_text_color_error_message));
            }
            case "Đang chờ" -> {
                holder.binding.txtStatus.setBackground(context.getDrawable(R.color.colorPrimary));
            }
        }
        holder.binding.item.setOnClickListener(view -> {
            MyDialog orderDetailDialog = new MyDialog(context, layoutInflater, R.layout.fragment_order_detail_dialog, new MyDialog.Handler() {
                @Override
                public void handle(AlertDialog dialog, View contentView) {
                    Button btnClose = contentView.findViewById(R.id.btnClose);
                    Button btnCancelOrder = contentView.findViewById(R.id.btnCancelOrder);
                    ((TextView) contentView.findViewById(R.id.txtTourName)).setText(currentOrder.getTour().getName().toUpperCase());
                    ((TextView) contentView.findViewById(R.id.txtOrderId)).setText("Mã đơn hàng: " + currentOrder.getId());
                    ((TextView) contentView.findViewById(R.id.txtOrderedDate)).setText("Ngày đặt: " + currentOrder.getOrderDate());
                    ((TextView) contentView.findViewById(R.id.txtAdults)).setText("Người lớn: " + currentOrder.getAdults());
                    ((TextView) contentView.findViewById(R.id.txtChildren)).setText("Trẻ em: " + currentOrder.getChildren());
                    ((TextView) contentView.findViewById(R.id.txtStartDate)).setText("Khởi hành: " + currentOrder.getDepartDate());
                    ((TextView) contentView.findViewById(R.id.txtEndDate)).setText("Kết thúc: " + currentOrder.getEndDate());
                    ((TextView) contentView.findViewById(R.id.txtDepart)).setText("Xuất phát: " + currentOrder.getTour().getDepart());
                    ((TextView) contentView.findViewById(R.id.txtDestination)).setText("Đích: " + currentOrder.getTour().getLocation());
                    ((TextView) contentView.findViewById(R.id.txtPaymentMethod)).setText("PTTT: " + currentOrder.getPaymentMethod());
                    ((TextView) contentView.findViewById(R.id.txtOrderPrice)).setText(currentOrder.getTotalPrice() + " VNĐ");
                    ((TextView) contentView.findViewById(R.id.txtFullName)).setText(currentOrder.getContactInfo().getCustomerFullName());
                    ((TextView) contentView.findViewById(R.id.txtPhone)).setText(currentOrder.getContactInfo().getCustomerPhone());
                    ((TextView) contentView.findViewById(R.id.txtEmail)).setText(currentOrder.getContactInfo().getCustomerEmail());
                    ((TextView) contentView.findViewById(R.id.txtAddress)).setText(currentOrder.getContactInfo().getCustomerAddress());
                    ((TextView) contentView.findViewById(R.id.txtSpecialRequest)).setText(currentOrder.getSpecialRequest());
                    ((TextView) contentView.findViewById(R.id.txtStatus)).setText(currentOrder.getStatus());
                    if(currentOrder.getHotel() != null){
                        ((TextView) contentView.findViewById(R.id.txtHotelName)).setText(currentOrder.getHotel().getName());
                        ((TextView) contentView.findViewById(R.id.txtHotelAddress)).setText(currentOrder.getHotel().getAddress());
                        if(currentOrder.getHotel().getIllustration() != null && !currentOrder.getHotel().getIllustration().isEmpty()){
                            Picasso.get()
                                    .load(currentOrder.getHotel().getIllustration())
                                    .error(R.color.light_gray)
                                    .placeholder(R.color.light_gray)
                                    .into(((ImageView) contentView.findViewById(R.id.imgHotelThumbnail)));
                        }
                    }else{
                        contentView.findViewById(R.id.extraServiceWrapper).setVisibility(View.GONE);
                    }
                    switch (currentOrder.getStatus()){
                        case "Đã thanh toán" -> {
                            ((TextView) contentView.findViewById(R.id.txtStatus)).setBackground(context.getDrawable(R.color.green));
                        }
                        case "Đã hủy" -> {
                            ((TextView) contentView.findViewById(R.id.txtStatus)).setBackground(context.getDrawable(R.color.vertical_stepper_form_text_color_error_message));
                            btnCancelOrder.setVisibility(View.GONE);
                        }
                        case "Đang chờ" -> {
                            ((TextView) contentView.findViewById(R.id.txtStatus)).setBackground(context.getDrawable(R.color.colorPrimary));
                        }
                    }
                    if(currentOrder.getTour().getImg() != null && !currentOrder.getTour().getImg().isEmpty()){
                        Picasso.get()
                                .load(currentOrder.getTour().getImg())
                                .error(R.color.light_gray)
                                .placeholder(R.color.light_gray)
                                .into(((ImageView) contentView.findViewById(R.id.imgTourBackground)));
                    }
                    btnClose.setOnClickListener(view -> {
                        dialog.dismiss();
                    });
                    btnCancelOrder.setOnClickListener(view -> {
                        viewModel.cancelOrder(currentOrder.getId(), dialog);
                    });
                }
            });
            orderDetailDialog.show(fragmentManager, "ORDER_DETAIL_DIALOG");
        });
    }

    @Override
    public int getItemCount() {
        return orders != null ? orders.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ItemViewOrderBinding binding;
        public MyViewHolder(@NonNull View itemView, ItemViewOrderBinding binding) {
            super(itemView);
            this.binding = binding;
        }
    }
}
