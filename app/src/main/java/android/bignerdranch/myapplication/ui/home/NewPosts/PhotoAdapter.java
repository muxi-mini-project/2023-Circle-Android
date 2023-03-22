package android.bignerdranch.myapplication.ui.home.NewPosts;

import android.annotation.SuppressLint;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.MyRecyclerItemClickListener;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import kotlin.jvm.internal.Intrinsics;

public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int ADD_ITEM;
    private final int PIC_ITEM;
    private final int TAKE_PHOTO = 1;  //拍照
    private final int PICK_PHOTO = 2; //相册选取
    private final int maxNum;
    private EditPostsActivity mEditPostsActivity;
    private MyRecyclerItemClickListener myRecyclerItemClickListener;
    private List data = null;

    public PhotoAdapter(@NotNull List data, int maxNum, EditPostsActivity context) {
        super();
        this.data = data;
        this.maxNum = maxNum;
        this.ADD_ITEM = 7;
        this.PIC_ITEM = 8;
        this.mEditPostsActivity = context;
    }

    public final int getADD_ITEM() {
        return this.ADD_ITEM;
    }

    public final int getPIC_ITEM() {
        return this.PIC_ITEM;
    }

    @NotNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View view;
        if (viewType == this.ADD_ITEM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_item, parent, false);
            Intrinsics.checkNotNullExpressionValue(view, "view");
            return (RecyclerView.ViewHolder) (new AddViewHolder(view, myRecyclerItemClickListener));
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            Intrinsics.checkNotNullExpressionValue(view, "view");
            return (RecyclerView.ViewHolder) (new PicViewHolder(view));
        }
    }

    public int getItemCount() {
        if (data != null) {
            return this.data.size() < this.maxNum ? this.data.size() + 1 : this.data.size();
        }
        return 0;
    }

    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder instanceof AddViewHolder) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View it) {

                    //test
                    Log.d("test", "onBindVIewholder");
                    

                    //权限检查
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (!mEditPostsActivity.checkPermission()) {
                            mEditPostsActivity.requestPermissions(PICK_PHOTO);
                        }
                        if (!mEditPostsActivity.checkPermission()) {
                            mEditPostsActivity.requestPermissions(TAKE_PHOTO);
                        }
                    }

                    //出现弹窗
                    mEditPostsActivity.createPopupWindow();
                }
            });

            if (this.data.size() >= this.maxNum) {
                holder.itemView.setVisibility(View.GONE);
            } else {
                holder.itemView.setVisibility(View.VISIBLE);

            }

        } else {
            Glide.with(holder.itemView.getContext()).load((String) this.data.get(position)).into(((PicViewHolder) holder).getPic());
        }

    }

    public int getItemViewType(int position) {
        if (this.data.size() == this.maxNum) {
            return this.PIC_ITEM;
        } else {
            return position + 1 == this.getItemCount() ? this.ADD_ITEM : this.PIC_ITEM;
        }
    }

    public void setMyRecyclerItemClickListener(MyRecyclerItemClickListener listener) {
        this.myRecyclerItemClickListener = listener;
    }

    public class AddViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MyRecyclerItemClickListener mMmyRecyclerItemClickListener;

        public AddViewHolder(@NotNull View itemView, MyRecyclerItemClickListener myRecyclerItemClickListener) {
            super(itemView);
            mMmyRecyclerItemClickListener = myRecyclerItemClickListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            //test
            Toast.makeText(mEditPostsActivity, "ViewHolder", Toast.LENGTH_SHORT).show();

            if (myRecyclerItemClickListener != null) {
                mMmyRecyclerItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    public class PicViewHolder extends RecyclerView.ViewHolder {
        @NotNull
        private ImageView pic;
        @NotNull
        private ImageView del;

        public PicViewHolder(@NotNull View itemView) {
            super(itemView);
            this.pic = (ImageView) itemView.findViewById(R.id.ivImage);
            this.del = (ImageView) itemView.findViewById(R.id.ivDelete);
        }

        @NotNull
        public ImageView getPic() {
            return this.pic;
        }

        @NotNull
        public ImageView getDel() {
            return this.del;
        }
    }
}

