package com.sheypoor.application.tvmaze.service.episodeList;

import com.sheypoor.application.tvmaze.dto.response.episodeList.Episode;
import com.sheypoor.application.tvmaze.util.ConstantServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Banafshe.Zarefar on 15/08/2017.
 */

public interface ServiceEpisodeListNo {
    @GET(ConstantServices.SER_NAME_EDPISODE_LIST)
    Call<List<Episode>> respN(@Query(ConstantServices.SPECIALS) String specials);
}
