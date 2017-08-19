package com.sheypoor.application.tvmaze.core;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sheypoor.application.tvmaze.util.RxBus;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ActivityBase extends AppCompatActivity {
    private Subscription rxSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoUnSubBus();
        autoSubBus();
    }

    @Override
    protected void onPause() {
        super.onPause();
        autoUnSubBus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        autoUnSubBus();
    }

    private void autoSubBus() {
        rxSubscription = RxBus.getInstance().toObserverable().cache()
                .subscribeOn(Schedulers.newThread())
                .onBackpressureBuffer()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<Object>() {
                            @Override
                            public void call(Object o) {
                                handlerBus(o);
                            }
                        }
                );
    }

    protected void handlerBus(Object object) {

    }

    protected void autoUnSubBus() {
        if (null != rxSubscription && !rxSubscription.isUnsubscribed()) {
            rxSubscription.unsubscribe();
        }
    }
}
