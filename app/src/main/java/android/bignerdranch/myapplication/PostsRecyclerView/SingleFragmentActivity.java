package android.bignerdranch.myapplication.PostsRecyclerView;

import android.bignerdranch.myapplication.BaseActivity;
import android.bignerdranch.myapplication.R;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class SingleFragmentActivity extends BaseActivity {
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posts_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.posts_fragment);//此处为activity_fragment的id
        if (fragment == null) {//即如果activity_fragment为空，创建一个
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.posts_fragment, fragment)
                    .commit();
        }
    }
}