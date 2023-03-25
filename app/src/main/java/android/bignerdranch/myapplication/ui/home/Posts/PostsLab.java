package android.bignerdranch.myapplication.ui.home.Posts;

import java.util.ArrayList;
import java.util.List;

public class PostsLab {

    private List<Posts> mPostsList;

    public PostsLab(List<Posts> mPosts) {
        mPostsList = mPosts;
    }


    //创建一个初始的默认帖子 防止闪退 否则在网络请求还没有接收到返回时加载帖子界面会导致null闪退
    public static PostsLab get(int dataLength) {
        List<Posts> mPosts = new ArrayList<>();
        for (int i=0;i<dataLength;i++) {
            Posts posts = new Posts();
            posts.setContent("");
            posts.setName("用户名");
            mPosts.add(posts);
        }
        return new PostsLab(mPosts);
    }


    public List<Posts> getPosts() {
        return mPostsList;
    }
}
