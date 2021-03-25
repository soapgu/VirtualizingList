package com.soapdemo.virtualizinglist.api;

import com.google.gson.reflect.TypeToken;
import com.soapdemo.virtualizinglist.model.PageResults;
import com.soapdemo.virtualizinglist.model.Photo;
import com.soapdemo.virtualizinglist.util.RxHttpWrapper;

import java.lang.reflect.Type;

import io.reactivex.rxjava3.core.Single;
import okhttp3.Request;

public class UnsplashService {
    private static String url = "https://api.unsplash.com/search/photos?client_id=ki5iNzD7hebsr-d8qUlEJIhG5wxGwikU71nsqj8PcMM&query=%s&page=%d&per_page=%d";

    /**
     * 搜索unsplash图片
     * @param searchKey 搜索关键字
     * @return 搜索结果
     */
    public Single<PageResults<Photo>> SearchPhotos( String searchKey , int page , int size ){
        Type jsonType = new TypeToken<PageResults<Photo>>() {}.getType();
        Request request = new Request.Builder()
                .url( String.format( url , searchKey ,page , size ) )
                .get()
                .build();

        return RxHttpWrapper.HttpGenericJsonSingle(request,jsonType );
    }
}
