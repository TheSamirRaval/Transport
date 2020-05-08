package com.transport.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.transport.R;
import com.transport.api.model.OrderProposal;
import com.transport.databinding.RvItemVehicleOwnerBinding;
import com.transport.listener.OnItemSelectListener;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by SAM on 5/5/2020.
 */
public class VehicleOwnerProposalAdapter extends RecyclerView.Adapter<VehicleOwnerProposalAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<OrderProposal.VehicleProposal> mList = new ArrayList<>();
    private OnItemSelectListener<OrderProposal.VehicleProposal> onItemSelectListener;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy 'at' hh:mm a", Locale.ENGLISH);
    private SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
    private NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("en", "in"));

    public VehicleOwnerProposalAdapter(OnItemSelectListener<OrderProposal.VehicleProposal> onItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        RvItemVehicleOwnerBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.rv_item_vehicle_owner, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addData(List<OrderProposal.VehicleProposal> list) {
        this.mList.clear();
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(int startIndex, List<OrderProposal.VehicleProposal> list) {
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

    public List<OrderProposal.VehicleProposal> getData() {
        return this.mList;
    }

    public void clearData() {
        this.mList.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RvItemVehicleOwnerBinding binding;

        private ViewHolder(RvItemVehicleOwnerBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;

            itemView.setOnClickListener(v -> onItemSelectListener.onItemSelect(getAdapterPosition(), mList.get(getAdapterPosition())));
        }

        @SuppressLint("SetTextI18n")
        private void bindData(OrderProposal.VehicleProposal model) {
//            binding.setOrderProposal.VehicleProposal(model);
//            binding.executePendingBindings();

            binding.tvVehicleOwnerName.setText(":  " + model.getUserName());
            binding.tvProductName.setText(":  -");
            try {
                binding.tvDate.setText(":  " + dateFormat.format(Objects.requireNonNull(apiDateFormat.parse(model.getProposalDateTime()))));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            binding.tvVehicleOwnerRate.setText(String.format(":  %s", numberFormat.format(model.getPrice())));
            binding.tvProfile.setText(":  -");
            binding.tvTotalProjects.setText(":  -");
        }
    }
}