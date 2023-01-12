package android.bignerdranch.myapplication.ui.home;

import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseHolder;
import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<BaseHolder> {

    private List<BaseItem> mList;//该Adapter管理的Posts的List

    public HomeAdapter(List<BaseItem> List) {
        mList = List;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (ItemTypeDef.Type.getItemTypeByCode(viewType)){
            case ONE_TEXT:
                return new SearchBoxHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_box,parent,false),ItemTypeDef.Type.ONE_TEXT);
            case TWO_TEXT:
                return new PostsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_posts_layout,parent,false),ItemTypeDef.Type.TWO_TEXT);//创建一个新的PostsHolder
        }
        return null;
    }

    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        if (holder.getType()== ItemTypeDef.Type.TWO_TEXT){
            BaseItem item = mList.get(position);
            PostsHolder postsHolder = (PostsHolder) holder;
            postsHolder.bind(item);
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
