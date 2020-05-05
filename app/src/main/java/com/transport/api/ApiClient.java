package com.transport.api;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by SAM on 8/3/20.
 */
public class ApiClient {
//    public static final String PREFIX = "http://transport.keypress.co.in/";
    public static final String PREFIX = "http://jaydeepa-001-site1.gtempurl.com/";

    public static final String BASE_URL = PREFIX + "api/";

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()));

    private static Retrofit retrofit;
    private static ApiService API_SERVICE_INSTANCE;


    private final static ObservableTransformer schedulersTransformer =
            observable -> observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = builder.build();
        }
        return retrofit;
    }

    public static ApiService getApiService() {
        if (API_SERVICE_INSTANCE == null) {
            API_SERVICE_INSTANCE = getRetrofit().create(ApiService.class);
        }
        return API_SERVICE_INSTANCE;
    }


    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return (ObservableTransformer<T, T>) schedulersTransformer;
    }

}
