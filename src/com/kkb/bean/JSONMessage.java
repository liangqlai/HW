package com.kkb.bean;

import com.alibaba.fastjson.JSONObject;

public class JSONMessage {
    private int status;
    private String message;
    private Object data;

    public JSONMessage() {
    }

    public JSONMessage(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public JSONMessage(int status) {
        this.status = status;
    }

    public JSONMessage(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String toJSON() {
        return JSONObject.toJSONString(this);
    }
}
