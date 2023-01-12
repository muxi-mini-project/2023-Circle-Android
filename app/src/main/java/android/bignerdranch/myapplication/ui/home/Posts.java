
package android.bignerdranch.myapplication.ui.home;

import android.bignerdranch.myapplication.ReusableTools.BaseItem;

import java.util.Date;
import java.util.UUID;

public class Posts extends BaseItem {
    private UUID mId;
    private String publisherName;//发布者的名字
    private boolean follow;//是否关注发布者
    private String content;//发布内容
    private Date releaseTime;//发布时间
    private boolean likes;//是否点赞


    public int typeCode(){
        return ItemTypeDef.Type.TWO_TEXT.getCode();
    }

    //构造器
    public Posts(String publisherName, boolean follow, String content, boolean likes) {
        mId=UUID.randomUUID();
        this.publisherName = publisherName;
        this.follow = follow;
        this.content = content;
        releaseTime=new Date();
        this.likes = likes;
    }

    //空构造器
    public Posts(){
        mId = UUID.randomUUID();
        releaseTime=new Date();
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
    public Date getReleaseTime() {
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
