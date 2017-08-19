package com.sheypoor.application.tvmaze;

import com.sheypoor.application.tvmaze.dto.response.episodeList.Episode;
import com.sheypoor.application.tvmaze.service.episodeList.ServiceEpisodeListNo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Query;
import retrofit2.mock.BehaviorDelegate;

/**
 * Created by Banafshe.Zarefar on 18/08/2017.
 */

public class MockServiceEpisodeListSuccess implements ServiceEpisodeListNo {

    private final BehaviorDelegate<ServiceEpisodeListNo> delegate;

    public MockServiceEpisodeListSuccess(BehaviorDelegate<ServiceEpisodeListNo> service) {
        this.delegate = service;
    }

    @Override
    public Call<List<Episode>> respN(@Query("specials") String specials) {
        List<Episode> episodesResponse = new ArrayList<>();
        Episode episode = new Episode();
        episode.setName("Episode Name. (Success)");
        episodesResponse.add(episode);
        return delegate.returningResponse(episodesResponse).respN("1");
    }
}
