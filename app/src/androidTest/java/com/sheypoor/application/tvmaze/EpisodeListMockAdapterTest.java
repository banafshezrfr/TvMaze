package com.sheypoor.application.tvmaze;

import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.SmallTest;


import com.sheypoor.application.tvmaze.dto.response.episodeList.Episode;
import com.sheypoor.application.tvmaze.service.episodeList.ServiceEpisodeListNo;

import junit.framework.Assert;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

/**
 * Created by Banafshe.Zarefar on 18/08/2017.
 */

public class EpisodeListMockAdapterTest extends InstrumentationTestCase {
    private MockRetrofit mockRetrofit;
    private Retrofit retrofit;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        retrofit = new Retrofit.Builder().baseUrl("http://test.com")
                .client(new OkHttpClient())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        NetworkBehavior behavior = NetworkBehavior.create();

        mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();
    }

    @SmallTest
    public void testRandomEpisodeRetrieval() throws Exception {
        BehaviorDelegate<ServiceEpisodeListNo> delegate = mockRetrofit.create(ServiceEpisodeListNo.class);
        ServiceEpisodeListNo serviceEpisodeList = new MockServiceEpisodeListSuccess(delegate);

        //Actual Test
        Call<List<Episode>> episodeList = serviceEpisodeList.respN("1");
        List<Episode> respose = episodeList.execute().body();

        //Asserting response
        Assert.assertTrue(!respose.get(0).getName().isEmpty());
        Assert.assertEquals("Episode Name. (Success)", respose.get(0).getName());

    }
    @SmallTest
    public void testFailedEpisodeRetrieval() throws Exception {
        BehaviorDelegate<ServiceEpisodeListNo> delegate = mockRetrofit.create(ServiceEpisodeListNo.class);
        MockServiceEpisodeListFailed episodeListService = new MockServiceEpisodeListFailed(delegate);

        //Actual Test
        Call<List<Episode>> episodeList = episodeListService.respN("1");
        List<Episode> respose = episodeList.execute().body();
        Assert.assertFalse(null != respose);

//        Converter<ResponseBody, EpisodeListErrorRespose> errorConverter = retrofit.responseBodyConverter(EpisodeListErrorRespose.class, new Annotation[0]);
//        EpisodeListErrorRespose error = errorConverter.convert(episodeList.execute().errorBody());

        //Asserting response
        Assert.assertEquals(null, respose);//error.getError().getStatus().intValue());
//        Assert.assertEquals("Episode Not Found", episodeList.execute().errorBody());

    }
}
