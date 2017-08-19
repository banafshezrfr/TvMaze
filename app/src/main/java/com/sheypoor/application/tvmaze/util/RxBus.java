package com.sheypoor.application.tvmaze.util;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;

/**
 * Created by Banafshe.Zarefar on 15/08/2017.
 */

public class RxBus {
    private static RxBus instance;
    private final rx.subjects.Subject<Object, Object> bus = new SerializedSubject<>(PublishSubject.create());

    public RxBus() {
    }

    public static RxBus getInstance() {
        if (instance == null) {
            return instance = new RxBus();
        } else {
            return instance;
        }
    }

    public void send(Object o) {
        bus.onBackpressureBuffer();
        bus.onNext(o);
    }

    public Observable<Object> toObserverable() {
        bus.onBackpressureBuffer();
        return bus;
    }
}
