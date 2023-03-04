package android.bignerdranch.myapplication.ui.home;

import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.ApiResult;

import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.bignerdranch.myapplication.ui.home.PostsDetailsRecyclerView.PostsDetailsActivity;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.MyRecyclerItemClickListener;
import android.bignerdranch.myapplication.ReusableTools.SpaceItemDecoration;
import android.bignerdranch.myapplication.ui.home.EditPosts.EditPostsActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment {
    private RecyclerView mHomeRecyclerView;
    private HomeAdapter mHomeAdapter;
    private ImageButton newPostsBtn;

    private Retrofit mRetrofit;
    private Api mApi;

    private String[] data;//帖子id数组
    private String token;
    private PostsLab postsLab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.layout_home, container, false);

        BaseActivity homeActivity = (BaseActivity) getActivity();//得到一个可以调用getMyToken的对象
        token = homeActivity.getMyToken();

        mHomeRecyclerView = (RecyclerView) view
                .findViewById(R.id.recyclerview_home);
        mHomeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        newPostsBtn = (ImageButton) view.findViewById(R.id.new_posts_btn);
        newPostsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EditPostsActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });

        upDateUI();

        return view;
    }

    private void upDateUI() {
        mRetrofit = new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = mRetrofit.create(Api.class);
        Call<ApiResult> apiResultCall = mApi.myPost(token);
        apiResultCall.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                data = response.body().getData();
                if (data != null) {
                    setAdapterAbout();
                }
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                Toast.makeText(getActivity(), "网络请求异常！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapterAbout() {
        postsLab = PostsLab.get(data.length);//
        List<BaseItem> mList = new ArrayList<>();
        mList.add(new SearchBox());//在mList中加入搜索框
        for (Posts e : postsLab.getPosts()) {
            mList.add(e);
        }

        mHomeRecyclerView.addItemDecoration(new SpaceItemDecoration(20));//设置item之间的间隔为20
        mHomeAdapter = new HomeAdapter(mList, data, token);//将mList装载入Adapter中
        mHomeRecyclerView.setAdapter(mHomeAdapter);//给该recyclerview设置adapter

        mHomeAdapter.setOnItemClickListener(new MyRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = PostsDetailsActivity.newIntent(getActivity(), mHomeAdapter.getList().get(position).getName()
                        , mHomeAdapter.getList().get(position).getTime()
                        , mHomeAdapter.getList().get(position).getContent());
                startActivity(intent);
            }
        });
    }
}