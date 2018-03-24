package io.github.anthonyeef.cryptopricewatcher.service;

import java.util.List;

import io.github.anthonyeef.cryptopricewatcher.data.entity.CoinEntity;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by wuyifen on 24/03/2018.
 */

public interface TickerService {

    @GET("ticker")
    Flowable<List<CoinEntity>> getTickerData();
}
