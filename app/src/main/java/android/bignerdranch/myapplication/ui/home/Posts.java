
package android.bignerdranch.myapplication.ui.home;

import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;

import java.util.Date;
import java.util.UUID;

public class Posts extends BaseItem {
    private UUID mId;
    private String publisherName;//发布者的昵称
    private boolean follow;//是否关注发布者
    private String content;//发布内容
    private String releaseTime;//发布时间
    private boolean likes;//是否点赞
    private int likesNumber;//点赞数
    private int commentNumber;//评论数


    public int typeCode(){
        return ItemTypeDef.Type.POSTS.getCode();
    }

    //构造器
    public Posts(String publisherName,String publishTime,String content) {
        this.publisherName = publisherName;
        this.content = content;
        releaseTime=publishTime;
    }


    //空构造器
    public Posts(){
        mId = UUID.randomUUID();
        releaseTime=new Date().toString();
        likesNumber=0;
        commentNumber=0;
    }

    public String getTime(){
        return releaseTime.toString();
    }
    public String getName(){
        return publisherName;
    }

    //以下为自动生成的get和set方法
    public boolean isLikes() {
        return likes;
    }
    public void setLikes(boolean likes) {
        this.likes = likes;
    }
    public UUID getId() {
        return mId;
    }
    public boolean isFollow() {
        return follow;
    }
    public void setFollow(boolean follow) {
        this.follow = follow;
    }
    public String getPublisherName() {
        return publisherName;
    }
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
    public String getReleaseTime() {
        return releaseTime;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    //get和set方法到此为止

}
