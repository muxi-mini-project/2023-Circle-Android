package android.bignerdranch.myapplication.ui.mine;

import android.bignerdranch.myapplication.User_Information_Edit.User_Information;
import android.bignerdranch.myapplication.ui.mine.MyPosts;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MyPostsLab {
    private static MyPostsLab sMyPostsLab;
    private List<MyPosts> mMyPosts;

    public static MyPostsLab get(){
        if (sMyPostsLab==null){
            sMyPostsLab=new MyPostsLab();
        }
        return sMyPostsLab;
    }

    private MyPostsLab(){
        mMyPosts=new ArrayList<>();
        for (int i=0;i<20;i++){
            MyPosts posts=new MyPosts();
            posts.setContent("第"+(i+1)+"次测试");
            mMyPosts.add(posts);
        }
    }

    public List<MyPosts> get_mPosts(){return mMyPosts;}

    public MyPosts getPosts(UUID id){
        for (MyPosts e:mMyPosts){
            if (e.getId().equals(id))
                return e;
        }
        return null;
    }
}
