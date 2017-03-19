package com.att.voice2.services;

import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Body;
import retrofit2.http.QueryMap;
import retrofit2.Call;
import java.util.Map;
import  java.util.List;

/**
 * Created by ebrimatunkara on 3/14/17.
 */

public interface SSOTService {
    @POST("/tickets")
    public Call<List> postData(@Body String cypher);

    @GET("/tickets")
    public Call<List> getData(@QueryMap Map<String, String> options);

}
