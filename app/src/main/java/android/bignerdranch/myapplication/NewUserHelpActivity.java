package android.bignerdranch.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class NewUserHelpActivity extends BaseActivity{

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, NewUserHelpActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user_help_layout);
    }


}
