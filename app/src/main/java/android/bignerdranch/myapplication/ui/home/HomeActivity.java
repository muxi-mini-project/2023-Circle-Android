package android.bignerdranch.myapplication.ui.home;

import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.bignerdranch.myapplication.R;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class HomeActivity extends BaseActivity {

    private static final String EXTRA_POSTS_DATA=
            "com.bignerdranch.android.huaxiaoquan.data";

    private String[] mData;

    protected Fragment createFragment() {
        HomeFragment homeFragment=new HomeFragment();
        homeFragment.setData(mData);
        return homeFragment;
    }

    public void setData(String[] data){
        mData=data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

        mData=getIntent().getStringArrayExtra(EXTRA_POSTS_DATA);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.layout_home);//此处为layout_home的id
        if (fragment == null) {//即如果posts_fragment为空，创建一个
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.layout_home, fragment)
                    .commit();
        }


    }

    public static Intent newIntent(Context packageContext,String[] data) {
        Intent intent = new Intent(packageContext, HomeActivity.class);

        intent.putExtra(EXTRA_POSTS_DATA,data);
        return intent;
    }

    public  void saveToken(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public  String getMyToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "false");
        return token;
    }
}
