package com.att.voice2;

/**
 * Created by ebrimatunkara on 3/14/17.
 */

public interface RequestHandler<R> {
    public void processRequest(R request);
}
