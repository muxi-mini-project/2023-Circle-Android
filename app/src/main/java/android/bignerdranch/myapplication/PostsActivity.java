package android.bignerdranch.myapplication;

import android.bignerdranch.myapplication.PostsRecyclerView.Posts;
import android.bignerdranch.myapplication.PostsRecyclerView.PostsAdapter;
import android.bignerdranch.myapplication.PostsRecyclerView.PostsLab;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class PostsActivity extends BaseActivity {

    private RecyclerView mPostsRecyclerView;
    private PostsAdapter mPostsAdapter;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, PostsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posts_recyclerview);
        upDateUI();

    }

    private void upDateUI() {
        PostsLab postsLab = PostsLab.get();
        List<Posts> postsList = postsLab.get_mPosts();

        mPostsRecyclerView = (RecyclerView) findViewById(R.id.posts_recyclerview);
        mPostsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPostsAdapter = new PostsAdapter(postsList);
        mPostsRecyclerView.setAdapter(mPostsAdapter);
    }
}
