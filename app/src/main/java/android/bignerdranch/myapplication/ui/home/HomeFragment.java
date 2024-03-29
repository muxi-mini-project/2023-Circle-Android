package android.bignerdranch.myapplication.ui.home;

import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.SimpleResult;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.bignerdranch.myapplication.ReusableTools.BaseFragment;
import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.MyRecyclerItemClickListener;
import android.bignerdranch.myapplication.ReusableTools.SpaceItemDecoration;
import android.bignerdranch.myapplication.ReusableTools.StringTool;
import android.bignerdranch.myapplication.ui.home.NewPosts.EditPostsActivity;
import android.bignerdranch.myapplication.ui.home.Posts.Posts;
import android.bignerdranch.myapplication.ui.home.Posts.PostsAdapter;
import android.bignerdranch.myapplication.ui.home.Posts.PostsLab;
import android.bignerdranch.myapplication.ui.home.PostsDetailsRecyclerView.PostsDetailsActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends BaseFragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mHomeRecyclerView;
    private PostsAdapter mPostsAdapter;
    private ImageButton newPostsBtn;

    private RadioButton mRecButton;
    private RadioButton mFollowButton;
    private RadioButton mNewestButton;

    private int t = 1;
    private int index=0;

    private Retrofit mRetrofit;
    private Api mApi;

    private String[] mData = new String[0];//帖子id数组
    private String mToken;
    private PostsLab postsLab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.layout_home, container, false);

        BaseActivity homeActivity = (BaseActivity) getActivity();//得到一个可以调用getMyToken的对象
        mToken = homeActivity.getMyToken();


        mHomeRecyclerView = view
                .findViewById(R.id.recyclerview_home);
        mHomeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        newPostsBtn = view.findViewById(R.id.new_posts_btn);
        newPostsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EditPostsActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_home);
        mSwipeRefreshLayout.setEnabled(true);//设置可用
        mSwipeRefreshLayout.setRefreshing(true);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {//刷新操作监听
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        index+=15;
                        upDateUI();
                    }
                },0);
            }
        });

        {
            mRecButton = view.findViewById(R.id.radio_recommend_btn);
            mRecButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    t = 1;
                    upDateUI();
                }
            });

            mFollowButton = view.findViewById(R.id.radio_follow_btn);
            mFollowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    t = 2;
                    upDateUI();
                }
            });

            mNewestButton = view.findViewById(R.id.radio_newest_btn);
            mNewestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    t = 3;
                    upDateUI();
                }
            });
        }//设置RadioButton

        upDateUI();

        return view;
    }

    private void upDateUI() {
        {
            mRetrofit = new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mApi = mRetrofit.create(Api.class);
        }
        String endDate;
        String startDate;
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_YEAR, -10);
            startDate = StringTool.getDateString(calendar.getTime());
            endDate = StringTool.getDateString(new Date());
        }//制作end和start时间字符串
        Call<SimpleResult> apiResultCall = null;
        switch (t) {
            case 1:
                apiResultCall = mApi.recPost(mToken, "日常唠嗑", endDate, startDate, 15, index);
                break;
            case 2:
                apiResultCall = mApi.followPost(mToken);
                break;
            case 3:
                apiResultCall = mApi.newestPost(mToken, 0, 15);
        }
        if (apiResultCall != null) {
            apiResultCall.enqueue(new Callback<SimpleResult>() {
                @Override
                public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                    if (response.body() != null) {
                        mData = response.body().getData();
                        if (mData.length==1){
                            String msg=("没有更多帖子了\n重置帖子请返回初始界面重新进入");
                            Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
                        }
                    }
                    setAdapterAbout();
                }

                @Override
                public void onFailure(Call<SimpleResult> call, Throwable t) {
                    Toast.makeText(getActivity(), "查询帖子；网络请求异常！", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void setAdapterAbout() {
        postsLab = PostsLab.get(mData.length);
        List<BaseItem> mList = new ArrayList<>();
        mList.add(new SearchBox());//在mList中加入搜索框
        for (Posts e : postsLab.getPosts()) {
            mList.add(e);
        }

        if (mHomeRecyclerView.getItemDecorationCount() == 0) {
            mHomeRecyclerView.addItemDecoration(new SpaceItemDecoration(40));//设置item之间的间隔为20
        }
        mPostsAdapter = new PostsAdapter(mList, mData, mToken, getActivity(), this);//将mList装载入Adapter中
        mHomeRecyclerView.setAdapter(mPostsAdapter);//给该recyclerview设置adapter

        mPostsAdapter.setOnItemClickListener(new MyRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = PostsDetailsActivity.newIntent(getActivity()
                        , mPostsAdapter.getList().get(position).getID());
                startActivity(intent);
            }
        });

        mPostsAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }


//




    @Override
    public void setData(String[] data) {
        mData = data;
    }

}