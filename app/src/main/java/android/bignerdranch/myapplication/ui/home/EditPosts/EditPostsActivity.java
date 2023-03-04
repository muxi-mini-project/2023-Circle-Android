package android.bignerdranch.myapplication.ui.home.EditPosts;

import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.ApiResult;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.bignerdranch.myapplication.ui.home.Posts;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.User_Information_Edit.User_Information;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditPostsActivity extends BaseActivity {

    private Posts mPosts;
    private User_Information user_information=User_Information.getUser_information();

    private Retrofit mRetrofit;
    private Api mApi;

    private ImageButton BackButton;
    private ImageButton ReleaseButton;
    private EditText EditTitle;
    private EditText EditContent;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mPosts=new Posts();
        setContentView(R.layout.layout_editposts);


        BackButton=(ImageButton) findViewById(R.id.backbutton_editposts);
        ReleaseButton=(ImageButton) findViewById(R.id.releasebutton_editposts);
        EditContent=(EditText) findViewById(R.id.posts_content_field);
        EditTitle=(EditText)findViewById(R.id.posts_title_field);

        //返回键的监听器，记得填起来
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //设置EditText的显示方式为多行文本输入
        EditContent.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        //改变EditText默认的单行模式
        EditContent.setSingleLine(false);

        //水平滚动设置为False
        EditContent.setHorizontallyScrolling(false);

        //创建一个指向该url的retrofit
        mRetrofit=new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi=mRetrofit.create(Api.class);
        //发布键的监听器
        ReleaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ApiResult> apiResult=mApi.publishPosts(getMyToken(),"no","日常唠嗑",EditTitle.getText().toString(),EditContent.getText().toString());
                apiResult.enqueue(new Callback<ApiResult>() {
                    @Override
                    public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                        Toast.makeText(EditPostsActivity.this,response.body().getMsg(),Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ApiResult> call, Throwable t) {
                        Toast.makeText(EditPostsActivity.this,"请求失败！",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    public static Intent newIntent(Context packageContext) {
        return  new Intent(packageContext, EditPostsActivity.class);
    }
}
