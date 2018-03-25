package io.github.anthonyeef.cryptopricewatcher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import io.fabric.sdk.android.Fabric;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.github.anthonyeef.cryptopricewatcher.data.entity.CoinEntity;
import io.github.anthonyeef.cryptopricewatcher.service.ServiceGenerator;
import io.github.anthonyeef.cryptopricewatcher.service.TickerService;
import io.github.anthonyeef.cryptopricewatcher.viewBinder.CoinListItemViewBinder;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mList;
    private MultiTypeAdapter mAdapter;
    private Items items;
    private TickerService service = ServiceGenerator.createService(TickerService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        setupView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        processCoinObservable(service.getTickerData());

        refreshEveyMinute();
    }


    private void setupView() {
        mList = findViewById(android.R.id.list);
        DividerItemDecoration decoration = new DividerItemDecoration(mList.getContext(), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(getResources().getDrawable(R.drawable.divider_background));
        mList.addItemDecoration(decoration);
        items = new Items();
        mAdapter = new MultiTypeAdapter(items);
        mAdapter.register(CoinEntity.class, new CoinListItemViewBinder());

        mList.setAdapter(mAdapter);
    }


    private void processCoinObservable(Flowable<List<CoinEntity>> data) {
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(
                        coinEntities -> {
                            items.clear();
                            items.addAll(coinEntities);
                            mAdapter.notifyDataSetChanged();
                        },
                        throwable -> {

                        });
    }


    private void refreshEveyMinute() {
        Flowable.interval(1, TimeUnit.MINUTES, Schedulers.io())
                .filter(index -> index != 0)
                .flatMap(index -> service.getTickerData())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        coinEntities -> {
                            Log.d(this.toString(), Thread.currentThread().getName());
                            items.clear();
                            items.addAll(coinEntities);
                            mAdapter.notifyDataSetChanged();
                        },
                        throwable -> {

                        });
    }
}
