package android.bignerdranch.myapplication.ui.home;

import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;

import java.util.UUID;

public class SearchBox extends BaseItem {
    public int typeCode(){
        return ItemTypeDef.Type.SEARCH_BOX.getCode();
    }

    @Override
    public UUID getId() {
        return null;
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
}
