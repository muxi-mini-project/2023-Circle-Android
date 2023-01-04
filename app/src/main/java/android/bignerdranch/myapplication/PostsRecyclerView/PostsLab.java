package android.bignerdranch.myapplication.PostsRecyclerView;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PostsLab {
    private static PostsLab sPostsLab;

    private List<Posts> mPosts;

    public static PostsLab get(){
        if (sPostsLab==null){
            sPostsLab=new PostsLab();
        }
        return sPostsLab;
    }

    private PostsLab(){
        mPosts=new ArrayList<>();
        for (int i=0;i<10;i++){
            Posts posts=new Posts();
            posts.setName("复读机"+(i+1)+"号");
            posts.setContent("第"+(i+1)+"次测试");
            if ((i%2)==0){
                posts.setFollow(true);
            }
            mPosts.add(posts);
        }
    }

    public List<Posts> get_mPosts(){return mPosts;}

    public Posts getPosts(UUID id){
        for (Posts e:mPosts){
            if (e.getId().equals(id))
                return e;
        }
        return null;
    }
}
