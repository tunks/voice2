package com.att.voice2;

import org.junit.Before;
import org.junit.Test;

import com.att.voice2.services.ClientService;
import com.att.voice2.services.RestServiceFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.att.voice2.models.ResponseData;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by ebrimatunkara on 3/14/17.
 */

public class RestServiceClientTest {
    private RestServiceFactory serviceFactory;
    private ClientService<Map,ResponseData > clientService;
    private String baseUrl = "http://Ebrimas-MBP.home:8888";
    private Map<String,String> request;

    @Before
    public void setUp() throws Exception {
        serviceFactory = new RestServiceFactory();
        clientService = serviceFactory.createRestServiceClient(baseUrl);
        request = new HashMap<>();
    }

    @Test
    public void test_SendRequest() throws Exception {
        ResponseData results = clientService.sendRequest(request);
        System.out.println("test send request "+results);
        assertNotNull(results);

    }
}
