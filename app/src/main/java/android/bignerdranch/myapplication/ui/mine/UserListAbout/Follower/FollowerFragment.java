package android.bignerdranch.myapplication.ui.mine.UserListAbout.Follower;

import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.SimpleResult;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.bignerdranch.myapplication.ReusableTools.MyRecyclerItemClickListener;
import android.bignerdranch.myapplication.ReusableTools.SpaceItemDecoration;
import android.bignerdranch.myapplication.User_Information_Edit.User_Information;
import android.bignerdranch.myapplication.ui.home.PostsDetailsRecyclerView.PostsDetailsActivity;
import android.bignerdranch.myapplication.ui.mine.UserListAbout.UserLab;
import android.bignerdranch.myapplication.ui.mine.UserListAbout.UserListAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FollowerFragment extends Fragment {

    private Retrofit mRetrofit;
    private Api mApi;

    private RecyclerView mRecyclerView;
    private UserListAdapter mAdapter;

    private TextView mTitle;

    private String mUserId;
    private String mToken;
    private String[] mData;

    public void setUserId(String userId) {
        mUserId = userId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_user, container, false);

        BaseActivity homeActivity = (BaseActivity) getActivity();//得到一个可以调用getMyToken的对象
        mToken = homeActivity.getMyToken();

        mTitle=(TextView)view.findViewById(R.id.user_layout_title);
        mTitle.setText("关注");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.follow_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {

        mRetrofit = new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = mRetrofit.create(Api.class);
        Call<SimpleResult> getLikedMsg=mApi.getUserFollow(mUserId,mToken);
        getLikedMsg.enqueue(new Callback<SimpleResult>() {
            @Override
            public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                if (response.body().getData() != null) {
                    mData = response.body().getData();
                }
                else {
                    Log.d("TAG","没有关注用户");
                }
                setAdapterAbout();
            }

            @Override
            public void onFailure(Call<SimpleResult> call, Throwable t) {
                Log.d("TAG","查找关注id数组：网络请求失败！");
            }
        });

    }

    private void setAdapterAbout() {
        UserLab userLab = UserLab.get(mData.length);
        List<User_Information> users = userLab.getUsers();

        mRecyclerView.addItemDecoration(new SpaceItemDecoration(20));
        mAdapter = new UserListAdapter(users, mData, mToken, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MyRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = PostsDetailsActivity.newIntent(getActivity()
                        , mAdapter.getList().get(position).getUserId());
                startActivity(intent);
            }
        });
    }

}
