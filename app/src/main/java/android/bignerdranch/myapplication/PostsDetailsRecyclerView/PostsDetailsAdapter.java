package android.bignerdranch.myapplication.PostsDetailsRecyclerView;

import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseHolder;
import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;
import android.bignerdranch.myapplication.ReusableTools.MyRecyclerItemClickListener;
import android.bignerdranch.myapplication.ui.home.PostsHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PostsDetailsAdapter extends RecyclerView.Adapter<BaseHolder> {

    private List<BaseItem> mList;//该Adapter管理的Posts的List



    public PostsDetailsAdapter(List<BaseItem> List) {
        mList = List;
    }


    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (ItemTypeDef.Type.getItemTypeByCode(viewType)){
            case COMMENT:
                return new CommentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false),ItemTypeDef.Type.COMMENT);//创建新CommentHolder
            case POSTS:
                return new PostsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_posts_layout,parent,false),ItemTypeDef.Type.POSTS);//创建一个新的PostsHolder
        }
        return null;
    }

    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        if (holder.getType()== ItemTypeDef.Type.POSTS){
            BaseItem item = mList.get(position);
            PostsHolder postsHolder = (PostsHolder) holder;
            postsHolder.bind(item);
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
