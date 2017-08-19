package com.sheypoor.application.tvmaze.service.factory;

/**
 * Created by banafshe.zarefar on 2016-12-01.
 */

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.sheypoor.application.tvmaze.service.exceptionHandling.RxErrorHandlingCallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceFactory {
    /**
     * Creates a retrofit service from an arbitrary class (clazz)
     *
     * @param clazz    Java interface of the retrofit service
     * @param endPoint REST endpoint url
     * @return retrofit service with defined endpoint
     */
    public static <T> T createRetrofitService(final Class<T> clazz, final String endPoint, String serviceName, final Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .baseUrl(endPoint)
                .client(getClient(serviceName))
                .build();
        T service = retrofit.create(clazz);
        return service;
    }

    private static OkHttpClient getClient(String whichService) { // brokers
        OkHttpClient okClient = null;
        final Response[] responseTotal = {null};
        okClient = new OkHttpClient.Builder().readTimeout(30000, TimeUnit.MILLISECONDS).connectTimeout(30000, TimeUnit.MILLISECONDS)
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) {
                                Request original = chain.request();
                                // Request customization: add request headers
                                Request.Builder requestBuilder = original.newBuilder()
//                                        .addHeader("Content-Type", "application/json")
                                        .method(original.method(), original.body());

                                Request request = requestBuilder.build();
                                Response response = null;
                                try {
                                    response = chain.proceed(request);
                                } catch (IOException e) {
                                    Logger.d(e.getMessage().toString());
                                    if (e.getMessage().toString().contains("Too many follow-up requests")) {
                                    } else {
                                    }
                                }
                                responseTotal[0] = response;
                                return responseTotal[0];
                            }
                        })
                .build();
        try {
            if (null != responseTotal[0]) {
                responseTotal[0].body().string();
                responseTotal[0].body().close();
            }
        } catch (Exception e) {
            Logger.d(e.getMessage().toString());
        }
        return okClient;
    }
}
