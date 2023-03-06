package android.bignerdranch.myapplication.User_Information_Edit;

import android.net.Uri;

//创建一个在此包内唯一的imageview的Uri，给头像的图片使用
public class Profile_picture_Uri {

    private Uri mUri;

    private Profile_picture_Uri(){
        mUri=null;
    }

    private static Uri Profile_picture_Uri=new Profile_picture_Uri().mUri;

    public static Uri getProfile_picture_Uri() {
        return Profile_picture_Uri;
    }

    public static void setProfile_picture_Uri(Uri uri) {
        Profile_picture_Uri = uri;
    }
}
