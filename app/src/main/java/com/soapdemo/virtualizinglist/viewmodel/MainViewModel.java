package com.soapdemo.virtualizinglist.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.paging.PagingData;

import com.soapdemo.virtualizinglist.api.UnsplashService;
import com.soapdemo.virtualizinglist.data.PhotoRepository;
import com.soapdemo.virtualizinglist.model.Photo;
import com.soapdemo.virtualizinglist.ui.PhotoAdapter;
import com.soapdemo.virtualizinglist.ui.PhotoComparator;

import io.reactivex.rxjava3.core.Flowable;

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

    public Flowable<PagingData<Photo>> Search() {
        return repository.GetFlowable(this.searchKey);
    }


}
