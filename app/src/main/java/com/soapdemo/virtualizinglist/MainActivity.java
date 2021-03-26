package com.soapdemo.virtualizinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.soapdemo.virtualizinglist.databinding.ActivityMainBinding;
import com.soapdemo.virtualizinglist.ui.PhotoAdapter;
import com.soapdemo.virtualizinglist.ui.PhotoComparator;
import com.soapdemo.virtualizinglist.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private PhotoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainViewModel viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(MainViewModel.class);
        ActivityMainBinding binding  = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setDatacontext( viewModel );
        adapter = new PhotoAdapter( new PhotoComparator());
        binding.listPhoto.setAdapter(adapter);
        binding.buttonSearch.setOnClickListener( v -> viewModel.Search()
                .subscribe(photoPagingData -> adapter.submitData( this.getLifecycle(), photoPagingData)));
    }
}