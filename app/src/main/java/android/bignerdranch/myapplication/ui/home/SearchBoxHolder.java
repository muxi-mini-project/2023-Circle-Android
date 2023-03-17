package android.bignerdranch.myapplication.ui.home;


import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.SimpleResult;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseFragment;
import android.bignerdranch.myapplication.ReusableTools.BaseHolder;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;
import android.bignerdranch.myapplication.ui.mine.UserListAbout.SearchUser.SearchUserActivity;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchBoxHolder extends BaseHolder {

    private EditText mQueryString;
    private Button mSearchBtn;

    private Retrofit mRetrofit;
    private Api mApi;

    private String[] data;

    private String mToken;
    private Context mContext;

    public SearchBoxHolder(View itemView, ItemTypeDef.Type type, Context context, String token, BaseFragment fragment) {
        super(itemView, type);

        mContext = context;
        mToken = token;

        mRetrofit = new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = mRetrofit.create(Api.class);

        mQueryString = (EditText) itemView.findViewById(R.id.search_box_edit);

        SpannableString s;
        if (fragment instanceof HomeFragment){
            s = new SpannableString("搜索帖子");
        }
        else {
            s=new SpannableString("搜索用户");
        }
        mQueryString.setHint(s);

        mSearchBtn = (Button) itemView.findViewById(R.id.search_btn);
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQueryString.getText().toString().trim().equals("")) {
                    Toast.makeText(mContext, "请输入搜索内容！", Toast.LENGTH_SHORT).show();
                } else {
                    if (fragment instanceof HomeFragment){
                        Call<SimpleResult> searchResult = mApi.searchPost(mToken, mQueryString.getText().toString().trim());
                        searchResult.enqueue(new Callback<SimpleResult>() {
                            @Override
                            public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                                if (response.body().getData() != null) {
                                    data = response.body().getData();
                                    fragment.setData(data);
                                    fragment.setAdapterAbout();
                                } else {
                                    data = new String[0];
                                }
                            }
                            @Override
                            public void onFailure(Call<SimpleResult> call, Throwable t) {
                                Log.d("TAG", "搜索帖子id数组：网络请求失败！");
                            }
                        });
                    }
                    else {
                        Intent intent= SearchUserActivity.newIntent(mContext,mQueryString.getText().toString());
                        mContext.startActivity(intent);
                    }
                }
            }
        });

    }

}