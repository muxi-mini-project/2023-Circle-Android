package android.bignerdranch.myapplication.ui.mine.UserListAbout;

import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.SimpleResult;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.User_Information_Edit.User_Information;
import android.bignerdranch.myapplication.ui.home.PersonalPage.PersonalPageActivity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserListHolder extends RecyclerView.ViewHolder {

    private ImageButton mProfile;
    private TextView mUserName;
    private TextView mUserSignature;
    private ImageButton mIsFollow;

    private boolean IsFollow;

    private Retrofit mRetrofit;
    private Api mApi;

    private User_Information mUser;

    private Context mContext;
    private String mToken;

    public UserListHolder(LayoutInflater inflater, ViewGroup parent, Context context, String token) {
        super(inflater.inflate(R.layout.item_user, parent, false));

        mRetrofit = new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = mRetrofit.create(Api.class);

        mContext = context;
        mToken = token;

        mProfile = (ImageButton) itemView.findViewById(R.id.profile);
        mUserName = (TextView) itemView.findViewById(R.id.user_name);
        mUserSignature = (TextView) itemView.findViewById(R.id.user_signature);
        mIsFollow = (ImageButton) itemView.findViewById(R.id.is_followed_btn);

        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= PersonalPageActivity.newIntent(mContext,mUser.getUserId());
                mContext.startActivity(intent);
            }
        });

        mIsFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!IsFollow) {
                    followUser();
                } else {
                    delFollowUser();
                }
                IsFollow = !IsFollow;
            }
        });
    }

    public void bind(User_Information user) {
        mUser = user;

        //获取是否关注数据
        Call<SimpleResult> isFollowResult=mApi.getIsFollow(mToken,mUser.getUserId());
        isFollowResult.enqueue(new Callback<SimpleResult>() {
            @Override
            public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                if (response.body().getMsg().equals("yes")) {
                    IsFollow=true;
                } else {
                    IsFollow=false;
                }
                setUser();
            }

            @Override
            public void onFailure(Call<SimpleResult> call, Throwable t) {
                Log.d("TAG","是否关注：网络请求失败！");
            }
        });

    }

    private void setFollow() {
        if (IsFollow) {
            mIsFollow.setBackgroundResource(R.mipmap.is_follow_followed);
        } else {
            mIsFollow.setBackgroundResource(R.mipmap.is_follow_not_followed);
        }
    }

    private void setUser(){
        mUserName.setText(mUser.getUser_Name());
        mUserSignature.setText(mUser.getSignature());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.recyangle)
                .circleCropTransform();
        setFollow();
        Glide.with(mContext)
                .load("http://" + mUser.getProfile())
                .apply(options)
                .into(mProfile);
    }

    private void followUser() {
        Call<SimpleResult> followResult = mApi.followUser(mToken, mUser.getUserId());
        followResult.enqueue(new Callback<SimpleResult>() {
            @Override
            public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                bind(mUser);
                Log.d("TAG", "关注用户:" + mUser.getUserId() + " " + "成功");
            }

            @Override
            public void onFailure(Call<SimpleResult> call, Throwable t) {
                Log.d("TAG", "关注用户：网络请求失败");
            }
        });
    }

    private void delFollowUser() {
        Call<SimpleResult> followResult = mApi.delFollowUser(mToken, mUser.getUserId());
        followResult.enqueue(new Callback<SimpleResult>() {
            @Override
            public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                bind(mUser);
                Log.d("TAG", "取消关注用户:" + mUser.getUserId() + " " + "成功");
            }

            @Override
            public void onFailure(Call<SimpleResult> call, Throwable t) {
                Log.d("TAG", "取消关注用户：网络请求失败");
            }
        });
    }
}
