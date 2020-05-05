package com.transport.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.transport.api.ApiClient;
import com.transport.api.model.OwnerVehicle;
import com.transport.api.model.Vehicles;
import com.transport.app.GlideApp;
import com.transport.databinding.RvItemVehicleDialogBinding;
import com.transport.listener.OnItemSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAM on 29/3/20.
 */
public class OwnerVehicleDialogAdapter extends RecyclerView.Adapter<OwnerVehicleDialogAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<OwnerVehicle.Vehicle> mList = new ArrayList<>();
    private OnItemSelectListener<OwnerVehicle.Vehicle> onItemSelectListener;

    public OwnerVehicleDialogAdapter(OnItemSelectListener<OwnerVehicle.Vehicle> onItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        return new ViewHolder(RvItemVehicleDialogBinding.inflate(layoutInflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addData(List<OwnerVehicle.Vehicle> list) {
        this.mList.clear();
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(int startIndex, List<OwnerVehicle.Vehicle> list) {
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

    public List<OwnerVehicle.Vehicle> getData() {
        return this.mList;
    }

    public void clearData() {
        this.mList.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RvItemVehicleDialogBinding binding;

        private ViewHolder(RvItemVehicleDialogBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;

            itemView.setOnClickListener(v -> onItemSelectListener.onItemSelect(getAdapterPosition(), mList.get(getAdapterPosition())));
        }

        private void bindData(OwnerVehicle.Vehicle model) {
//            binding.setVehicle(model);
//            binding.executePendingBindings();
            GlideApp.with(itemView.getContext()).load(ApiClient.PREFIX + model.getImage()).centerCrop().into(binding.ivIcon);
            binding.tvName.setText(model.getVehicleType());
            binding.llMain.setSelected(model.isSelected());
        }
    }
}