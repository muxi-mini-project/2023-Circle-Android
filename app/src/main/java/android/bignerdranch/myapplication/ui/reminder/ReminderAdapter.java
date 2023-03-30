package android.bignerdranch.myapplication.ui.reminder;

import android.annotation.SuppressLint;
import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.ComplexResult;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;
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

    private final List<Reminder> mReminders;
    private final int t;//1为点赞，2为评论
    private final String[] mData;
    private final String mToken;
    private final Context mContext;
    private Retrofit mRetrofit;
    private Api mApi;
    private MyRecyclerItemClickListener myRecyclerItemClickListener;

    public ReminderAdapter(List<Reminder> reminders, String[] data, String token, Context context, int mInt) {
        mReminders = reminders;
        mData = data;
        mToken = token;
        mContext = context;
        t = mInt;
    }

    public List<Reminder> getList() {
        return mReminders;
    }

    @Override
    public ReminderHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ReminderHolder((LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder,
                parent, false)), ItemTypeDef.Type.REMINDER, myRecyclerItemClickListener, mContext);
    }

    public void onBindViewHolder(RecyclerView.ViewHolder mHolder, @SuppressLint("RecyclerView") int position) {
        Reminder reminder = mReminders.get(position);
        ReminderHolder holder = (ReminderHolder) mHolder;
        {
            mRetrofit = new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mApi = mRetrofit.create(Api.class);
        }
        Call<ComplexResult> reminderInformation=null;
        switch (t){
            case 1:reminderInformation = mApi.getLikeInformation(mData[position], mToken);break;
            case 2:reminderInformation =mApi.getCommentInformation(mData[position],mToken);break;
        }
        reminderInformation.enqueue(new Callback<ComplexResult>() {
            @Override
            public void onResponse(Call<ComplexResult> call, Response<ComplexResult> response) {
                reminder.setId(mData[position]);
                reminder.setPostID(StringTool.getJsonString(response.body().getData(), "post_id"));
                reminder.setUserID(StringTool.getJsonString(response.body().getData(), "user_id"));
                reminder.setDate(StringTool.getJsonString(response.body().getData(), "CreatedAt"));
                if (t == 2) {
                    reminder.setContent(StringTool.getJsonString(response.body().getData(), "content"));
                }
                Call<ComplexResult> userMsg = mApi.getUserMsg(reminder.getUserID(), mToken);
                userMsg.enqueue(new Callback<ComplexResult>() {
                    @Override
                    public void onResponse(Call<ComplexResult> call, Response<ComplexResult> response) {
                        if (response.body() != null) {
                            reminder.setPersonName(StringTool.getJsonString(response.body().getData(), "Name"));
                            reminder.setProfile(StringTool.getJsonString(response.body().getData(), "AvatarPath"));
                            if (t==1){
                                Call<ComplexResult> postMsg = mApi.seekPosts(reminder.getPostID(), mToken);
                                postMsg.enqueue(new Callback<ComplexResult>() {
                                    @Override
                                    public void onResponse(Call<ComplexResult> call, Response<ComplexResult> response) {
                                        if (response.body() != null) {
                                            reminder.setTitle(StringTool.getJsonString(response.body().getData(), "title"));
                                            holder.bind(reminder, t);
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<ComplexResult> call, Throwable t) {
                                        Log.d("TAG", "Reminder:查找用户帖子信息");
                                    }
                                });
                            }else {
                                holder.bind(reminder,t);
                            }

                        } else {
                            Log.d("TAG", "Reminder，查找帖子：未收到返回体");
                        }
                    }

                    @Override
                    public void onFailure(Call<ComplexResult> call, Throwable t) {
                        Log.d("TAG", "查找点赞用户信息：网络请求失败！");
                    }
                });

            }

            @Override
            public void onFailure(Call<ComplexResult> call, Throwable t) {
                Log.d("TAG", "查找点赞信息：网络请求失败！");
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
