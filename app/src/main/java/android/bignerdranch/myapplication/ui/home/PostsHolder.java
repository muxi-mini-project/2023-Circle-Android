package android.bignerdranch.myapplication.ui.home;

import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.ComplexResult;
import android.bignerdranch.myapplication.ApiAbout.SimpleResult;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseHolder;
import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;
import android.bignerdranch.myapplication.ReusableTools.StringTool;
import android.bignerdranch.myapplication.ReusableTools.MyRecyclerItemClickListener;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PostsHolder extends BaseHolder implements View.OnClickListener {

    private TextView mNameView;
    private TextView mDateView;
    private TextView mContent;
    private TextView mTitle;
    private ImageButton mIsFollow;
    private ImageButton mIsLikes;
    private TextView mLikesNum;
    private TextView mCommentNum;


    private ImageView mProfile;

    private Retrofit mRetrofit;
    private Api mApi;

    private Context mContext;

    private Posts mPosts;
    private String mToken;

    public PostsHolder(View itemView, ItemTypeDef.Type type, MyRecyclerItemClickListener myRecyclerItemClickListener
            , String token, Context context) {
        super(itemView, type, myRecyclerItemClickListener);

        mRetrofit = new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = mRetrofit.create(Api.class);

        {
            mContext = context;
            mToken = token;//传入token
            mNameView = (TextView) itemView.findViewById(R.id.publisher_name);
            mDateView = (TextView) itemView.findViewById(R.id.publish_time);
            mContent = (TextView) itemView.findViewById(R.id.publish_content);
            mTitle=(TextView)itemView.findViewById(R.id.publish_title);
            mIsFollow = (ImageButton) itemView.findViewById(R.id.is_followed_btn);
            mIsLikes = (ImageButton) itemView.findViewById(R.id.is_likes_btn);
            mLikesNum = (TextView) itemView.findViewById(R.id.likes_num);
            mCommentNum = (TextView) itemView.findViewById(R.id.comment_num);
            mProfile = (ImageView) itemView.findViewById(R.id.publish_head_portrait);
        }


        mIsLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //改变mPosts，并再次加载posts
                if (!mPosts.isLikes()) {
                    likePost();
                } else {
                    deleteLikePost();
                }
            }
        });

        mIsFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (!mPosts.isFollow()){
                   followUser();
               }else {
                   delFollowUser();
               }
            }
        });
    }


    public void bind(BaseItem item, String id) {
        if (mPosts == null) {
            mPosts = (Posts) item;
        }
        Call<ComplexResult> seekPostsResult = mApi.seekPosts(id, mToken);
        //每次装载数据时都要通过网络请求更新帖子数据，如点赞数之类的
        seekPostsResult.enqueue(new Callback<ComplexResult>() {
            @Override
            public void onResponse(Call<ComplexResult> call, Response<ComplexResult> response) {

                mPosts.setLikesNumber(StringTool.getJsonString(response.body().getData(), "likes"));
                mPosts.setCommentNumber(StringTool.getJsonString(response.body().getData(), "comment_no"));

                //获取是否点赞数据
                Call<SimpleResult> isLikeResult = mApi.getIsLike(mPosts.getID(), mToken);
                isLikeResult.enqueue(new Callback<SimpleResult>() {
                    @Override
                    public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                        if (response.body().getMsg().equals("yes")) {
                            mPosts.setLikes(true);
                        } else {
                            mPosts.setLikes(false);
                        }
                        setPosts();
                    }

                    @Override
                    public void onFailure(Call<SimpleResult> call, Throwable t) {
                        Log.d("TAG", "是否点赞：网络请求失败！");
                    }
                });

                //获取是否关注数据
                Call<SimpleResult> isFollowResult=mApi.getIsFollow(mToken,mPosts.getPublisherId());
                isFollowResult.enqueue(new Callback<SimpleResult>() {
                    @Override
                    public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                        if (response.body().getMsg().equals("yes")) {
                            mPosts.setFollow(true);
                        } else {
                            mPosts.setFollow(false);
                        }
                        setPosts();
                    }

                    @Override
                    public void onFailure(Call<SimpleResult> call, Throwable t) {
                        Log.d("TAG","是否关注：网络请求失败！");
                    }
                });
            }

            @Override
            public void onFailure(Call<ComplexResult> call, Throwable t) {
                Log.d("TAG", "查找帖子：网络请求失败");
            }
        });
    }

    private void setFollow(boolean isFollow) {
        if (isFollow) {
            mIsFollow.setBackgroundResource(R.mipmap.is_follow_followed);
        } else {
            mIsFollow.setBackgroundResource(R.mipmap.is_follow_not_followed);
        }
    }

    private void setLikes(boolean isLike) {
        if (isLike) {
            mIsLikes.setBackgroundResource(R.mipmap.likes_yes);
        } else {
            mIsLikes.setBackgroundResource(R.mipmap.likes_not);
        }
    }

    private void setPosts() {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.recyangle)
                .circleCropTransform();
        Glide.with(mContext)
                .load("http://" + mPosts.getProfilePath())
                .apply(options)
                .into(mProfile);//设置头像
        mNameView.setText(mPosts.getName());
        mDateView.setText(mPosts.getTime());
        mLikesNum.setText(mPosts.getLikesNumber());
        mCommentNum.setText(mPosts.getCommentNumber());
        setLikes(mPosts.isLikes());
        setFollow(mPosts.isFollow());
        mTitle.setText(mPosts.getTitle());
        mContent.setText(mPosts.getContent());
    }

    private void likePost() {
        Call<SimpleResult> likesResult = mApi.likesPosts(mPosts.getID(), mToken);
        likesResult.enqueue(new Callback<SimpleResult>() {
            @Override
            public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                bind(mPosts, mPosts.getID());
                Log.d("TAG", "点赞成功");
            }

            @Override
            public void onFailure(Call<SimpleResult> call, Throwable t) {
                Log.d("TAG", "点赞：网络请求失败");
            }
        });
    }

    private void deleteLikePost() {
        Call<SimpleResult> deleteLikesResult = mApi.deleteLikesPosts(mPosts.getID(), mToken);
        deleteLikesResult.enqueue(new Callback<SimpleResult>() {
            @Override
            public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                bind(mPosts, mPosts.getID());
                Log.d("TAG", "取消点赞成功");
            }

            @Override
            public void onFailure(Call<SimpleResult> call, Throwable t) {
                Log.d("TAG", "取消点赞：网络请求失败");
            }
        });
    }

    private void followUser(){
        Call<SimpleResult> followResult=mApi.followUser(mToken,mPosts.getPublisherId());
        followResult.enqueue(new Callback<SimpleResult>() {
            @Override
            public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                bind(mPosts,mPosts.getID());
                Log.d("TAG","关注用户:"+mPosts.getPublisherId()+" "+"成功");
            }

            @Override
            public void onFailure(Call<SimpleResult> call, Throwable t) {
                Log.d("TAG","关注用户：网络请求失败");
            }
        });
    }

    private void delFollowUser(){
        Call<SimpleResult> followResult=mApi.delFollowUser(mToken,mPosts.getPublisherId());
        followResult.enqueue(new Callback<SimpleResult>() {
            @Override
            public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                bind(mPosts,mPosts.getID());
                Log.d("TAG","取消关注用户:"+mPosts.getPublisherId()+" "+"成功");
            }

            @Override
            public void onFailure(Call<SimpleResult> call, Throwable t) {
                Log.d("TAG","取消关注用户：网络请求失败");
            }
        });
    }
}
