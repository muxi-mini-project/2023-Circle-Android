package android.bignerdranch.myapplication.ui.mine.UserListAbout.Fans;

import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.bignerdranch.myapplication.ui.mine.UserListAbout.Follower.FollowerFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FansActivity extends BaseActivity {

    private static final String EXTRA_USER_ID =
            "com.bignerdranch.android.huaxiaoquan.user_id";
    private String mUserId;

    public static Intent newIntent(Context packageContext,String userId) {
        Intent intent = new Intent(packageContext, FansActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user);

        mUserId = getIntent().getStringExtra(EXTRA_USER_ID);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.layout_user);

        if (fragment == null) {
            fragment = createFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.layout_user, fragment)
                    .commit();
        }
    }
    private Fragment createFragment() {
        FansFragment fragment = new FansFragment();
        fragment.setUserId(mUserId);
        return fragment;
    }
}
