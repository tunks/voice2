package com.att.voice2.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import java.util.concurrent.TimeUnit;
/**
 * Created by ebrimatunkara on 3/14/17.
 */

public class RestServiceFactory {

    private  static OkHttpClient getHttpClient() {
              OkHttpClient.Builder builder =  new OkHttpClient.Builder()
                                      //.connectTimeout(20, TimeUnit.SECONDS)
                                      .readTimeout(60L, TimeUnit.SECONDS)
                                      .writeTimeout(60L, TimeUnit.SECONDS);

        return builder.build();
    }
    public SSOTService createSSOTClient(String baseUrl){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                 .client(RestServiceFactory.getHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(SSOTService.class);
    }


    public RestServiceClient createRestServiceClient(String baseUrl){
        return new RestServiceClient(createSSOTClient(baseUrl));
    }
}
