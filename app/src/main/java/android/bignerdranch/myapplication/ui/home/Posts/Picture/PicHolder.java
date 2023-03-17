package android.bignerdranch.myapplication.ui.home.Posts.Picture;

import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseHolder;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;
import android.bignerdranch.myapplication.ReusableTools.MyRecyclerItemClickListener;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class PicHolder extends BaseHolder {

    private ImageView mImageView;

    private Context mContext;
    private String path;

    public PicHolder(View itemView, ItemTypeDef.Type type, MyRecyclerItemClickListener myRecyclerItemClickListener, Context context) {
        super(itemView, type, myRecyclerItemClickListener);

        mContext=context;
        mImageView=itemView.findViewById(R.id.pic_image);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void bind(){
        Glide.with(mContext)
                .load("http://" + path)
                .centerCrop()
                .into(mImageView);
    }
}
