package android.bignerdranch.myapplication.ui.home.PersonalPage;

import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class PersonalPageActivity extends BaseActivity {

    private static final String EXTRA_USER_ID =
            "com.bignerdranch.android.huaxiaoquan.user_id";

    private String mUserid;


    public static Intent newIntent(Context packageContext, String userid) {
        Intent intent = new Intent(packageContext, PersonalPageActivity.class);
        intent.putExtra(EXTRA_USER_ID, userid);
        return intent;
    }

    protected Fragment createFragment() {
        PersonalPageFragment fragment = new PersonalPageFragment();
        fragment.setUserId(mUserid);
        return (Fragment) fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mine);

        mUserid = getIntent().getStringExtra(EXTRA_USER_ID);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.layout_mine);//此处为layout_home的id
        if (fragment == null) {//即如果posts_fragment为空，创建一个
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.layout_mine, fragment)
                    .commit();
        }
    }
}
