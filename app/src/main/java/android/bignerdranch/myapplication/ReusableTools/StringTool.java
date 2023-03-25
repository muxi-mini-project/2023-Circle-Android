package android.bignerdranch.myapplication.ReusableTools;

import android.util.Log;

import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringTool {
    public static String getJsonString(JsonObject jsonObject,String key){
        String s;
        if (jsonObject.getAsJsonPrimitive(key)!=null){
            s=jsonObject.getAsJsonPrimitive(key).toString().replaceAll("\"","").replaceAll("\\\\n","\n");
        }else {
            s="";
        }
        return s;
    }

    public static String getDateString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = sdf.format(date);
        return dateString;
    }
}
