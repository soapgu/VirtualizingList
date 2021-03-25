package com.soapdemo.virtualizinglist.data;

import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.soapdemo.virtualizinglist.api.UnsplashService;
import com.soapdemo.virtualizinglist.model.Photo;

import io.reactivex.rxjava3.core.Flowable;

public class PhotoRepository {
    private UnsplashService service;
    public PhotoRepository( UnsplashService service )
    {
        this.service = service;
    }

    public Flowable<PagingData<Photo>> GetFlowable(String searchKey) {
        Pager<Integer, Photo> pager = new Pager<>(
            new PagingConfig(  UnsplashPagingSource.NETWORK_PAGE_SIZE),
            () -> new UnsplashPagingSource(searchKey, this.service));

        return PagingRx.getFlowable(pager);
    }
}
