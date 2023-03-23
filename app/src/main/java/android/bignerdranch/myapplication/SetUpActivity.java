package android.bignerdranch.myapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SetUpActivity extends BaseActivity {
    private TextView mVersion;
    private TextView mNewestVersion;
    private TextView mUpdate;
    private Button mSignOut;

    private Retrofit mRetrofit;
    private Api mApi;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setup);

        mRetrofit = new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = mRetrofit.create(Api.class);

        mNewestVersion = (TextView) findViewById(R.id.version_newest_text);

        mVersion = (TextView) findViewById(R.id.version_text);
        mVersion.setText("当前版本号：" + getAppVersionName(SetUpActivity.this));

        mUpdate = (TextView) findViewById(R.id.update_text);
        mUpdate.setText("前往这个网址以下载最新版本：\nhttp://43.138.61.49:8080/api/update");


        mSignOut = (Button) findViewById(R.id.sign_out_btn);
        mSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeToken();
                String s = "退出登录成功\n您现在可以在点击登陆按钮进入登陆界面";
                Toast.makeText(SetUpActivity.this, s, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
