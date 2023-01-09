package android.bignerdranch.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignInActivity extends BaseActivity{

    Button mSignInButton;
    TextView mSignInTipText;

    public static Intent newIntent(Context packageContext) {
        return  new Intent(packageContext, SignInActivity.class);
    }//启动SignInActivity需要的方法，用来创建一个新Intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_layout);//布局绑定

        mSignInButton=(Button) findViewById(R.id.sign_in_btn);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginSucceeded();//使用这个方法来启动下一个Activity，便于后续扩展认证功能
                //后续可以在此处写认证方法，将LoginSucceeded方法放在认证方法的认证成功中去调用
            }
        });

        mSignInTipText=(TextView) findViewById(R.id.sign_in_tip);
        mSignInTipText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=NewUserHelpActivity.newIntent(SignInActivity.this);
                startActivity(intent);
            }
        });
    }

    private void LoginSucceeded(){
        Intent intent =NavigationBarActivity.newIntent(SignInActivity.this);
        //以SignInActivity创建一个PostsActivity的Intent
        startActivity(intent);//启动PostsActivity
    }

}
