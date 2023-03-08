package android.bignerdranch.myapplication.ui.home.PostsDetailsRecyclerView;

import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;

import java.util.Date;
import java.util.UUID;

public class Comment extends BaseItem {
    private String itSelfID;//该评论的ID
    private String commenterName;//发布评论者昵称
    private String content;//内容
    private boolean likes;//是否点赞
    private int likesNumber;
    private int commentNumber;
    private String releaseTime;//发布时间
    private String profilePath;

    public int typeCode(){
        return ItemTypeDef.Type.COMMENT.getCode();
    }


    public Comment() {
        releaseTime=new Date().toString();
    }



    @Override
    public String getProfilePath() {
        return profilePath;
    }

    @Override
    public String getID() {
        return itSelfID;
    }

    @Override
    public void setID(String id) {
        itSelfID=id;
    }

    @Override
    public void setProfilePath(String path) {
        profilePath=path;
    }
    @Override
    public void setTime(String time) {releaseTime=time;}

    public String getTime() {
        return releaseTime;
    }



    public String getId() {
        return itSelfID;
    }
    public String getName() {
        return commenterName;
    }
    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }
    public void setName(String commenterName) {
        this.commenterName = commenterName;
    }
    public boolean isLikes() {
        return likes;
    }
    public void setLikes(boolean likes) {
        this.likes = likes;
    }

    public int getLikesNumber() {
        return likesNumber;
    }
    public void setLikesNumber(int likesNumber) {
        this.likesNumber = likesNumber;
    }

    public int getCommentNumber() {
        return commentNumber;
    }
    public void setCommentNumber(int commentNumber) {
        this.commentNumber = commentNumber;
    }
}
