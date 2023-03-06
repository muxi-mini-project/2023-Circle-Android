package android.bignerdranch.myapplication.ReusableTools;

import com.google.gson.JsonObject;

public class JsonTool {
    public static String getJsonString(JsonObject jsonObject,String key){
        String s;
        s=jsonObject.getAsJsonPrimitive(key).toString().replaceAll("\"","");
        return s;
    }
}
