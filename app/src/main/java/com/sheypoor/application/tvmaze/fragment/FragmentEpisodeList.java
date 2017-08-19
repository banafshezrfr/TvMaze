package com.sheypoor.application.tvmaze.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sheypoor.application.tvmaze.R;
import com.sheypoor.application.tvmaze.adapter.AdapterEpisodeList;
import com.sheypoor.application.tvmaze.core.FragmentBase;
import com.sheypoor.application.tvmaze.dto.response.episodeList.Episode;
import com.sheypoor.application.tvmaze.event.Events;
import com.sheypoor.application.tvmaze.service.episodeList.ServiceEpisodeList;
import com.sheypoor.application.tvmaze.service.factory.ServiceFactory;
import com.sheypoor.application.tvmaze.util.ConstantServices;
import com.sheypoor.application.tvmaze.util.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEpisodeList extends FragmentBase {
    @BindView(R.id.rv_episodes)
    RecyclerView rvEpisodes;
    List<Episode> episodeList = new ArrayList<>();
    private View view;
    private AdapterEpisodeList adapterEpisodeList;

    public FragmentEpisodeList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_episode_list, container, false);
        ButterKnife.bind(this, view);

        int numberOfColumns = 2;
        final GridLayoutManager gridLayoutManager = (new GridLayoutManager(getActivity(), numberOfColumns));
        rvEpisodes.setLayoutManager(gridLayoutManager);
        rvEpisodes.setItemAnimator(new DefaultItemAnimator());
        rvEpisodes.setHasFixedSize(true);
        adapterEpisodeList = new AdapterEpisodeList(episodeList, getContext());
        rvEpisodes.setAdapter(adapterEpisodeList);
        rvEpisodes.setNestedScrollingEnabled(false);
        serviceEpisodeList();
        return view;
    }

    private void serviceEpisodeList() {
        ServiceEpisodeList serviceEpisodeList = ServiceFactory.createRetrofitService(ServiceEpisodeList.class, ConstantServices.END_POINT, ConstantServices.SER_NAME_EDPISODE_LIST, getActivity());
        RxBus.getInstance().send(new Events.EventLoadingView(true));

        serviceEpisodeList.resp("1")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseEpisodeListSubscriber());
    }

    @Override
    protected void handlerBus(Object o) {
        super.handlerBus(o);
    }

    private class ResponseEpisodeListSubscriber extends Subscriber<List<Episode>> {
        @Override
        public void onCompleted() {
            // do nothing here
        }

        @Override
        public void onError(Throwable e) {
            RxBus.getInstance().send(new Events.EventLoadingView(false));
        }


        @Override
        public void onNext(List<Episode> response) {
            if (!response.isEmpty()) {
                episodeList.clear();
                episodeList.addAll(response);
                adapterEpisodeList.notifyDataSetChanged();
            }
            RxBus.getInstance().send(new Events.EventLoadingView(false));

        }
    }
}
