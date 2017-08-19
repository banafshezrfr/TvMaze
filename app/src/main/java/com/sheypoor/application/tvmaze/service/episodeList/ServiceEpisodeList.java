package com.sheypoor.application.tvmaze.service.episodeList;

import com.sheypoor.application.tvmaze.dto.response.episodeList.Episode;
import com.sheypoor.application.tvmaze.util.ConstantServices;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Banafshe.Zarefar on 15/08/2017.
 */

public interface ServiceEpisodeList {

    @GET(ConstantServices.SER_NAME_EDPISODE_LIST)
    Observable<List<Episode>> resp(@Query(ConstantServices.SPECIALS) String specials);
}
