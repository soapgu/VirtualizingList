package com.soapdemo.virtualizinglist.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.soapdemo.virtualizinglist.R;
import com.soapdemo.virtualizinglist.model.Photo;
import androidx.databinding.DataBindingUtil;

import org.jetbrains.annotations.NotNull;

public class PhotoAdapter extends PagingDataAdapter<Photo, PhotoAdapter.ViewHolder> {

    public PhotoAdapter(@NotNull DiffUtil.ItemCallback<Photo> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding  = DataBindingUtil.inflate(inflater, R.layout.photo_item,parent, false );
        return new PhotoAdapter.ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewDataBinding binding = DataBindingUtil.getBinding(holder.itemView);
        assert binding != null;
        //binding.setContent(  (String)this.mDataSet.get(position));
        binding.setVariable(BR.datacontext , getItem(position) );
        binding.executePendingBindings();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
