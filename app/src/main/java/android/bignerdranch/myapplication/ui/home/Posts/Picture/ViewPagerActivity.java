package android.bignerdranch.myapplication.ui.home.Posts.Picture;

import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.bignerdranch.myapplication.ui.home.Posts.Posts;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class ViewPagerActivity extends BaseActivity {

    private static final String EXTRA_PIC_PATHS =
            "com.bignerdranch.android.huaxiaoquan.pic_paths";

    private static final String EXTRA_POSITION =
            "com.bignerdranch.android.huaxiaoquan.position";

    private ViewPager mViewPager;
    private String[] mPicPaths;

    public static Intent newIntent(Context packageContext, String[] picPaths) {
        Intent intent = new Intent(packageContext, ViewPagerActivity.class);
        intent.putExtra(EXTRA_PIC_PATHS, picPaths);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_layout);

        mPicPaths=getIntent().getStringArrayExtra(EXTRA_PIC_PATHS);


        mViewPager=(ViewPager) findViewById(R.id.view_pager_layout);

        FragmentManager fragmentManager=getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                BigPicFragment fragment=new BigPicFragment();
                fragment.setPath(mPicPaths[position]);
                return fragment;
            }

            @Override
            public int getCount() {
                return mPicPaths.length;
            }
        });
    }
}
