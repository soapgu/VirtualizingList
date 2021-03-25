package com.soapdemo.virtualizinglist.ui;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.soapdemo.virtualizinglist.model.Photo;

public class PhotoComparator extends DiffUtil.ItemCallback<Photo> {
    @Override
    public boolean areItemsTheSame(@NonNull Photo oldItem, @NonNull Photo newItem) {
        return oldItem.id.equals( newItem.id );
    }

    @SuppressLint("DiffUtilEquals")
    @Override
    public boolean areContentsTheSame(@NonNull Photo oldItem, @NonNull Photo newItem) {
        return oldItem == newItem;
    }
}
