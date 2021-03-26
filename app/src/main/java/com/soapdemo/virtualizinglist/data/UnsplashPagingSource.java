package com.soapdemo.virtualizinglist.data;



import androidx.annotation.NonNull;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.orhanobut.logger.Logger;
import com.soapdemo.virtualizinglist.api.UnsplashService;
import com.soapdemo.virtualizinglist.model.PageResults;
import com.soapdemo.virtualizinglist.model.Photo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class UnsplashPagingSource extends RxPagingSource<Integer,Photo> {
    private String searchKey;
    private UnsplashService service;
    public static final int NETWORK_PAGE_SIZE = 10;

    public UnsplashPagingSource( String searchKey,UnsplashService service ){
        this.searchKey = searchKey;
        this.service = service;
    }

    @NotNull
    @Override
    public Single<LoadResult<Integer, Photo>> loadSingle(@NotNull LoadParams<Integer> loadParams) {
        Integer nextPageNumber = loadParams.getKey();
        if (nextPageNumber == null) {
            Logger.i( "-----First Request for Photo-----" );
            nextPageNumber = 1;
        }
        Logger.i( "----Begin Request,Load Photo searchKey :%s, page：%d size:%d", this.searchKey , nextPageNumber , loadParams.getLoadSize() );

        Integer finalNextPageNumber = nextPageNumber;
        return service.SearchPhotos(searchKey, nextPageNumber,loadParams.getLoadSize())
                .doOnError( e-> Logger.e( e, "SearchPhotos Error:(,%s",e.getMessage() ) )
                .subscribeOn(Schedulers.io())
                .map( photoPageResults -> toLoadResult(photoPageResults, finalNextPageNumber) )
                .onErrorReturn(LoadResult.Error::new);

    }

    private LoadResult<Integer, Photo> toLoadResult(
            @NonNull PageResults<Photo> response , Integer currentPage) {
        Logger.i( "----End Request,Load size:%d",response.results.size() );
        return new LoadResult.Page<>(
                response.results,
                currentPage == 1 ? null : currentPage - 1,
                currentPage == response.total_pages ? null : currentPage + response.results.size()/NETWORK_PAGE_SIZE ,
                LoadResult.Page.COUNT_UNDEFINED,
                LoadResult.Page.COUNT_UNDEFINED);
    }



    @Nullable
    @Override
    public Integer getRefreshKey(@NotNull PagingState<Integer, Photo> pagingState) {
        Integer anchorPosition = pagingState.getAnchorPosition();
        if (anchorPosition == null) {
            return null;
        }

        LoadResult.Page<Integer, Photo> anchorPage = pagingState.closestPageToPosition(anchorPosition);
        if (anchorPage == null) {
            return null;
        }

        Integer prevKey = anchorPage.getPrevKey();
        if (prevKey != null) {
            return prevKey + 1;
        }

        Integer nextKey = anchorPage.getNextKey();
        if (nextKey != null) {
            return nextKey - 1;
        }

        return null;

    }
}
