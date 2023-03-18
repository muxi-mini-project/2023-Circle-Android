package android.bignerdranch.myapplication.ui.home.Posts;

import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Posts extends BaseItem {
    private final String CreateTime;//发布时间
    private String publisherId;//发布者id
    private String PostsId;
    private String Name;//发布者的昵称
    private boolean follow;//是否关注发布者
    private String title;
    private String content;//发布内容
    private String UpdatedTime;//最近一次更新时间
    private boolean likes;//是否点赞
    private String likesNumber;//点赞数
    private String commentNumber;//评论数
    private String profilePath;
    private List<String> picPaths;

    //空构造器
    public Posts() {
        picPaths = new ArrayList<>();
        CreateTime = new Date().toString();
        UpdatedTime = CreateTime;
    }

    public List<String> getPicPaths() {
        return picPaths;
    }

    public void setPicPaths(List<String> picPaths) {
        this.picPaths = picPaths;
    }

    public void addPicPath(String picPath) {
        picPaths.add(picPath);
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

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
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

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
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
