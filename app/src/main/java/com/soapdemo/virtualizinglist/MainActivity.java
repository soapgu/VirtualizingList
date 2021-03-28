package com.soapdemo.virtualizinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.LoadState;

import android.os.Bundle;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.soapdemo.virtualizinglist.databinding.ActivityMainBinding;
import com.soapdemo.virtualizinglist.ui.PhotoAdapter;
import com.soapdemo.virtualizinglist.ui.PhotoComparator;
import com.soapdemo.virtualizinglist.ui.PhotoLoadStateAdapter;
import com.soapdemo.virtualizinglist.viewmodel.MainViewModel;

import io.reactivex.rxjava3.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private PhotoAdapter adapter;
    private Disposable searchJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainViewModel viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(MainViewModel.class);
        ActivityMainBinding binding  = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setDatacontext( viewModel );
        adapter = new PhotoAdapter( new PhotoComparator());

        binding.listPhoto.setAdapter( adapter.withLoadStateHeaderAndFooter(
                new PhotoLoadStateAdapter(v ->  adapter.retry() ),
                new PhotoLoadStateAdapter(v ->  adapter.retry() )
        ) );

        adapter.addLoadStateListener( loadStates -> {
                    // Only show the list if refresh succeeds.
                    Logger.i( "Current Refresh State:" + loadStates.getRefresh().toString() );
                    binding.listPhoto.setVisibility(loadStates.getRefresh() instanceof LoadState.NotLoading
                            ? View.VISIBLE : View.GONE);

                    // Show loading spinner during initial load or refresh.
                    binding.progressBar.setVisibility(loadStates.getRefresh() instanceof LoadState.Loading
                            ? View.VISIBLE : View.GONE);

                    // Show the retry state if initial load or refresh fails.
                    binding.retryButton.setVisibility(loadStates.getRefresh() instanceof LoadState.Error
                            ? View.VISIBLE : View.GONE);
                    return null;
                }
         );
        binding.retryButton.setOnClickListener( v -> adapter.retry() );
        binding.buttonSearch.setOnClickListener( v -> {
            if( searchJob != null && !searchJob.isDisposed() ) {
                searchJob.dispose();
                searchJob = null;
            }
            this.searchJob = viewModel.Search()
                    .subscribe(photoPagingData -> adapter.submitData( this.getLifecycle(), photoPagingData));
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if( this.searchJob != null && !this.searchJob.isDisposed() )
            this.searchJob.dispose();

    }
}