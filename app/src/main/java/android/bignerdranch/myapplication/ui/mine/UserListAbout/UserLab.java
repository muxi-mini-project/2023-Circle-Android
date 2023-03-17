package android.bignerdranch.myapplication.ui.mine.UserListAbout;

import android.bignerdranch.myapplication.User_Information_Edit.User_Information;

import java.util.ArrayList;
import java.util.List;

public class UserLab {

    private List<User_Information> mUsers;


    private UserLab(List<User_Information> users) {
        mUsers = users;
    }

    public static UserLab get(int dataLength) {
        List<User_Information> users = new ArrayList<>();
        for (int i = 0; i < dataLength; i++) {
            User_Information user = new User_Information();
            user.setUser_Name("用户名");
            user.setSignature("个性签名");
            users.add(user);
        }
        return new UserLab(users);
    }

    public List<User_Information> getUsers() {
        return mUsers;
    }
}
