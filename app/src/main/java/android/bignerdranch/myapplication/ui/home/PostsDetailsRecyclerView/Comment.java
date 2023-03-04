package android.bignerdranch.myapplication.ui.home.PostsDetailsRecyclerView;

import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;

import java.util.Date;
import java.util.UUID;

public class Comment extends BaseItem {
    private UUID itSelfID;//该评论的ID
    private String commenterName;//发布评论者昵称
    private String content;//内容
    private boolean likes;//是否点赞
    private int likesNumber;
    private int commentNumber;
    private String releaseTime;//发布时间

    public int typeCode(){
        return ItemTypeDef.Type.COMMENT.getCode();
    }

    public Comment(UUID affiliationPostsID, String commenterName, String content) {
        itSelfID=UUID.randomUUID();
        releaseTime=new Date().toString();

        this.commenterName = commenterName;
        this.content = content;
    }

    public Comment() {
        itSelfID=UUID.randomUUID();
        releaseTime=new Date().toString();
    }

    public String getTime() {
        return releaseTime;
    }

    @Override
    public void setTime(String time) {releaseTime=time;}

    public UUID getId() {
        return itSelfID;
    }

    public String getName() {
        return commenterName;
    }
    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
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
