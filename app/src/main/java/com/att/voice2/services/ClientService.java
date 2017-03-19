package com.att.voice2.services;

import com.att.voice2.exception.RequestException;
/**
 * Created by ebrimatunkara on 3/14/17.
 */

public interface ClientService<R,T> {
    public T sendRequest(R request );
}
