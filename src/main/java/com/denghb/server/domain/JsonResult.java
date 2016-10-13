package com.denghb.server.domain;

/**
 * Created by denghb on 2016/10/12.
 */
public class JsonResult {
    private int code;
    private String msg;
    private Object result;

    public JsonResult(){

    }

    public JsonResult(String msg){
        this.code = 1;
        this.msg = msg;
        this.result = null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
