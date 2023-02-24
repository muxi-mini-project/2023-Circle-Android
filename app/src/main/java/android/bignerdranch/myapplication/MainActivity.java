package android.bignerdranch.myapplication;

import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

public class MainActivity extends BaseActivity {//继承了BaseActivity的透明任务栏，锁定竖屏

    Button mSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSignInButton=(Button) findViewById(R.id.sign_in);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getToken()!="false"){
                    Intent intent =NavigationBarActivity.newIntent(MainActivity.this);
                    startActivity(intent);
                }
                else {
                    Intent intent = SignInActivity.newIntent(MainActivity.this);
                    //以MainActivity创建一个SignInActivity的Intent
                    startActivity(intent);//启动SignInActivity
                }
            }
        });
    }

}