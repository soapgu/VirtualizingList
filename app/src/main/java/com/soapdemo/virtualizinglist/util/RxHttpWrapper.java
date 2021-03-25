package com.soapdemo.virtualizinglist.util;

import java.lang.reflect.Type;

import io.reactivex.rxjava3.core.Single;
import okhttp3.Request;

public class RxHttpWrapper {
    /**
     * 把Http的Json返回包装成Rx的Single<T>对象
     * @param request  http的request
     * @param classOfT json反序列化类
     * @param <T> json反序列化泛型类
     * @return Rx Single 对象
     */
    public static <T> Single<T> HttpJsonSingle(Request request , Class<T> classOfT ) {
        return Single.create(subscriber ->
            HttpClientWrapper.getInstance().ResponseJson(request,
                    subscriber::onSuccess,
                    subscriber::onError,
                    classOfT)
            );
    }

    /**
     * 把Http的泛型Json返回包装成Rx的Single<T>对象
     * @param request http的request
     * @param type json反序列化泛型类
     * @param <T> json反序列化泛型类
     * @return Rx Single 对象
     */
    public static <T> Single<T> HttpGenericJsonSingle(Request request, Type type ) {
        return Single.create(subscriber ->
                HttpClientWrapper.getInstance().ResponseGenericJson(request,
                        subscriber::onSuccess,
                        subscriber::onError,
                        type)
                );
    }

}
