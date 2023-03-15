package android.bignerdranch.myapplication.ui.mine.UserListAbout.Follower;

import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FollowerActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user);

        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment=fragmentManager.findFragmentById(R.id.layout_user);

        if(fragment==null){
            fragment=new FollowerFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.layout_user,fragment)
                    .commit();
        }
    }
    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, FollowerActivity.class);
        return intent;
    }
}
