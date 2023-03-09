package android.bignerdranch.myapplication.ui.home;

import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;

import java.util.UUID;

public class SearchBox extends BaseItem {
    public int typeCode() {
        return ItemTypeDef.Type.SEARCH_BOX.getCode();
    }

    @Override
    public void setLikes(boolean isLike) {

    }

    @Override
    public boolean isLikes() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {
    }

    @Override
    public String getContent() {
        return null;
    }

    @Override
    public void setContent(String content) {
    }

    @Override
    public String getTime() {
        return null;
    }

    @Override
    public void setTime(String time) {
    }

    @Override
    public String getProfilePath() {
        return null;
    }

    @Override
    public void setProfilePath(String path) {

    }

    @Override
    public String getID() {
        return null;
    }

    @Override
    public void setID(String id) {

    }

    @Override
    public String getLikesNumber() {
        return null;
    }

    @Override
    public void setLikesNumber(String likesNumber) {

    }

    @Override
    public String getCommentNumber() {
        return null;
    }

    @Override
    public void setCommentNumber(String commentNumber) {

    }
}
