package android.bignerdranch.myapplication.ApiAbout;

public class SimpleResult {
    private int code;
    private String msg;
    private String token;
    private String[] data;


    public String getMsg() {
        return msg;
    }
    public String getToken() {
        return token;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }
}
