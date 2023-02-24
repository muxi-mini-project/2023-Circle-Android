package android.bignerdranch.myapplication.ui.home;

import android.bignerdranch.myapplication.NavigationBarActivity;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.SignInActivity;
import android.bignerdranch.myapplication.ui.home.NewPosts.NewPostsActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class HomeActivity extends BaseActivity {

    ImageButton newPostsBtn;

    protected Fragment createFragment() {
        return new HomeFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.layout_home);//此处为layout_home的id
        if (fragment == null) {//即如果posts_fragment为空，创建一个
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.layout_home, fragment)
                    .commit();
        }


    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, HomeActivity.class);
        return intent;
    }

}
