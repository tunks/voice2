package com.att.voice2.exception;

/**
 * Created by ebrimatunkara on 3/15/17.
 */

public class RequestException extends RuntimeException {
    public RequestException() {
    }

    public RequestException(String message) {
        super(message);
    }

    public RequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
