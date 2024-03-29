package android.bignerdranch.myapplication.ui.reminder;

import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.SimpleResult;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.bignerdranch.myapplication.ReusableTools.MyRecyclerItemClickListener;
import android.bignerdranch.myapplication.ReusableTools.SpaceItemDecoration;
import android.bignerdranch.myapplication.ui.home.PostsDetailsRecyclerView.PostsDetailsActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReminderFragment extends Fragment {

    private Retrofit mRetrofit;
    private Api mApi;

    private RecyclerView mRecyclerView;
    private ReminderAdapter mAdapter;
    private RadioButton mMyLikesBtn;
    private RadioButton mCommentBtn;

    private String mToken;
    private String[] mData;

    private int t=1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_reminder, container, false);

        BaseActivity homeActivity = (BaseActivity) getActivity();//得到一个可以调用getMyToken的对象
        mToken = homeActivity.getMyToken();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_reminder);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mMyLikesBtn=view.findViewById(R.id.radio_likes);
        mMyLikesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t=1;
                updateUI();
            }
        });

        mCommentBtn=view.findViewById(R.id.radio_comment);
        mCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t=2;
                updateUI();
            }
        });

        updateUI();

        return view;
    }

    private void updateUI() {

        mRetrofit = new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = mRetrofit.create(Api.class);

        Call<SimpleResult> getReminder=null;
        switch (t){
            case 1:getReminder= mApi.getMyLikedMsg(mToken);break;
            case 2:getReminder= mApi.getCommentMsg(mToken);break;
        }
        getReminder.enqueue(new Callback<SimpleResult>() {
            @Override
            public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                if (response.body().getData() != null) {
                    mData = response.body().getData();
                }
                setAdapterAbout();
            }

            @Override
            public void onFailure(Call<SimpleResult> call, Throwable t) {
                Log.d("查找点赞信息","网络请求失败！");
            }
        });

    }

    private void setAdapterAbout() {
        ReminderLab reminderLab = ReminderLab.get(mData.length);
        List<Reminder> reminders = reminderLab.getReminders();

        mRecyclerView.addItemDecoration(new SpaceItemDecoration(20));
        mAdapter = new ReminderAdapter(reminders, mData, mToken, this.getContext(),t);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MyRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = PostsDetailsActivity.newIntent(getActivity()
                        , mAdapter.getList().get(position).getPostID());
                startActivity(intent);
            }
        });
    }
}