package android.bignerdranch.myapplication.ui.home;

import java.util.ArrayList;
import java.util.List;

public class PostsLab {

    private List<Posts> mPostsList;

    public PostsLab(List<Posts> mPosts) {
        mPostsList = mPosts;
    }

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
