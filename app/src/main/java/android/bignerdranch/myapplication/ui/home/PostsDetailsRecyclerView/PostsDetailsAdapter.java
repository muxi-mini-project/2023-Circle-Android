package android.bignerdranch.myapplication.ui.home.PostsDetailsRecyclerView;

import android.annotation.SuppressLint;
import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.ComplexResult;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseHolder;
import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;
import android.bignerdranch.myapplication.ReusableTools.MyRecyclerItemClickListener;
import android.bignerdranch.myapplication.ReusableTools.StringTool;
import android.bignerdranch.myapplication.ui.home.Posts.PostsHolder;
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

public class PostsDetailsAdapter extends RecyclerView.Adapter<BaseHolder> {

    private MyRecyclerItemClickListener myRecyclerItemClickListener;

    private String[] CommentData;//评论id数组
    private List<BaseItem> mList;//该Adapter管理的Posts的List
    private String mToken;

    private Retrofit mRetrofit;
    private Api mApi;

    private Context mContext;


    public PostsDetailsAdapter(List<BaseItem> List, Context context, String token, String[] data) {
        mList = List;
        mContext = context;
        mToken = token;
        CommentData = data;
    }


    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (ItemTypeDef.Type.getItemTypeByCode(viewType)) {
            case COMMENT:
                return new CommentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false), ItemTypeDef.Type.COMMENT,mContext,mToken,mList.get(0).getID());//创建新CommentHolder
            case POSTS:
                return new PostsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_posts_layout, parent, false), ItemTypeDef.Type.POSTS, myRecyclerItemClickListener, mToken, mContext);//创建一个新的PostsHolder
        }
        return null;
    }

    public void onBindViewHolder(@NonNull BaseHolder holder, @SuppressLint("RecyclerView")int position) {
        if (holder.getType() == ItemTypeDef.Type.POSTS) {
            BaseItem item = mList.get(position);
            PostsHolder postsHolder = (PostsHolder) holder;
            postsHolder.bind(item, item.getID());//此时这个item在启动PostsDetailsActivity时就已经装载了数据
        }
        if (holder.getType() == ItemTypeDef.Type.COMMENT) {
            Comment item = (Comment) mList.get(position);
            CommentHolder commentHolder = (CommentHolder) holder;
            {
                mRetrofit = new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                mApi = mRetrofit.create(Api.class);
            }//初始化mRetrofit
            Call<ComplexResult> seekCommentResult = mApi.seekComment(mToken, CommentData[position - 1]);
            seekCommentResult.enqueue(new Callback<ComplexResult>() {
                @Override
                public void onResponse(Call<ComplexResult> call, Response<ComplexResult> response) {
                    item.setContent(StringTool.getJsonString(response.body().getData(),"content"));
                    item.setTime(StringTool.getJsonString(response.body().getData(),"UpdatedAt"));
                    item.setPublisherID(StringTool.getJsonString(response.body().getData(),"user_id"));
                    item.setID(CommentData[position-1]);

                    Call<ComplexResult> userMsgResult=mApi.getUserMsg(item.getPublisherID(),mToken);
                    userMsgResult.enqueue(new Callback<ComplexResult>() {
                        @Override
                        public void onResponse(Call<ComplexResult> call, Response<ComplexResult> response) {
                            item.setProfilePath(StringTool.getJsonString(response.body().getData(),"AvatarPath"));
                            item.setCommenterName(StringTool.getJsonString(response.body().getData(),"Name"));
                            commentHolder.bind(item);
                        }

                        @Override
                        public void onFailure(Call<ComplexResult> call, Throwable t) {
                            Log.d("TAG","查找评论者用户信息：网络请求失败！");
                        }
                    });
                }

                @Override
                public void onFailure(Call<ComplexResult> call, Throwable t) {
                    Log.d("TAG", "查找评论具体信息：网络请求失败");
                }
            });
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

    public void setOnItemClickListener(MyRecyclerItemClickListener listener) {
        this.myRecyclerItemClickListener = listener;
    }

}
