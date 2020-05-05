package com.transport.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.transport.R;
import com.transport.api.ApiClient;
import com.transport.api.model.RowMaterials;
import com.transport.app.GlideApp;
import com.transport.databinding.RvItemRowMaterialDialogBinding;
import com.transport.listener.OnItemSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAM on 16/3/20.
 */
public class RowMatirealDialogAdapter extends RecyclerView.Adapter<RowMatirealDialogAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<RowMaterials.ProductMaterial> mList = new ArrayList<>();
    private OnItemSelectListener<RowMaterials.ProductMaterial> onItemSelectListener;

    public RowMatirealDialogAdapter(OnItemSelectListener<RowMaterials.ProductMaterial> onItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        RvItemRowMaterialDialogBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.rv_item_row_material_dialog, parent, false);
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

    public void addData(List<RowMaterials.ProductMaterial> list) {
        this.mList.clear();
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(int startIndex, List<RowMaterials.ProductMaterial> list) {
        if (startIndex == 0) {
            this.mList.clear();
            this.mList.addAll(list);
            notifyDataSetChanged();
        } else {
            int index = mList.size();
            this.mList.addAll(list);
            notifyItemRangeInserted(index, list.size());
        }
    }

    public List<RowMaterials.ProductMaterial> getData() {
        return this.mList;
    }

    public void clearData() {
        this.mList.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RvItemRowMaterialDialogBinding binding;

        private ViewHolder(RvItemRowMaterialDialogBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;

            itemView.setOnClickListener(v -> onItemSelectListener.onItemSelect(getAdapterPosition(), mList.get(getAdapterPosition())));
        }

        private void bindData(RowMaterials.ProductMaterial model) {
            binding.setMaterial(model);
            binding.executePendingBindings();
//            GlideApp.with(itemView.getContext()).load(ApiClient.PREFIX + model.getImage()).centerCrop().into(binding.ivIcon);
//            binding.tvName.setText(model.getName());
            binding.llMain.setSelected(model.isSelected());
        }
    }
}