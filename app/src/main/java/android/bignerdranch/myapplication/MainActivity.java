package android.bignerdranch.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends BaseActivity {//继承了BaseActivity的透明任务栏，锁定竖屏

    Button mSignInButton;

    private Button mTest_Navigation_Button;           //测试导航按钮，等APP全部完成后删除

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSignInButton=(Button) findViewById(R.id.sign_in);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SignInActivity.newIntent(MainActivity.this);
                //以MainActivity创建一个SignInActivity的Intent
                startActivity(intent);//启动SignInActivity
            }
        });

        //测试按钮，App全部完成后需删除
        mTest_Navigation_Button=(Button) findViewById(R.id.test);
        mTest_Navigation_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Test_Navigation_Activity.class);
                startActivity(intent);
            }
        });
        //测试按钮到此为止
    }

}