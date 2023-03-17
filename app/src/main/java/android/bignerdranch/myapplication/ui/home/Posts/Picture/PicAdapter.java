package android.bignerdranch.myapplication.ui.home.Posts.Picture;

import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;
import android.bignerdranch.myapplication.ReusableTools.MyRecyclerItemClickListener;
import android.bignerdranch.myapplication.ui.reminder.ReminderHolder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PicAdapter extends RecyclerView.Adapter {

    private List<String> mPicPaths;
    private Context mContext;
    private MyRecyclerItemClickListener mMyRecyclerItemClickListener;

    public PicAdapter(List<String> picPaths, Context context){
        mPicPaths=picPaths;
        mContext=context;
    }

    @NonNull
    @Override
    public PicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PicHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_added_photo,
                parent, false),ItemTypeDef.Type.PIC,mMyRecyclerItemClickListener,mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        holder=(PicHolder)holder;
        ((PicHolder) holder).setPath(mPicPaths.get(position));
        ((PicHolder) holder).bind();
    }

    @Override
    public int getItemCount() {
        return mPicPaths.size();
    }
}
