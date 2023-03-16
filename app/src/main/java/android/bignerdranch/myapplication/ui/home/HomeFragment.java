package android.bignerdranch.myapplication.ui.home;

import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.SimpleResult;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.MyRecyclerItemClickListener;
import android.bignerdranch.myapplication.ReusableTools.SpaceItemDecoration;
import android.bignerdranch.myapplication.ReusableTools.StringTool;
import android.bignerdranch.myapplication.ui.home.EditPosts.NewPostsActivity;
import android.bignerdranch.myapplication.ui.home.PostsDetailsRecyclerView.PostsDetailsActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment {
    private RecyclerView mHomeRecyclerView;
    private PostsAdapter mPostsAdapter;
    private ImageButton newPostsBtn;

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

        mHomeRecyclerView = (RecyclerView) view
                .findViewById(R.id.recyclerview_home);
        mHomeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        newPostsBtn = (ImageButton) view.findViewById(R.id.new_posts_btn);
        newPostsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = NewPostsActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });

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
        Call<SimpleResult> apiResultCall = mApi.recPost(mToken, "日常唠嗑", endDate, startDate, 10, 0);
        Log.d("TAG", endDate + " " + startDate);
        apiResultCall.enqueue(new Callback<SimpleResult>() {
            @Override
            public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                if (response.body() != null) {
                    mData = response.body().getData();
                }
                setAdapterAbout();

            }

            @Override
            public void onFailure(Call<SimpleResult> call, Throwable t) {
                Toast.makeText(getActivity(), "网络请求异常！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapterAbout() {
        postsLab = PostsLab.get(mData.length);
        List<BaseItem> mList = new ArrayList<>();
        mList.add(new SearchBox());//在mList中加入搜索框
        for (Posts e : postsLab.getPosts()) {
            mList.add(e);
        }

        mHomeRecyclerView.addItemDecoration(new SpaceItemDecoration(20));//设置item之间的间隔为20
        mPostsAdapter = new PostsAdapter(mList, mData, mToken, getActivity());//将mList装载入Adapter中
        mHomeRecyclerView.setAdapter(mPostsAdapter);//给该recyclerview设置adapter

        mPostsAdapter.setOnItemClickListener(new MyRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = PostsDetailsActivity.newIntent(getActivity()
                        , mPostsAdapter.getList().get(position).getID());
                startActivity(intent);
            }
        });
    }
}