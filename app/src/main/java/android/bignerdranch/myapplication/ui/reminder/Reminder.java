package android.bignerdranch.myapplication.ui.reminder;

public class Reminder {
    private String mId;                         //通知的编码
    private String mPostID;
    private String mUserID;                     //通知发出者的id
    private String mPersonName;               //存储通知相关的对象
    private String mDate;                       //存储通知时间
    private String mTitle;                  //对应帖子的标题
    private String mProfile;
    private String mContent;

    public Reminder() {
    }


//getter and setter

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getProfile() {
        return mProfile;
    }

    public void setProfile(String profile) {
        mProfile = profile;
    }

    public String getUserID() {
        return mUserID;
    }

    public void setUserID(String userID) {
        mUserID = userID;
    }

    public String getPostID() {
        return mPostID;
    }

    public void setPostID(String postID) {
        mPostID = postID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getPersonName() {
        return mPersonName;
    }

    public void setPersonName(String personName) {
        mPersonName = personName;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }
}
