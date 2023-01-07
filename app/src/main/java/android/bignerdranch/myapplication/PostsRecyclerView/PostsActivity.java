package android.bignerdranch.myapplication.PostsRecyclerView;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;


public class PostsActivity extends SingleFragmentActivity {
    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, PostsActivity.class);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        return new PostsFragment();
    }
}
