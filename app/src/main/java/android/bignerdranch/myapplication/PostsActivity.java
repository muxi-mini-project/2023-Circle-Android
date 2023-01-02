package android.bignerdranch.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class PostsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posts_layout);}//表示启动时将posts_layout作为视图

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, PostsActivity.class);//使用传递来的这个Activity创建一个新的Intent
                                // （之后多半会使用创建的这个Intent来创建一个新的PostsActivity，用以启动PostsActivity）
        return intent;
    }
}
