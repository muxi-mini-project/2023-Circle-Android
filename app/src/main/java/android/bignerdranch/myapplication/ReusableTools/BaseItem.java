package android.bignerdranch.myapplication.ReusableTools;

import java.util.UUID;

public abstract class BaseItem {
    private String ID;
    private String Name;//发布者的昵称
    private boolean follow;//是否关注发布者
    private String content;//发布内容
    private String CreateTime;//发布时间
    private String UpdatedTime;//最近一次更新时间
    private boolean likes;//是否点赞
    private int likesNumber;//点赞数
    private int commentNumber;//评论数
    private String profilePath;

    public abstract String getName();
    public abstract String getContent();
    public abstract String getTime();
    public abstract String getProfilePath();
    public abstract String getID();

    public abstract void setID(String id);
    public abstract void setProfilePath(String path);
    public abstract void setName(String name);
    public abstract void setTime(String time);
    public abstract void setContent(String content);

    public abstract int typeCode();
}
