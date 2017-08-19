package com.sheypoor.application.tvmaze.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sheypoor.application.tvmaze.R;
import com.sheypoor.application.tvmaze.core.FragmentBase;
import com.sheypoor.application.tvmaze.dto.response.episodeList.Episode;
import com.sheypoor.application.tvmaze.event.Events;
import com.sheypoor.application.tvmaze.service.episodeDetail.ServiceEpisodeDetail;
import com.sheypoor.application.tvmaze.service.factory.ServiceFactory;
import com.sheypoor.application.tvmaze.util.ConstantServices;
import com.sheypoor.application.tvmaze.util.RxBus;
import com.sheypoor.application.tvmaze.util.UtilString;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEpisodeDetail extends FragmentBase {
    View view;
    @BindView(R.id.tv_summary)
    TextView tvSummary;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    private Long number;
    private Long season;

    public FragmentEpisodeDetail() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public FragmentEpisodeDetail(Long number, Long season) {
        this.number = number;
        this.season = season;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_episode_detail, container, false);
        ButterKnife.bind(this, view);
        tvSummary.setMovementMethod(new ScrollingMovementMethod());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        serviceEpisodeDetail(number, season);
    }

    private void serviceEpisodeDetail(Long number, Long season) {
        ServiceEpisodeDetail serviceEpisodeDetail = ServiceFactory.createRetrofitService(ServiceEpisodeDetail.class, ConstantServices.END_POINT, ConstantServices.SER_NAME_EDPISODE_DETAIL, getActivity());
        RxBus.getInstance().send(new Events.EventLoadingView(true));
        serviceEpisodeDetail.resp(season, number)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseEpisodeDetailSubscriber());
    }

    @Override
    protected void handlerBus(Object o) {
        super.handlerBus(o);
    }

    private class ResponseEpisodeDetailSubscriber extends Subscriber<Episode> {
        @Override
        public void onCompleted() {
            // do nothing here
        }

        @Override
        public void onError(Throwable e) {
            RxBus.getInstance().send(new Events.EventLoadingView(false));
        }

        @Override
        public void onNext(Episode response) {
            if (null != response) {
                tvSummary.setText(UtilString.stripHtml(response.getSummary()));
                tvTitle.setText(response.getName());
                tvTime.setText(response.getAirdate() + ConstantServices.SPACE + response.getRuntime() + ConstantServices.MIN);
            }
            RxBus.getInstance().send(new Events.EventLoadingView(false));
        }
    }
}
