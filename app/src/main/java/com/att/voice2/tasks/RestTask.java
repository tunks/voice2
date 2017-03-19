package com.att.voice2.tasks;

/**
 * Created by ebrimatunkara on 3/14/17.
 */
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import com.att.voice2.services.ClientService;
import com.att.voice2.exception.RequestException;
import com.att.voice2.models.ResponseData;
import java.io.Serializable;


public class RestTask extends AsyncTask<Map, Void, ResponseData > {
    public static final String HTTP_RESPONSE = "HTTP_RESPONSE";
    private Context mContext;
    private ClientService<Map,ResponseData > service;
    private String mAction;

    public RestTask(Context mContext, String mAction, ClientService<Map, ResponseData> service) {
        this.mContext = mContext;
        this.mAction = mAction;
        this.service = service;
    }

    @Override
    protected ResponseData doInBackground(Map... params)
    {
            Map request = params[0];
            return service.sendRequest(request);

    }

    @Override
    protected void onPostExecute(ResponseData result)
    {
        Intent intent = new Intent(mAction);
        intent.putExtra(HTTP_RESPONSE,(Serializable)result);
        // broadcast the completion
        mContext.sendBroadcast(intent);
        System.out.println("broadcast intent message :"+result);

    }




}
