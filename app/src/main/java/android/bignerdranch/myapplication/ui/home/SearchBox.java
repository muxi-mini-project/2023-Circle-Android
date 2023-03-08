package android.bignerdranch.myapplication.ui.home;

import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;

import java.util.UUID;

public class SearchBox extends BaseItem {
    public int typeCode(){
        return ItemTypeDef.Type.SEARCH_BOX.getCode();
    }

    @Override
    public String getName() {
        return null;
    }
    @Override
    public String getContent() {
        return null;
    }
    @Override
    public String getTime() {
        return null;
    }
    @Override
    public String getProfilePath() {
        return null;
    }
    @Override
    public String getID() {
        return null;
    }



    @Override
    public void setID(String id) {

    }
    @Override
    public void setProfilePath(String path) {

    }
    @Override
    public void setTime(String time) {}
    @Override
    public void setContent(String content) {}
    @Override
    public void setName(String name){}
}
