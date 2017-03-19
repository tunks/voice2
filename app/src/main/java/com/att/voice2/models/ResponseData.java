package com.att.voice2.models;

import java.io.Serializable;

/**
 * Created by ebrimatunkara on 3/16/17.
 */

public class ResponseData implements Serializable{
    private Object content;
    private String description;
    private StatusCode code;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusCode getCode() {
        return code;
    }

    public void setCode(StatusCode code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "content=" + content +
                ", description='" + description + '\'' +
                ", code=" + code +
                '}';
    }

    public static enum StatusCode{
        STATUS_OK,
        STATUS_FAIL
    }

    public static class ResponseDataBuilder{
        private ResponseData responseData;
        public ResponseDataBuilder() {
            responseData = new ResponseData();
        }

        public ResponseDataBuilder setCode(StatusCode code) {
            this.responseData.setCode(code);
            return this;
        }

        public ResponseDataBuilder setContent(Object content) {
            this.responseData.setContent(content);
            return this;
        }

        public ResponseDataBuilder setDescription(String description) {
            this.responseData.setDescription(description);
            return this;
        }

        public ResponseData build(){
            return responseData;
        }

    }
}
