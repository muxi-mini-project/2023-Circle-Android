package android.bignerdranch.myapplication.ui.mine.UserListAbout.SearchUser;

import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.bignerdranch.myapplication.ui.home.PostsDetailsRecyclerView.PostsDetailsFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class SearchUserActivity extends BaseActivity {

    private static final String EXTRA_QUERY=
            "com.bignerdranch.android.huaxiaoquan.query";

    private String mQuery;

    protected Fragment createFragment() {
        SearchUserFragment fragment = new SearchUserFragment();
        fragment.setQuery(mQuery);

        return fragment;
    }

    public static Intent newIntent(Context packageContext,String query) {
        Intent intent = new Intent(packageContext, SearchUserActivity.class);
        intent.putExtra(EXTRA_QUERY,query);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user);

        mQuery=getIntent().getStringExtra(EXTRA_QUERY);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.layout_user);

        if (fragment == null) {
            fragment = createFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.layout_user, fragment)
                    .commit();
        }
    }
}
