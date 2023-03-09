package android.bignerdranch.myapplication.ui.home;

import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;

import java.util.Date;
import java.util.UUID;

public class Posts extends BaseItem {
    private String PostsId;
    private String Name;//发布者的昵称
    private boolean follow;//是否关注发布者
    private String content;//发布内容
    private String CreateTime;//发布时间
    private String UpdatedTime;//最近一次更新时间
    private boolean likes;//是否点赞
    private String likesNumber;//点赞数
    private String commentNumber;//评论数
    private String profilePath;


    //构造器
    public Posts(String publisherName, String publishTime, String content) {
        this.Name = publisherName;
        this.content = content;
        CreateTime = publishTime;
    }

    //空构造器
    public Posts() {
        CreateTime = new Date().toString();
        UpdatedTime = CreateTime;
        likesNumber = "0";
        commentNumber = "0";
    }

    public int typeCode() {
        return ItemTypeDef.Type.POSTS.getCode();
    }

    public String getTime() {
        return UpdatedTime;
    }

    public void setTime(String time) {
        this.UpdatedTime = time;
    }



    public String getName() {
        return Name;
    }

    public void setName(String publisherName) {
        this.Name = publisherName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProfilePath() {
        return profilePath;
    }

    @Override
    public void setProfilePath(String path) {
        profilePath = path;
    }

    @Override
    public String getID() {
        return PostsId;
    }

    @Override
    public void setID(String id) {
        PostsId = id;
    }

    @Override
    public String getLikesNumber() {
        return likesNumber;
    }

    @Override
    public void setLikesNumber(String number) {
        likesNumber = number;
    }

    @Override
    public String getCommentNumber() {
        return commentNumber;
    }

    @Override
    public void setCommentNumber(String number) {
        commentNumber = number;
    }

    public boolean isLikes() {
        return likes;
    }

    public void setLikes(boolean likes) {
        this.likes = likes;
    }

    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }


}
