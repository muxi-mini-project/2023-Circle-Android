package android.bignerdranch.myapplication.ui.home;

import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.SimpleResult;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseHolder;
import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;
import android.bignerdranch.myapplication.ReusableTools.MyRecyclerItemClickListener;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PostsHolder extends BaseHolder implements View.OnClickListener{

    private TextView mNameView;
    private TextView mDateView;
    private ImageButton mIsFollow;
    private TextView mContent;
    private ImageButton mIsLikes;
    private ImageView mProfile;

    private Retrofit mRetrofit;
    private Api mApi;

    private Context mContext;

    private Posts mPosts;
    private String mToken;

    public PostsHolder(View itemView, ItemTypeDef.Type type,MyRecyclerItemClickListener myRecyclerItemClickListener,String token,Context context) {
        super(itemView,type,myRecyclerItemClickListener);

        mRetrofit=new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi=mRetrofit.create(Api.class);

        {mContext=context;
        mToken=token;//传入token
        mNameView = (TextView) itemView.findViewById(R.id.publisher_name);
        mDateView = (TextView) itemView.findViewById(R.id.publish_time);
        mContent = (TextView) itemView.findViewById(R.id.publish_content);
        mIsFollow = (ImageButton) itemView.findViewById(R.id.is_followed_btn);
        mIsLikes =(ImageButton) itemView.findViewById(R.id.is_likes_btn);
        mProfile=(ImageView) itemView.findViewById(R.id.publish_head_portrait);}


        mIsLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //改变mPosts，并再次加载posts
                if (!mPosts.isLikes()){
                Call<SimpleResult> likesResult= mApi.likesPosts(mPosts.getID(),mToken);
                likesResult.enqueue(new Callback<SimpleResult>() {
                    @Override
                    public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                        Log.d("TAG","点赞成功");
                    }

                    @Override
                    public void onFailure(Call<SimpleResult> call, Throwable t) {
                        Log.d("TAG","点赞：网络请求失败");
                    }
                });}
                mPosts.setLikes(!mPosts.isLikes());
                bind(mPosts);
            }
        });

        mIsFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //同理
                mPosts.setFollow(!mPosts.isFollow());
                bind(mPosts);
            }
        });
    }
    //给帖子列表（Home界面使用的构造器）

    public PostsHolder(View itemView, ItemTypeDef.Type type,Context context,String token) {
        super(itemView,type);

        mRetrofit=new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi=mRetrofit.create(Api.class);


        {mToken=token;
        mContext=context;
        mNameView = (TextView) itemView.findViewById(R.id.publisher_name);
        mDateView = (TextView) itemView.findViewById(R.id.publish_time);
        mContent = (TextView) itemView.findViewById(R.id.publish_content);
        mIsFollow = (ImageButton) itemView.findViewById(R.id.is_followed_btn);
        mIsLikes =(ImageButton) itemView.findViewById(R.id.is_likes_btn);
        mProfile=(ImageView) itemView.findViewById(R.id.publish_head_portrait);
        }//给设置帖子各项数据

        mIsLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mPosts.isLikes()){
                Call<SimpleResult> likesResult= mApi.likesPosts(mPosts.getID(),mToken);
                likesResult.enqueue(new Callback<SimpleResult>() {
                    @Override
                    public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                        Log.d("TAG","点赞成功");
                    }

                    @Override
                    public void onFailure(Call<SimpleResult> call, Throwable t) {
                        Log.d("TAG","点赞：网络请求失败");
                    }
                });
            }
                mPosts.setLikes(!mPosts.isLikes());
                bind(mPosts);
            }
        });

        mIsFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosts.setFollow(!mPosts.isFollow());
                bind(mPosts);
            }
        });
    }//给帖子详情界面使用的构造器

    public void bind(BaseItem item) {
        mPosts =(Posts) item;
        Glide.with(mContext)
                .load("http://"+mPosts.getProfilePath())
                .centerCrop()
                .into(mProfile);
        mNameView.setText(mPosts.getName());
        mDateView.setText(mPosts.getTime());
        if (mPosts.isFollow()) {
            mIsFollow.setBackgroundResource(R.mipmap.is_follow_followed);
        } else {
            mIsFollow.setBackgroundResource(R.mipmap.is_follow_not_followed);
        }
        if (mPosts.isLikes()){
            mIsLikes.setBackgroundResource(R.mipmap.likes_yes);
        }
        else {
            mIsLikes.setBackgroundResource(R.mipmap.likes_not);
        }
        mContent.setText(mPosts.getContent());
    }

}
