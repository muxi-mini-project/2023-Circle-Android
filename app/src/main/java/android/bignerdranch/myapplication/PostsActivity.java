package android.bignerdranch.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class PostsActivity extends BaseActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, PostsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posts_layout);
    }
}
