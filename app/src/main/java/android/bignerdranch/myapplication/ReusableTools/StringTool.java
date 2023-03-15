package android.bignerdranch.myapplication.ReusableTools;

import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringTool {
    public static String getJsonString(JsonObject jsonObject,String key){
        String s;
        s=jsonObject.getAsJsonPrimitive(key).toString().replaceAll("\"","");
        return s;
    }
    public static String getDateString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = sdf.format(date);
        return dateString;
    }
}
