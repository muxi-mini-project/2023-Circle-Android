package android.bignerdranch.myapplication;

import java.util.Date;

public class Posts {
    private String name;//发布者的名字
    private boolean follow;//是否关注发布者
    private String content;//发布内容
    private Date releaseTime;//发布时间


    //构造器
    public Posts(String name, boolean follow, String content, Date releaseTime) {
        this.name = name;
        this.follow = follow;
        this.content = content;
        this.releaseTime = releaseTime;
    }

    //以下为自动生成的get和set方法
    public boolean isFollow() {
        return follow;
    }
    public void setFollow(boolean follow) {
        this.follow = follow;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
