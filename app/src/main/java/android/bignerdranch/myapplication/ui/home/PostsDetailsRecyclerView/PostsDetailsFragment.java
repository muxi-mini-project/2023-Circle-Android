package android.bignerdranch.myapplication.ui.home.PostsDetailsRecyclerView;

import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.ComplexResult;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.JsonTool;
import android.bignerdranch.myapplication.ui.home.Posts;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostsDetailsFragment extends Fragment {
    private RecyclerView mPostsDetailsRecyclerView;
    private PostsDetailsAdapter mPostsDetailsAdapter;

    private ImageButton mBackBtn;

    private Retrofit mRetrofit;
    private Api mApi;

    private String mPostsID;
    private String mToken;



    public void setPostsID(String id){mPostsID=id;}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.post_details, container, false);

        BaseActivity homeActivity = (BaseActivity) getActivity();//得到一个可以调用getMyToken的对象
        mToken = homeActivity.getMyToken();

        mBackBtn = (ImageButton) view.findViewById(R.id.back_btn);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mPostsDetailsRecyclerView = (RecyclerView) view
                .findViewById(R.id.recyclerview_posts_details);
        mPostsDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        upDateUI();

        return view;
    }

    private void upDateUI() {
        CommentLab commentLab = CommentLab.get();
        List<BaseItem> mList = new ArrayList<>();
        Posts item=new Posts();
        {mRetrofit = new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
            mApi = mRetrofit.create(Api.class);
        Call<ComplexResult> seekPostsResult = mApi.seekPosts(mPostsID,mToken);
        seekPostsResult.enqueue(new Callback<ComplexResult>() {
            @Override
            public void onResponse(Call<ComplexResult> call, Response<ComplexResult> response) {
                item.setName(JsonTool.getJsonString(response.body().getData(),"author_name"));
                item.setContent(JsonTool.getJsonString(response.body().getData(),"content"));
                item.setTime(JsonTool.getJsonString(response.body().getData(),"UpdatedAt"));
                item.setProfilePath(JsonTool.getJsonString(response.body().getData(),"avatar_path"));
                item.setID(JsonTool.getJsonString(response.body().getData(),"ID"));
                mList.add(item);
                for (Comment e : commentLab.get_mComment()) {
                    mList.add(e);
                }
                mPostsDetailsAdapter = new PostsDetailsAdapter(mList,getContext(),mToken);//将mList装载入Adapter中
                mPostsDetailsRecyclerView.setAdapter(mPostsDetailsAdapter);//给该recyclerview设置adapter
            }
            @Override
            public void onFailure(Call<ComplexResult> call, Throwable t) {
                Log.d("TAG","评论详情：请求失败");
            }
        });}
    }


}