package android.bignerdranch.myapplication.ui.mine;

import android.annotation.SuppressLint;
import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.ComplexResult;
import android.bignerdranch.myapplication.ApiAbout.SimpleResult;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.StringTool;
import android.bignerdranch.myapplication.ReusableTools.MyRecyclerItemClickListener;
import android.bignerdranch.myapplication.ReusableTools.SpaceItemDecoration;
import android.bignerdranch.myapplication.User_Information_Edit.Activity_User_Information;
import android.bignerdranch.myapplication.ui.home.PostsAdapter;
import android.bignerdranch.myapplication.ui.home.Posts;
import android.bignerdranch.myapplication.ui.home.PostsDetailsRecyclerView.PostsDetailsActivity;
import android.bignerdranch.myapplication.ui.home.PostsLab;
import android.bignerdranch.myapplication.ui.home.SearchBox;
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

import androidx.fragment.app.Fragment;
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

public class MineFragment extends Fragment {
    private LinearLayout mUserInformation;
    private RecyclerView mPostsRecyclerView;
    private PostsAdapter mPostsAdapter;

    private Retrofit mRetrofit;
    private Api mApi;

    private String[] data;//??????id??????
    private String token;
    private PostsLab postsLab;
    private String profileUrl;

    private ImageView mProfile;
    private TextView mUserName;
    private TextView mUserSignature;

    private TextView mPostsNum;
    private TextView mFollowNum;
    private TextView mFansNum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        View view =inflater.inflate(R.layout.layout_mine,container,false);

        BaseActivity homeActivity = (BaseActivity) getActivity();//????????????????????????getMyToken?????????
        token = homeActivity.getMyToken();


        mUserInformation=(LinearLayout)view
                .findViewById(R.id.user_information);
        mUserInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Activity_User_Information.class);
                startActivity(intent);
            }
        });

        mUserName=(TextView) view.findViewById(R.id.user_name);
        mUserSignature=(TextView)view.findViewById(R.id.user_signature);
        mProfile=(ImageView)view.findViewById(R.id.profile_pic);
        mFollowNum=(TextView)view.findViewById(R.id.follow_number);
        mFansNum=(TextView)view.findViewById(R.id.fans_number);
        mPostsNum=(TextView)view.findViewById(R.id.posts_number);


        mRetrofit=new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi=mRetrofit.create(Api.class);

        Call<ComplexResult> apiResult=mApi.getMyMsg(token);
        apiResult.enqueue(new Callback<ComplexResult>() {
            @SuppressLint("ResourceType")
            @Override
            public void onResponse(Call<ComplexResult> call, Response<ComplexResult> response) {
                mUserName.setText(StringTool.getJsonString(response.body().getData(),"Name"));
                mUserSignature.setText(StringTool.getJsonString(response.body().getData(),"Signature"));
                mPostsNum.setText(StringTool.getJsonString(response.body().getData(),"PostNo"));
                mFollowNum.setText(StringTool.getJsonString(response.body().getData(),"FollowingNo"));
                mFansNum.setText(StringTool.getJsonString(response.body().getData(),"FollowerNo"));
                profileUrl= StringTool.getJsonString(response.body().getData(),"AvatarPath");
                Log.d("TAG",profileUrl);
                Glide.with(MineFragment.this)
                        .load("http://"+profileUrl
                        )
                        .error(R.mipmap.sign_in)
                        .centerCrop()
                        .into(mProfile);
            }

            @Override
            public void onFailure(Call<ComplexResult> call, Throwable t) {

            }
        });

        mPostsRecyclerView=(RecyclerView) view
                .findViewById(R.id.recyclerview_mine);
        mPostsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        upDateUI();

        return view;
    }

    private void upDateUI() {
        mRetrofit = new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = mRetrofit.create(Api.class);
        Call<SimpleResult> apiResultCall = mApi.myPost(token);
        apiResultCall.enqueue(new Callback<SimpleResult>() {
            @Override
            public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                data = response.body().getData();
                if (data != null) {
                    setAdapterAbout();
                }
            }

            @Override
            public void onFailure(Call<SimpleResult> call, Throwable t) {
                Toast.makeText(getActivity(), "?????????????????????", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapterAbout() {
        postsLab = PostsLab.get(data.length);//
        List<BaseItem> mList = new ArrayList<>();
        mList.add(new SearchBox());//???mList??????????????????
        for (Posts e : postsLab.getPosts()) {
            mList.add(e);
        }

        mPostsRecyclerView.addItemDecoration(new SpaceItemDecoration(20));//??????item??????????????????20
        mPostsAdapter = new PostsAdapter(mList, data, token,getContext());//???mList?????????Adapter???
        mPostsRecyclerView.setAdapter(mPostsAdapter);//??????recyclerview??????adapter

        mPostsAdapter.setOnItemClickListener(new MyRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = PostsDetailsActivity.newIntent(getActivity()
                        ,mPostsAdapter.getList().get(position).getID());
                startActivity(intent);
            }
        });
    }

}