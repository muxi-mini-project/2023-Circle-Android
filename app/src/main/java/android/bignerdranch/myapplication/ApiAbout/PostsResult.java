package android.bignerdranch.myapplication.ApiAbout;

import com.google.gson.JsonObject;

public class PostsResult {
    private int code;
    private String msg;
    private JsonObject data;

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

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }
}
