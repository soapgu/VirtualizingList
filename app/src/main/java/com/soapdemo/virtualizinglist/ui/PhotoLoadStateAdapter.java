package com.soapdemo.virtualizinglist.ui;

import android.view.View;
import android.view.ViewGroup;

import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;

import org.jetbrains.annotations.NotNull;

public class PhotoLoadStateAdapter extends LoadStateAdapter<PhotoLoadStateViewHolder> {

    private View.OnClickListener mRetryCallback;

    public PhotoLoadStateAdapter(View.OnClickListener retryCallback) {
        mRetryCallback = retryCallback;
    }


    @Override
    public void onBindViewHolder(@NotNull PhotoLoadStateViewHolder photoLoadStateViewHolder, @NotNull LoadState loadState) {
        photoLoadStateViewHolder.bind(loadState);
    }

    @NotNull
    @Override
    public PhotoLoadStateViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, @NotNull LoadState loadState) {
        return new PhotoLoadStateViewHolder(viewGroup, mRetryCallback);
    }
}
