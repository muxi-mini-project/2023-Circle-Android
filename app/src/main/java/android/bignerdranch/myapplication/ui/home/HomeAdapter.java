package android.bignerdranch.myapplication.ui.home;

import android.annotation.SuppressLint;
import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.PostsResult;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseHolder;
import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;
import android.bignerdranch.myapplication.ReusableTools.MyRecyclerItemClickListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeAdapter extends RecyclerView.Adapter<BaseHolder> {

    private List<BaseItem> mList;//该Adapter管理的Posts的List
    private MyRecyclerItemClickListener myRecyclerItemClickListener;
    private String[] mData;
    private String mToken;

    private static Retrofit mRetrofit;
    private static Api mApi;

    public List<BaseItem> getList() {
        return mList;
    }

    public HomeAdapter(List<BaseItem> List,String[] data,String token) {
        mList = List;
        mData=data;
        mToken=token;
    }


    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (ItemTypeDef.Type.getItemTypeByCode(viewType)){
            case SEARCH_BOX:
                return new SearchBoxHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_box,parent,false),ItemTypeDef.Type.SEARCH_BOX);
            case POSTS:
                return new PostsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_posts_layout,parent,
                        false),ItemTypeDef.Type.POSTS,myRecyclerItemClickListener,mToken);//创建一个新的PostsHolder
        }
        return null;
    }

    public void onBindViewHolder(@NonNull BaseHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder.getType()== ItemTypeDef.Type.POSTS){
            BaseItem item = mList.get(position);
            PostsHolder postsHolder = (PostsHolder) holder;
            {mRetrofit = new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
                mApi = mRetrofit.create(Api.class);}
            Call<PostsResult> seekPostsResult = mApi.seekPosts(mData[position-1],mToken);
            seekPostsResult.enqueue(new Callback<PostsResult>() {
                @Override
                public void onResponse(Call<PostsResult> call, Response<PostsResult> response) {
                    JsonObject jsonObject = response.body().getData();
                    item.setContent(jsonObject.getAsJsonPrimitive("content").toString().replaceAll("\"",""));
                    //将字符串中的双引号去掉
                    item.setTime(jsonObject.getAsJsonPrimitive("UpdatedAt").toString().replaceAll("\"",""));
                    postsHolder.bind(item,mData[position-1]);//把此帖子的id传递给holder
                }

                @Override
                public void onFailure(Call<PostsResult> call, Throwable t) {
                    Log.d("TAG","网络请求失败");
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();//返回List的长度
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).typeCode();//返回当前这个item的类别（这里应该是Posts或者SearchBox）
    }

    public void setOnItemClickListener(MyRecyclerItemClickListener listener){
        this.myRecyclerItemClickListener = listener;
    }

}
