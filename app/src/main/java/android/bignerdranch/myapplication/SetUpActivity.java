package android.bignerdranch.myapplication;

import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.SimpleResult;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SetUpActivity extends BaseActivity {
    private TextView mVersion;
    private Button mUpdate;
    private Button mSignOut;

    private Retrofit mRetrofit;
    private Api mApi;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setup);

        mVersion=(TextView) findViewById(R.id.version_text);
        mVersion.setText("版本号："+getAppVersionName(SetUpActivity.this));

        mUpdate=(Button) findViewById(R.id.update_btn);
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mSignOut=(Button) findViewById(R.id.sign_out_btn);
        mSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeToken();
                String s="退出登录成功\n您现在可以在点击登陆按钮进入登陆界面";
                Toast.makeText(SetUpActivity.this,s,Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
