package android.bignerdranch.myapplication.ReusableTools;

import java.util.UUID;

public abstract class BaseItem {
    private String ID;
    private String Name;//发布者的昵称
    private boolean follow;//是否关注发布者
    private String title;
    private String content;//发布内容
    private String CreateTime;//发布时间
    private String UpdatedTime;//最近一次更新时间
    private boolean likes;//是否点赞
    private String likesNumber;//点赞数
    private String commentNumber;//评论数
    private String profilePath;

    public abstract String getTitle();

    public abstract void setTitle(String title);

    public abstract boolean isLikes();

    public abstract void setLikes(boolean isLike);

    public abstract String getName();

    public abstract void setName(String name);

    public abstract String getContent();

    public abstract void setContent(String content);

    public abstract String getTime();

    public abstract void setTime(String time);

    public abstract String getProfilePath();

    public abstract void setProfilePath(String path);

    public abstract String getID();

    public abstract void setID(String id);

    public abstract String getLikesNumber();

    public abstract void setLikesNumber(String likesNumber);

    public abstract String getCommentNumber();

    public abstract void setCommentNumber(String commentNumber);

    public abstract int typeCode();
}
