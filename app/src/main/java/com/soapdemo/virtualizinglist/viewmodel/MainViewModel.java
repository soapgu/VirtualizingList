package com.soapdemo.virtualizinglist.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.soapdemo.virtualizinglist.api.UnsplashService;
import com.soapdemo.virtualizinglist.data.PhotoRepository;

public class MainViewModel extends ObservableViewModel {

    private PhotoRepository repository;
    private String searchKey = "cow";

    public MainViewModel(@NonNull Application application) {
        super(application);
        UnsplashService unsplashService = new UnsplashService();
        repository = new PhotoRepository(unsplashService);
    }

    @Bindable
    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        if(!searchKey.equals(this.searchKey)) {
            this.searchKey = searchKey;
            notifyPropertyChanged(BR.searchKey);
        }
    }

    public void Search()
    {
        String aaa = this.searchKey;
    }

}
