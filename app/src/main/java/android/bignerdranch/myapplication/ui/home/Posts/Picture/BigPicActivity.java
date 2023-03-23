package android.bignerdranch.myapplication.ui.home.Posts.Picture;

import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.bignerdranch.myapplication.ui.mine.UserListAbout.Fans.FansActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;

import uk.co.senab.photoview.PhotoView;

public class BigPicActivity extends BaseActivity {
    private static final String EXTRA_PIC_PATH =
            "com.bignerdranch.android.huaxiaoquan.pic_path";
    private String mPicPath;

    private PhotoView mPhotoView;

    public static Intent newIntent(Context packageContext, String picPath) {
        Intent intent = new Intent(packageContext, BigPicActivity.class);
        intent.putExtra(EXTRA_PIC_PATH, picPath);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_dialog);

        mPicPath=getIntent().getStringExtra(EXTRA_PIC_PATH);

        mPhotoView=findViewById(R.id.photo);
        Glide.with(BigPicActivity.this)
                .load("http://"+mPicPath)
                .into(mPhotoView);
    }

}
