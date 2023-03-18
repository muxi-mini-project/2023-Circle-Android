package android.bignerdranch.myapplication.ui.home.Posts;

import android.annotation.SuppressLint;
import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.ComplexResult;
import android.bignerdranch.myapplication.ApiAbout.SimpleResult;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseFragment;
import android.bignerdranch.myapplication.ReusableTools.BaseHolder;
import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;
import android.bignerdranch.myapplication.ReusableTools.MyRecyclerItemClickListener;
import android.bignerdranch.myapplication.ReusableTools.StringTool;
import android.bignerdranch.myapplication.ui.home.SearchBoxHolder;
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

public class PostsAdapter extends RecyclerView.Adapter<BaseHolder> {

    private static Retrofit mRetrofit;
    private static Api mApi;
    private final List<BaseItem> mList;//该Adapter管理的Posts的List
    private MyRecyclerItemClickListener myRecyclerItemClickListener;
    private final String[] mData;
    private final String mToken;
    private final Context mContext;

    private final BaseFragment mFragment;

    public PostsAdapter(List<BaseItem> List, String[] data, String token, Context context, BaseFragment fragment) {
        mFragment = fragment;
        mContext = context;
        mList = List;
        mData = data;
        mToken = token;
    }

    public List<BaseItem> getList() {
        return mList;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (ItemTypeDef.Type.getItemTypeByCode(viewType)) {
            case SEARCH_BOX:
                return new SearchBoxHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_box,
                        parent, false), ItemTypeDef.Type.SEARCH_BOX, mContext, mToken, mFragment);
            case POSTS:
                PostsHolder holder=new PostsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_posts_layout,
                        parent, false), ItemTypeDef.Type.POSTS, myRecyclerItemClickListener, mToken, mContext);
                return holder;
            //在此处将token和context都传递给PostsHolder
        }
        return null;
    }

    public void onBindViewHolder(@NonNull BaseHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder.getType() == ItemTypeDef.Type.POSTS) {

            Posts item = (Posts) mList.get(position);
            PostsHolder postsHolder = (PostsHolder) holder;
            {
                mRetrofit = new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                mApi = mRetrofit.create(Api.class);
            }
            //由于是初次启动，要先通过网络请求返回体得到数据并装载后再启动Holder

            Call<ComplexResult> seekPostsResult = mApi.seekPosts(mData[position - 1], mToken);
            seekPostsResult.enqueue(new Callback<ComplexResult>() {
                @Override
                public void onResponse(Call<ComplexResult> call, Response<ComplexResult> response) {
                    {
                        if (response.body() != null) {
                            item.setName(StringTool.getJsonString(response.body().getData(), "author_name"));//用户名
                            item.setTitle(StringTool.getJsonString(response.body().getData(), "title"));//标题
                            item.setContent(StringTool.getJsonString(response.body().getData(), "content"));//内容
                            item.setTime(StringTool.getJsonString(response.body().getData(), "UpdatedAt"));
                            item.setProfilePath(StringTool.getJsonString(response.body().getData(), "avatar_path"));
                            item.setID(StringTool.getJsonString(response.body().getData(), "ID"));//帖子id
                            item.setLikesNumber(StringTool.getJsonString(response.body().getData(), "likes"));//点赞数
                            item.setCommentNumber(StringTool.getJsonString(response.body().getData(), "comment_no"));
                            item.setPublisherId(StringTool.getJsonString(response.body().getData(), "author_id"));
                            for (int i = 1; !StringTool.getJsonString(response.body().getData(), "file_path" + i).equals(""); i++) {
                                item.addPicPath(StringTool.getJsonString(response.body().getData(), "file_path" + i));
                            }
                        } else {
                            Log.d("TAG", "查找帖子：未收到返回体");
                        }
                    }
                    //由于服务器给的帖子数据当中并没有这个帖子是否已经点赞的数据，所以要写一个嵌套请求
                    //在这个请求完成后调用bind方法
                    Call<SimpleResult> isLikeResult = mApi.getIsLike(item.getID(), mToken);
                    isLikeResult.enqueue(new Callback<SimpleResult>() {
                        @Override
                        public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                            item.setLikes(response.body().getMsg().equals("yes"));
                            postsHolder.bind(item, item.getID());//把此帖子的id传递给holder
                        }

                        @Override
                        public void onFailure(Call<SimpleResult> call, Throwable t) {
                            Log.d("TAG", "是否点赞：网络请求失败！");
                        }
                    });

                }

                @Override
                public void onFailure(Call<ComplexResult> call, Throwable t) {
                    Log.d("TAG", "网络请求失败");
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }//返回List的长度

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).typeCode();
    }//返回当前这个item的类别（这里应该是Posts或者SearchBox）

    public void setOnItemClickListener(MyRecyclerItemClickListener listener) {
        this.myRecyclerItemClickListener = listener;
    }


}
