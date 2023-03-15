package android.bignerdranch.myapplication.ui.home.PostsDetailsRecyclerView;

import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;

import java.util.Date;
import java.util.UUID;

public class Comment extends BaseItem {
    private String itSelfID;//该评论的ID
    private String publisherID;//发布者id
    private String commenterName;//发布评论者昵称
    private String content;//内容
    private boolean likes;//是否点赞
    private String likesNumber;
    private String commentNumber;
    private String releaseTime;//发布时间
    private String profilePath;

    public Comment() {
        releaseTime = new Date().toString();
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public String getPublisherID() {
        return publisherID;
    }

    public void setPublisherID(String publisherID) {
        this.publisherID = publisherID;
    }

    public int typeCode() {
        return ItemTypeDef.Type.COMMENT.getCode();
    }

    @Override
    public String getProfilePath() {
        return profilePath;
    }

    @Override
    public void setProfilePath(String path) {
        profilePath = path;
    }

    @Override
    public String getID() {
        return itSelfID;
    }

    @Override
    public void setID(String id) {
        itSelfID = id;
    }

    public String getTime() {
        return releaseTime;
    }

    @Override
    public void setTime(String time) {
        releaseTime = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void setTitle(String title) {

    }

    public boolean isLikes() {
        return likes;
    }

    public void setLikes(boolean likes) {
        this.likes = likes;
    }

    @Override
    public String getName() {
        return commenterName;
    }

    public void setName(String commenterName) {
        this.commenterName = commenterName;
    }

    public String getLikesNumber() {
        return likesNumber;
    }

    public void setLikesNumber(String likesNumber) {
        this.likesNumber = likesNumber;
    }

    public String getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(String commentNumber) {
        this.commentNumber = commentNumber;
    }
}
