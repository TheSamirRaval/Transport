package com.transport.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.transport.R;
import com.transport.app.GlideApp;
import com.transport.databinding.RvDrawerItemBinding;
import com.transport.listener.OnItemSelectListener;
import com.transport.model.DrawerItem;
import com.transport.utils.DataBinds;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAM on 27/3/20.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<DrawerItem> mList = new ArrayList<>();
    private OnItemSelectListener<DrawerItem> onItemSelectListener;
    private int selectedItem = 0;

    public DrawerAdapter(OnItemSelectListener<DrawerItem> onItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener;
        mList.addAll(DataBinds.getDrawerItems());
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        RvDrawerItemBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.rv_drawer_item, parent, false);
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

    public void addData(List<DrawerItem> list) {
        this.mList.clear();
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(int startIndex, List<DrawerItem> list) {
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

    public List<DrawerItem> getData() {
        return this.mList;
    }

    public void clearData() {
        this.mList.clear();
        notifyDataSetChanged();
    }

    public boolean isSelected(int position) {
        boolean isSelected = selectedItem == position;
        final int oldPosition = selectedItem;
        selectedItem = position;
        if (!isSelected) {
            notifyItemChanged(oldPosition);
            notifyItemChanged(selectedItem);
        }
        return isSelected;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RvDrawerItemBinding binding;

        private ViewHolder(RvDrawerItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;

            itemView.setOnClickListener(v -> onItemSelectListener.onItemSelect(getAdapterPosition(), mList.get(getAdapterPosition())));
        }

        private void bindData(DrawerItem model) {
//            binding.setDrawerItem(model);
//            binding.executePendingBindings();
            binding.tvTitle.setText(model.getTitle());
            binding.tvTitle.setSelected(getAdapterPosition() == selectedItem);
            GlideApp.with(itemView.getContext())
                    .load(getAdapterPosition() == selectedItem ? model.getPressedIcon() : model.getUnPressedIcon())
                    .into(binding.ivIcon);
        }
    }
}