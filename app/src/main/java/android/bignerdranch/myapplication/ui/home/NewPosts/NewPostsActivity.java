package android.bignerdranch.myapplication.ui.home.NewPosts;

import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class NewPostsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_editposts);

    }
    public static Intent newIntent(Context packageContext) {
        return  new Intent(packageContext, NewPostsActivity.class);
    }
}
