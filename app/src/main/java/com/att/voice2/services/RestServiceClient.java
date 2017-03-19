package com.att.voice2.services;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import retrofit2.Response;
import com.att.voice2.models.ResponseData;
import com.att.voice2.exception.RequestException;
/**
 * Created by ebrimatunkara on 3/14/17.
 */

public class RestServiceClient implements  ClientService<Map,ResponseData > {
    private SSOTService service;

    public RestServiceClient(SSOTService service) {
        this.service = service;
    }

    public ResponseData  sendRequest(Map request){
        try {
            Response<List> response = service.getData(request).execute();
            return new ResponseData.ResponseDataBuilder()
                                                        .setCode(ResponseData.StatusCode.STATUS_OK)
                                                        .setContent(response.body())
                                                        .setDescription("successful")
                                                        .build();
        }catch (Exception ex) {
            return new ResponseData.ResponseDataBuilder()
                    .setCode(ResponseData.StatusCode.STATUS_FAIL)
                    .setDescription(ex.getMessage())
                    .build();
        }
    }
}
