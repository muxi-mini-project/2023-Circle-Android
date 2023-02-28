package android.bignerdranch.myapplication;



import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.SignInResult;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends BaseActivity {

    Button mSignInButton;
    TextView mSignInTipText;
    EditText usernameEdit;
    EditText passwordEdit;

    private Retrofit mRetrofit;
    private Api mApi;


    public static Intent newIntent(Context packageContext) {
        return  new Intent(packageContext, SignInActivity.class);
    }//启动SignInActivity需要的方法，用来创建一个新Intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_layout);//布局绑定

        //获取用户输入的用户名和密码
        usernameEdit=(EditText)findViewById(R.id.user_name_edit);
        passwordEdit=(EditText)findViewById(R.id.password_edit);

        //隐藏密码
        TransformationMethod method2 = PasswordTransformationMethod.getInstance();
        passwordEdit.setTransformationMethod(method2);

        //创建一个指向该url的retrofit
        mRetrofit=new Retrofit.Builder().baseUrl("http://43.138.61.49:8899/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi=mRetrofit.create(Api.class);

        mSignInButton=(Button) findViewById(R.id.sign_in_btn);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((usernameEdit.getText().toString().trim().equals("")) && (passwordEdit.getText().toString().trim().equals(""))) {
                    Toast.makeText(SignInActivity.this, "请输入账号及密码！", Toast.LENGTH_SHORT).show();
                }
                else {
                    Call<SignInResult> apiResult = mApi.loginTest(usernameEdit.getText().toString(), passwordEdit.getText().toString());
                    apiResult.enqueue(new Callback<SignInResult>() {
                        @Override
                        public void onResponse(Call<SignInResult> call, Response<SignInResult> response) {
                            String token = response.body().getToken();
                            if (token != null) {
                                LoginSucceeded();//使用这个方法来启动下一个Activity
                                saveToken(token);
                            } else {
                                Toast.makeText(SignInActivity.this, response.body().getMsg().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SignInResult> call, Throwable t) {
                            Toast.makeText(SignInActivity.this, "登陆失败！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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
        startActivity(intent);
    }

}
