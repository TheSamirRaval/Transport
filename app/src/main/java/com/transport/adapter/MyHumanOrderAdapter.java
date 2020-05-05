package com.transport.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.transport.api.model.Orders;
import com.transport.databinding.RvItemMyOrderBinding;
import com.transport.databinding.RvMyHumanOrderItemBinding;
import com.transport.databinding.RvMyOrderDateItemBinding;
import com.transport.listener.OnItemSelectListener;
import com.transport.utils.SharedPrefs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by SAM on 16/3/20.
 */
public class MyHumanOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Object> mList = new ArrayList<>();
    private OnItemSelectListener<Orders.AllOrder.OrderList> onItemSelectListener;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
    private SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
    private boolean isOwner = false;

    public MyHumanOrderAdapter(OnItemSelectListener<Orders.AllOrder.OrderList> onItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener;
    }

    public MyHumanOrderAdapter(boolean isOwner, OnItemSelectListener<Orders.AllOrder.OrderList> onItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener;
        this.isOwner = isOwner;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) layoutInflater = LayoutInflater.from(parent.getContext());
        return viewType == 0 ? new DateViewHolder(RvMyOrderDateItemBinding.inflate(layoutInflater, parent, false))
                : new ViewHolder(RvMyHumanOrderItemBinding.inflate(layoutInflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DateViewHolder)
            ((DateViewHolder) holder).bindData((String) mList.get(position));
        else ((ViewHolder) holder).bindData((Orders.AllOrder.OrderList) mList.get(position));

    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position) instanceof String ? 0 : 1;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addData(List<Object> list) {
        this.mList.clear();
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(int startIndex, List<Object> list) {
        if (startIndex == 1) {
            this.mList.clear();
            this.mList.addAll(list);
            notifyDataSetChanged();
        } else {
            int index = mList.size();
            this.mList.addAll(list);
            notifyItemRangeInserted(index, list.size());
        }
    }

    public List<Object> getData() {
        return this.mList;
    }

    public void clearData() {
        this.mList.clear();
        notifyDataSetChanged();
    }

    public void updateItem(int selectedItem, Orders.AllOrder.OrderList allOrder) {
        mList.set(selectedItem, allOrder);
        notifyItemChanged(selectedItem);
    }

    class DateViewHolder extends RecyclerView.ViewHolder {
        private RvMyOrderDateItemBinding binding;

        DateViewHolder(@NonNull RvMyOrderDateItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bindData(String date) {
            try {
                binding.tvDate.setText(dateFormat.format(Objects.requireNonNull(apiDateFormat.parse(date))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RvMyHumanOrderItemBinding binding;

        private ViewHolder(RvMyHumanOrderItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;

            itemView.setOnClickListener(v -> onItemSelectListener.onItemSelect(getAdapterPosition(), (Orders.AllOrder.OrderList) mList.get(getAdapterPosition())));
        }

        @SuppressLint("SetTextI18n")
        private void bindData(Orders.AllOrder.OrderList model) {
            binding.tvYourLocation.setText(model.getFromAddress());
            binding.tvLocation.setText(model.getToAddress());
            binding.tvPriceValue.setText(model.getPrice().toString());
            binding.tvTotalBidsValue.setText(model.getProposalCount().toString());
            if (model.getProductList() != null)
                binding.tvMaterialTypeValue.setText(model.getProductList().get(0).getQty().toString());

            //            binding.set Orders.OrderList(model);
//            binding.executePendingBindings();
        }
    }
}