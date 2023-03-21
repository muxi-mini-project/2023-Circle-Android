package android.bignerdranch.myapplication;

import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.SimpleResult;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity {//继承了BaseActivity的透明任务栏，锁定竖屏

    Button mSignInButton;
    Button mSetUpButton;

    private Retrofit mRetrofit;
    private Api mApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //创建一个指向该url的retrofit
        mRetrofit = new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = mRetrofit.create(Api.class);

        mSignInButton = (Button) findViewById(R.id.sign_in);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    TokenVerify();
                    System.out.println(getMyToken());
                }
        });

        mSetUpButton=(Button) findViewById(R.id.set_up);
        mSetUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, SetUpActivity.class);
                startActivity(intent);
            }
        });

    }
    private void TokenVerify(){
        Call<SimpleResult> apiResult = mApi.tokenVerify(getMyToken());
        apiResult.enqueue(new Callback<SimpleResult>(){
            @Override
            public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                Intent intent;
                if (response.body().getMsg().equals("token不合法.")) {
                    intent = SignInActivity.newIntent(MainActivity.this);
                    Log.d("TAG",response.body().getMsg());
                } else {
                    intent = NavigationBarActivity.newIntent(MainActivity.this);
                    Log.d("TAG",response.body().getMsg());
                }
                startActivity(intent);
            }
            @Override
            public void onFailure(Call<SimpleResult> call, Throwable t) {
                Toast.makeText(MainActivity.this, "请求失败！", Toast.LENGTH_SHORT).show();
                Log.d("TAG","请求失败");
            }
        });
    }

}