package android.bignerdranch.myapplication.ui.mine.UserListAbout;

import android.annotation.SuppressLint;
import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.ComplexResult;
import android.bignerdranch.myapplication.ReusableTools.MyRecyclerItemClickListener;
import android.bignerdranch.myapplication.ReusableTools.StringTool;
import android.bignerdranch.myapplication.User_Information_Edit.User_Information;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserListAdapter extends RecyclerView.Adapter {
    private Retrofit mRetrofit;
    private Api mApi;

    private List<User_Information> mUsers;
    private String[] mData;
    private String mToken;
    private Context mContext;
    private MyRecyclerItemClickListener myRecyclerItemClickListener;

    public UserListAdapter(List<User_Information> users, String[] data, String token, Context context) {
        Log.d("TAG","创建Adapter");
        mUsers = users;
        mData = data;
        mToken = token;
        mContext = context;
    }

    public List<User_Information> getList() {
        return mUsers;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        Log.d("TAG","创建Holder");
        return new UserListHolder(layoutInflater, parent, mContext, mToken);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView")int position) {
        User_Information user=new User_Information();
        {
            mRetrofit = new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mApi = mRetrofit.create(Api.class);
        }
        Call<ComplexResult> userMsg=mApi.getUserMsg(mData[position],mToken);
        userMsg.enqueue(new Callback<ComplexResult>() {
            @Override
            public void onResponse(Call<ComplexResult> call, Response<ComplexResult> response) {
                user.setUserId(mData[position]);
                user.setProfile(StringTool.getJsonString(response.body().getData(),"AvatarPath"));
                user.setUser_Name(StringTool.getJsonString(response.body().getData(),"Name"));
                user.setSignature(StringTool.getJsonString(response.body().getData(),"Signature"));
                Log.d("TAG",user.getProfile());
                Log.d("TAG",user.getUser_Name());
                Log.d("TAG",user.getSignature());
                ((UserListHolder)holder).bind(user);
            }

            @Override
            public void onFailure(Call<ComplexResult> call, Throwable t) {
                Log.d("TAG","查询关注用户信息：网络请求失败！");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void setOnItemClickListener(MyRecyclerItemClickListener listener) {
        this.myRecyclerItemClickListener = listener;
    }
}
