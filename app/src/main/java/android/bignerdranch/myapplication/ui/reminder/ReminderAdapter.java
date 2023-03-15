package android.bignerdranch.myapplication.ui.reminder;

import android.annotation.SuppressLint;
import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.ComplexResult;
import android.bignerdranch.myapplication.ApiAbout.SimpleResult;
import android.bignerdranch.myapplication.ReusableTools.MyRecyclerItemClickListener;
import android.bignerdranch.myapplication.ReusableTools.StringTool;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReminderAdapter extends RecyclerView.Adapter {

    private Retrofit mRetrofit;
    private Api mApi;

    private List<Reminder> mReminders;
    private String[] mData;
    private String mToken;
    private Context mContext;
    private MyRecyclerItemClickListener myRecyclerItemClickListener;

    public ReminderAdapter(List<Reminder> reminders, String[] data, String token, Context context) {
        mReminders = reminders;
        mData = data;
        mToken = token;
        mContext = context;
    }

    public List<Reminder> getList() {
        return mReminders;
    }

    @Override
    public ReminderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        return new ReminderHolder(layoutInflater, parent,mContext);
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView")int position) {
        Reminder reminder = mReminders.get(position);
        {
            mRetrofit = new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mApi = mRetrofit.create(Api.class);
        }
        Call<ComplexResult> likesInformation= mApi.getLikeInformation(mData[position],mToken);
        likesInformation.enqueue(new Callback<ComplexResult>() {
            @Override
            public void onResponse(Call<ComplexResult> call, Response<ComplexResult> response) {
                reminder.setId(mData[position]);
                reminder.setPostID(StringTool.getJsonString(response.body().getData(),"post_id"));
                reminder.setDate(StringTool.getJsonString(response.body().getData(),"UpdatedAt"));
                Call<ComplexResult> seekPost=mApi.seekPosts(reminder.getPostID(),mToken);
                seekPost.enqueue(new Callback<ComplexResult>() {
                    @Override
                    public void onResponse(Call<ComplexResult> call, Response<ComplexResult> response) {
                        reminder.setTitle(StringTool.getJsonString(response.body().getData(),"title"));
                        reminder.setPersonName(StringTool.getJsonString(response.body().getData(),"author_name"));
                        reminder.setProfile(StringTool.getJsonString(response.body().getData(),"avatar_path"));
                        ((ReminderHolder) holder).bind(reminder);
                    }

                    @Override
                    public void onFailure(Call<ComplexResult> call, Throwable t) {
                        Log.d("TAG","查找点赞用户信息：网络请求失败！");
                    }
                });
            }

            @Override
            public void onFailure(Call<ComplexResult> call, Throwable t) {
                Log.d("TAG","查找点赞信息：网络请求失败！");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mReminders.size();
    }

    public void setOnItemClickListener(MyRecyclerItemClickListener listener) {
        this.myRecyclerItemClickListener = listener;
    }

}
