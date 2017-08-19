package com.sheypoor.application.tvmaze.core;


import android.support.v4.app.Fragment;

import com.sheypoor.application.tvmaze.util.RxBus;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBase extends Fragment {
    private Subscription rxSubscription;

    public FragmentBase() {
        // Required empty public constructor
    }


    public void autoSub() {
        rxSubscription = RxBus.getInstance().toObserverable().cache()
                .subscribeOn(Schedulers.newThread())
                .onBackpressureBuffer()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<Object>() {
                            @Override
                            public void call(Object o) {
                                if (o == null) return;
                                handlerBus(o);
                            }
                        }
                );
    }

    protected void autoUnSubBus() {
        if (rxSubscription != null && !rxSubscription.isUnsubscribed()) {
            rxSubscription.unsubscribe();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        autoUnSubBus();
        autoSub();
    }

    @Override
    public void onPause() {
        super.onPause();
        autoUnSubBus();
    }

    protected void handlerBus(Object o) {

    }

}
