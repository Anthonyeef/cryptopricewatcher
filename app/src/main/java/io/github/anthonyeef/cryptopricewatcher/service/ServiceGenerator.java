package io.github.anthonyeef.cryptopricewatcher.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wuyifen on 24/03/2018.
 */

public final class ServiceGenerator {

    private static final String BASE_URL = "https://api.coinmarketcap.com/v1/";
    private static final HttpLoggingInterceptor sLogging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static final OkHttpClient sClient = new OkHttpClient.Builder().addNetworkInterceptor(sLogging).build();
    private static final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    private static final Retrofit.Builder sBuilder = new Retrofit.Builder().client(sClient).baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson));

    public static <S> S createService(Class<S> className) {
        return sBuilder.build().create(className);
    }
}
