package android.bignerdranch.myapplication;

import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.SignInResult;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity {//继承了BaseActivity的透明任务栏，锁定竖屏

    Button mSignInButton;
    Button mTestButton;

    private Retrofit mRetrofit;
    private Api mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建一个指向该url的retrofit
        mRetrofit = new Retrofit.Builder().baseUrl("http://43.138.61.49:8899/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = mRetrofit.create(Api.class);

        mSignInButton = (Button) findViewById(R.id.sign_in);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(getToken());
                Call<SignInResult> apiResult = mApi.seekPosts(1, getToken());
                apiResult.enqueue(new Callback<SignInResult>() {
                    @Override
                    public void onResponse(Call<SignInResult> call, Response<SignInResult> response) {
                        Intent intent;
                        if (response.body().getCode()!=0) {
                            intent = NavigationBarActivity.newIntent(MainActivity.this);
                        } else {
                            intent = SignInActivity.newIntent(MainActivity.this);
                        }
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<SignInResult> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "请求失败！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mTestButton=(Button) findViewById(R.id.test_button);
        mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=SignInActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

}