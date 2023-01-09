package android.bignerdranch.myapplication.ui.mine;

import java.util.Date;
import java.util.UUID;

public class MyPosts {
    private UUID mId;
    private String publisherName;//发布者的名字
    private String content;//发布内容
    private Date releaseTime;//发布时间

    public MyPosts() {
        mId = UUID.randomUUID();
        releaseTime=new Date();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }
}
