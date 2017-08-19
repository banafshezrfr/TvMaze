package com.sheypoor.application.tvmaze;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sheypoor.application.tvmaze.dto.response.episodeList.Episode;
import com.sheypoor.application.tvmaze.service.episodeList.ServiceEpisodeListNo;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Query;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.Calls;

/**
 * Created by Banafshe.Zarefar on 18/08/2017.
 */

public class MockServiceEpisodeListFailed implements ServiceEpisodeListNo {

    private final BehaviorDelegate<ServiceEpisodeListNo> delegate;

    public MockServiceEpisodeListFailed(BehaviorDelegate<ServiceEpisodeListNo> service) {
        this.delegate = service;
    }

    @Override
    public Call<List<Episode>> respN(@Query("specials") String specials) {
        Error error = new Error();
        error.setCode(0);
        error.setStatus(404);
        error.setMessage("Page not found.");
        EpisodeListErrorRespose errorRespose = new EpisodeListErrorRespose();
        errorRespose.setError(error);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(errorRespose);
            Response response = Response.error(404, ResponseBody.create(MediaType.parse("application/json") ,json));
            return delegate.returning(Calls.response(response)).respN("1");
        } catch (JsonProcessingException e) {
            Log.e("Error ", "JSON Processing exception:",e);
            return  Calls.failure(e);
        }

    }
}
