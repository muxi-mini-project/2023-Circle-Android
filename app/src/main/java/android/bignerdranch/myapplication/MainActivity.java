package android.bignerdranch.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

<<<<<<< HEAD
public class MainActivity extends AppCompatActivity {
    private ImageButton signInButton;

=======
public class MainActivity extends BaseActivity {//继承了BaseActivity的透明任务栏，锁定竖屏

    Button mSignInButton;
>>>>>>> liz

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        //透明任务栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //

        setContentView(R.layout.activity_main);

        signInButton=(ImageButton) findViewById(R.id.sign_in_btn);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = PostsActivity.newIntent(MainActivity.this);//传入当前Activity创建一个新Intent
                startActivity(intent);//启动PostsActivity
=======
        setContentView(R.layout.activity_main);

        mSignInButton=(Button) findViewById(R.id.sign_in);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SignInActivity.newIntent(MainActivity.this);
                //以MainActivity创建一个SignInActivity的Intent
                startActivity(intent);//启动SignInActivity
>>>>>>> liz
            }
        });
    }
}