package com.transport.adapter;

import android.view.View;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAM on 27/3/20.
 */
abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private List<T> mList = new ArrayList<>();

    private class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
