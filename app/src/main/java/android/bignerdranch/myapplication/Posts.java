package android.bignerdranch.myapplication;

import java.util.Date;

public class Posts {
<<<<<<< HEAD
    //帖子类
    private String name;//发布者的名字
    private boolean follow;//你是否关注发布者
    private String content;//帖子的文本内容
    private Date ReleaseTime;//帖子的发布时间（可能不是用String）
=======
    private String name;//发布者的名字
    private boolean follow;//是否关注发布者
    private String content;//发布内容
    private Date releaseTime;
>>>>>>> liz

    //构造器
    public Posts(String name, boolean follow, String content, Date releaseTime) {
        this.name = name;
        this.follow = follow;
        this.content = content;
<<<<<<< HEAD
        ReleaseTime = releaseTime;
    }

    //以下一长段都是自动生成的get和set方法
=======
        this.releaseTime = releaseTime;
    }

    //以下为自动生成的get和set方法
    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

>>>>>>> liz
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

<<<<<<< HEAD
    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
=======
    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
>>>>>>> liz
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
<<<<<<< HEAD

    public Date getReleaseTime() {
        return ReleaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        ReleaseTime = releaseTime;
    }
    //get和set方法到此处为止

=======
    //get和set方法到此为止
>>>>>>> liz
}
