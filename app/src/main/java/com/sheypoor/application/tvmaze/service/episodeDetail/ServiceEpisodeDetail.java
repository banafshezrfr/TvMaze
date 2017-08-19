package com.sheypoor.application.tvmaze.service.episodeDetail;

import com.sheypoor.application.tvmaze.dto.response.episodeList.Episode;
import com.sheypoor.application.tvmaze.util.ConstantServices;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Banafshe.Zarefar on 15/08/2017.
 */

public interface ServiceEpisodeDetail {

    @GET(ConstantServices.SER_NAME_EDPISODE_DETAIL)
    Observable<Episode> resp(@Query(ConstantServices.SEASON) Long season, @Query(ConstantServices.NUMBER) Long number);
}
