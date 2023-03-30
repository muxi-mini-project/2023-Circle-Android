package android.bignerdranch.myapplication.ui.home.PostsDetailsRecyclerView;

import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.bignerdranch.myapplication.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class PostsDetailsActivity extends BaseActivity {


    private static final String EXTRA_POSTS_ID=
            "com.bignerdranch.android.huaxiaoquan.id";

    private String postsId;



    public static Intent newIntent(Context packageContext,String postsId) {
        Intent intent = new Intent(packageContext, PostsDetailsActivity.class);
        intent.putExtra(EXTRA_POSTS_ID,postsId);
        return intent;
    }

    protected Fragment createFragment() {
        PostsDetailsFragment fragment = new PostsDetailsFragment();
        fragment.setPostsID(postsId);

        return fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_post_details);
        View root = findViewById(R.id.posts_details);
        root.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        postsId=getIntent().getStringExtra(EXTRA_POSTS_ID);

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
