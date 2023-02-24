package android.bignerdranch.myapplication.ui.home.PostsDetailsRecyclerView;

import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.bignerdranch.myapplication.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class PostsDetailsActivity extends BaseActivity {

    private static final String EXTRA_POSTS_PUBLISHER_NAME =
            "com.bignerdranch.android.huaxiaoquan.posts_publisher_name";
    private static final String EXTRA_POSTS_PUBLISH_TIME =
            "com.bignerdranch.android.huaxiaoquan.posts_publish_time";
    private static final String EXTRA_POSTS_CONTENT=
            "com.bignerdranch.android.huaxiaoquan.content";

    private String PostsPublisherName;
    private String PostsPublishTime;
    private String PostsContent;


    public static Intent newIntent(Context packageContext,String publisherName,String publishTime,String content) {
        Intent intent = new Intent(packageContext, PostsDetailsActivity.class);

        intent.putExtra(EXTRA_POSTS_PUBLISHER_NAME,publisherName);
        intent.putExtra(EXTRA_POSTS_PUBLISH_TIME,publishTime);
        intent.putExtra(EXTRA_POSTS_CONTENT,content);

        return intent;
    }

    protected Fragment createFragment() {
        PostsDetailsFragment fragment=new PostsDetailsFragment();
        fragment.setPostsPublisherName(PostsPublisherName);
        fragment.setPostsTime(PostsPublishTime);
        fragment.setPostsContent(PostsContent);

        return fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_details);

        PostsPublisherName=getIntent().getStringExtra(EXTRA_POSTS_PUBLISHER_NAME);
        PostsPublishTime=getIntent().getStringExtra(EXTRA_POSTS_PUBLISH_TIME);
        PostsContent=getIntent().getStringExtra(EXTRA_POSTS_CONTENT);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.posts_details);//此处为layout_home的id
        if (fragment == null) {//即如果posts_fragment为空，创建一个
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.posts_details, fragment)
                    .commit();
        }
    }
}
