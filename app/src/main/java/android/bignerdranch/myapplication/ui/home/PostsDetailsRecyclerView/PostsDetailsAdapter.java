package android.bignerdranch.myapplication.ui.home.PostsDetailsRecyclerView;

import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseHolder;
import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;
import android.bignerdranch.myapplication.ReusableTools.MyRecyclerItemClickListener;
import android.bignerdranch.myapplication.ui.home.PostsHolder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Retrofit;

public class PostsDetailsAdapter extends RecyclerView.Adapter<BaseHolder> {

    private MyRecyclerItemClickListener myRecyclerItemClickListener;

    private List<BaseItem> mList;//该Adapter管理的Posts的List
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
                return new PostsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_posts_layout,parent,false),ItemTypeDef.Type.POSTS,myRecyclerItemClickListener,mToken,mContext);//创建一个新的PostsHolder
        }
        return null;
    }

    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        if (holder.getType()== ItemTypeDef.Type.POSTS){
            BaseItem item = mList.get(position);
            PostsHolder postsHolder = (PostsHolder) holder;
            postsHolder.bind(item,item.getID());//此时这个item在启动PostsDetailsActivity时就已经装载了数据
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

    public void setOnItemClickListener(MyRecyclerItemClickListener listener){
        this.myRecyclerItemClickListener = listener;
    }

}
