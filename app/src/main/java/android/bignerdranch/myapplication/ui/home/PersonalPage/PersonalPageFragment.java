package android.bignerdranch.myapplication.ui.home.PersonalPage;

import android.annotation.SuppressLint;
import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.ComplexResult;
import android.bignerdranch.myapplication.ApiAbout.SimpleResult;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.bignerdranch.myapplication.ReusableTools.BaseFragment;
import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.MyRecyclerItemClickListener;
import android.bignerdranch.myapplication.ReusableTools.SpaceItemDecoration;
import android.bignerdranch.myapplication.ReusableTools.StringTool;
import android.bignerdranch.myapplication.ui.home.Posts.Posts;
import android.bignerdranch.myapplication.ui.home.Posts.PostsAdapter;
import android.bignerdranch.myapplication.ui.home.Posts.PostsLab;
import android.bignerdranch.myapplication.ui.home.PostsDetailsRecyclerView.PostsDetailsActivity;
import android.bignerdranch.myapplication.ui.home.SearchBox;
import android.bignerdranch.myapplication.ui.mine.UserListAbout.Fans.FansActivity;
import android.bignerdranch.myapplication.ui.mine.UserListAbout.Follower.FollowerActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonalPageFragment extends BaseFragment {
    private RecyclerView mPostsRecyclerView;
    private PostsAdapter mPostsAdapter;

    private Retrofit mRetrofit;
    private Api mApi;

    private String mUserId;
    private String[] mData;//帖子id数组
    private String token;
    private PostsLab postsLab;
    private String profileUrl;

    private ImageView mProfile;
    private TextView mUserName;
    private TextView mUserSignature;
    private ImageView mUserSex;

    private TextView mPostsNum;
    private TextView mFollowNum;
    private TextView mFansNum;

    public void setUserId(String userId) {
        mUserId = userId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.layout_mine, container, false);

        BaseActivity homeActivity = (BaseActivity) getActivity();//得到一个可以调用getMyToken的对象
        token = homeActivity.getMyToken();


        mUserName = (TextView) view.findViewById(R.id.user_name);
        mUserSignature = (TextView) view.findViewById(R.id.user_signature);
        mProfile = (ImageView) view.findViewById(R.id.profile_pic);
        mFollowNum = (TextView) view.findViewById(R.id.follow_number);
        mFansNum = (TextView) view.findViewById(R.id.fans_number);
        mPostsNum = (TextView) view.findViewById(R.id.posts_number);
        mUserSex=(ImageView)view.findViewById(R.id.user_sex);

        {
            mRetrofit = new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mApi = mRetrofit.create(Api.class);
        }
        Call<ComplexResult> apiResult = mApi.getUserMsg(mUserId, token);
        apiResult.enqueue(new Callback<ComplexResult>() {
            @SuppressLint("ResourceType")
            @Override
            public void onResponse(Call<ComplexResult> call, Response<ComplexResult> response) {
                mUserName.setText(StringTool.getJsonString(response.body().getData(), "Name"));
                mUserSignature.setText(StringTool.getJsonString(response.body().getData(), "Signature"));
                mPostsNum.setText(StringTool.getJsonString(response.body().getData(), "PostNo"));
                mFollowNum.setText(StringTool.getJsonString(response.body().getData(), "FollowingNo"));
                mFansNum.setText(StringTool.getJsonString(response.body().getData(), "FollowerNo"));
                profileUrl = StringTool.getJsonString(response.body().getData(), "AvatarPath");
                setUserSex(StringTool.getJsonString(response.body().getData(),"Gender"));
                Glide.with(PersonalPageFragment.this)
                        .load("http://" + profileUrl
                        )
                        .error(R.mipmap.sign_in)
                        .centerCrop()
                        .into(mProfile);
            }

            @Override
            public void onFailure(Call<ComplexResult> call, Throwable t) {
                Log.d("TAG", "获取用户信息：网络请求失败！");
            }
        });

        mFollowNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = FollowerActivity.newIntent(getActivity(),mUserId);
                startActivity(intent);
            }
        });

        mFansNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = FansActivity.newIntent(getActivity(),mUserId);
                startActivity(intent);
            }
        });


        mPostsRecyclerView = (RecyclerView) view
                .findViewById(R.id.recyclerview_mine);
        mPostsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        upDateUI();

        return view;
    }

    private void setUserSex(String userSex){
        switch (userSex){
            case "Male":mUserSex.setBackgroundResource(R.drawable.ismale_no);break;
            case "Female":mUserSex.setBackgroundResource(R.drawable.isfemale_no);break;
            default:mUserSex.setBackgroundResource(R.drawable.unselected_no);break;
        }
    }

    private void upDateUI() {
        Call<SimpleResult> apiResultCall = mApi.userPosts(mUserId, token);
        apiResultCall.enqueue(new Callback<SimpleResult>() {
            @Override
            public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                mData = response.body().getData();
                if (mData != null) {
                    setAdapterAbout();
                }
            }
            @Override
            public void onFailure(Call<SimpleResult> call, Throwable t) {
                Toast.makeText(getActivity(), "网络请求异常！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setAdapterAbout() {
        postsLab = PostsLab.get(mData.length);//
        List<BaseItem> mList = new ArrayList<>();
        mList.add(new SearchBox());//在mList中加入搜索框
        for (Posts e : postsLab.getPosts()) {
            mList.add(e);
        }

        mPostsRecyclerView.addItemDecoration(new SpaceItemDecoration(20));//设置item之间的间隔为20
        mPostsAdapter = new PostsAdapter(mList, mData, token, getContext(), this);//将mList装载入Adapter中
        mPostsRecyclerView.setAdapter(mPostsAdapter);//给该recyclerview设置adapter

        mPostsAdapter.setOnItemClickListener(new MyRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = PostsDetailsActivity.newIntent(getActivity()
                        , mPostsAdapter.getList().get(position).getID());
                startActivity(intent);
            }
        });
    }

    @Override
    public void setData(String[] data) {
        mData = data;
    }
}
