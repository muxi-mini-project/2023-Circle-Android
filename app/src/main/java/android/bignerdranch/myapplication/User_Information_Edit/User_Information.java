package android.bignerdranch.myapplication.User_Information_Edit;

import android.bignerdranch.myapplication.UserSex;

public class User_Information {
    private String mUser_Name_Title;
    private String mUser_Name;
    private UserSex mUserSex;
    private String mSignature;
    private int mFans;
    private int mFollow;
    private int mDynamics;

    private User_Information(){
        //初始化,等到可以网络请求的时候应该要修改
        mUser_Name_Title="显示用户名";
        mUser_Name="默认用户名";
        mUserSex= UserSex.Unselected;
    }

    private static User_Information mUser_information = new User_Information();

    public  static User_Information getUser_information(){
        return  mUser_information;
    }


    public UserSex getUserSex() {
        return mUserSex;
    }

    public void setUserSex(UserSex userSex) {
        mUserSex = userSex;
    }

    public int getFans() {
        return mFans;
    }

    public void setFans(int fans) {
        mFans = fans;
    }

    public int getFollow() {
        return mFollow;
    }

    public void setFollow(int follow) {
        mFollow = follow;
    }

    public int getDynamics() {
        return mDynamics;
    }

    public void setDynamics(int dynamics) {
        mDynamics = dynamics;
    }

    public String getUser_Name() {
        return mUser_Name;
    }

    public void setUser_Name(String name) {
        mUser_Name = name;
    }

    public String getSignature() {
        return mSignature;
    }

    public void setSignature(String signature) {
        mSignature = signature;
    }

    public String getUser_Name_Title() {
        return mUser_Name_Title;
    }

    public void setUser_Name_Title(String user_Name_Title) {
        mUser_Name_Title = user_Name_Title;
    }
}
