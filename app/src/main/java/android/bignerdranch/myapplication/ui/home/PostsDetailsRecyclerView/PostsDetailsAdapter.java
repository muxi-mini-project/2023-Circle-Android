package android.bignerdranch.myapplication.ui.home.PostsDetailsRecyclerView;

import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.ComplexResult;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseHolder;
import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;
import android.bignerdranch.myapplication.ReusableTools.JsonTool;
import android.bignerdranch.myapplication.ui.home.PostsHolder;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostsDetailsAdapter extends RecyclerView.Adapter<BaseHolder> {

    private List<BaseItem> mList;//该Adapter管理的Posts的List
    private String PostsId;
    private String ProfilePath;
    private String mToken;

    private Retrofit mRetrofit;
    private Api mApi;

    private Context mContext;


    public PostsDetailsAdapter(List<BaseItem> List,Context context,String token) {
        mList = List;
        mContext=context;
        mToken=token;
    }


    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (ItemTypeDef.Type.getItemTypeByCode(viewType)){
            case COMMENT:
                return new CommentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false),ItemTypeDef.Type.COMMENT);//创建新CommentHolder
            case POSTS:
                return new PostsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_posts_layout,parent,false),ItemTypeDef.Type.POSTS,mContext,mToken);//创建一个新的PostsHolder
        }
        return null;
    }

    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        if (holder.getType()== ItemTypeDef.Type.POSTS){
            BaseItem item = mList.get(position);
            PostsHolder postsHolder = (PostsHolder) holder;
            {mRetrofit = new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
                mApi = mRetrofit.create(Api.class);}
            Call<ComplexResult> seekPostsResult = mApi.seekPosts(item.getID(),mToken);
            Log.d("TAG","PostsId="+item.getID());
            seekPostsResult.enqueue(new Callback<ComplexResult>() {
                @Override
                public void onResponse(Call<ComplexResult> call, Response<ComplexResult> response) {
                    item.setName(JsonTool.getJsonString(response.body().getData(),"author_name"));
                    item.setContent(JsonTool.getJsonString(response.body().getData(),"content"));
                    item.setTime(JsonTool.getJsonString(response.body().getData(),"UpdatedAt"));
                    item.setProfilePath(JsonTool.getJsonString(response.body().getData(),"avatar_path"));
                    postsHolder.bind(item);//把此帖子的id传递给holder
                }

                @Override
                public void onFailure(Call<ComplexResult> call, Throwable t) {
                    Log.d("TAG","PostsDetailsAdapter:网络请求失败");
                    item.setName("用户名");
                    item.setContent("看到这行文字时说明您发出的的网络请求失败了");
                    item.setTime(new Date().toString());
                    item.setProfilePath("ts1.cn.mm.bing.net/th/id/R-C.e89d745d8651a20a0bf9a72a2c8405c9?rik=VZLYlVQZt2Ny%2bA&riu=http%3a%2f%2fbpic.588ku.com%2felement_pic%2f01%2f37%2f85%2f80573c6529bb88f.jpg&ehk=MmfPCBHD8juSbs0fmBYdCJyBI9%2bs%2bItwvtsiKV7X4Ps%3d&risl=&pid=ImgRaw&r=0");
                    postsHolder.bind(item);

                }
            });
        }
        if (holder.getType()==ItemTypeDef.Type.COMMENT){
            BaseItem item=mList.get(position);
            CommentHolder commentHolder=(CommentHolder) holder;
            commentHolder.bind(item);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).typeCode();
    }

}
