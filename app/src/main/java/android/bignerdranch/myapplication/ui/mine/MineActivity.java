package android.bignerdranch.myapplication.ui.mine;

import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class MineActivity extends BaseActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, MineActivity.class);
        return intent;
    }

    protected Fragment createFragment() {
        return new MineFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mine);

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
