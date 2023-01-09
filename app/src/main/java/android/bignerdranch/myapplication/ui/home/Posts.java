
package android.bignerdranch.myapplication.ui.home;

import java.util.Date;
import java.util.UUID;

public class Posts {
    private UUID mId;
    private String publisherName;//发布者的名字
    private boolean follow;//是否关注发布者
    private String content;//发布内容
    private Date releaseTime;//发布时间


    //构造器
    public Posts(String publisherName, boolean follow, String content, Date releaseTime) {
        this.publisherName = publisherName;
        this.follow = follow;
        this.content = content;
        this.releaseTime = releaseTime;
    }
    //空构造器
    public Posts(){
        mId = UUID.randomUUID();
        releaseTime=new Date();
    }

    //以下为自动生成的get和set方法
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
    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;

    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    //get和set方法到此为止

}
