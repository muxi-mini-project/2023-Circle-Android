package android.bignerdranch.myapplication.ui.home.NewPosts;

import android.annotation.SuppressLint;
import android.bignerdranch.myapplication.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import kotlin.jvm.internal.Intrinsics;

public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int ADD_ITEM;
    private final int PIC_ITEM;
    private OnItemClickListener onItemClickListener;
    private List data=null;
    private final int maxNum;

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
            return (RecyclerView.ViewHolder)(new AddViewHolder(view));
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            Intrinsics.checkNotNullExpressionValue(view, "view");
            return (RecyclerView.ViewHolder)(new PicViewHolder(view));
        }
    }

    public int getItemCount() {
        if(data!=null){return this.data.size() < this.maxNum ? this.data.size() + 1 : this.data.size();}
        return 0;
    }

    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder instanceof AddViewHolder) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View it) {
                    onItemClickListener  = PhotoAdapter.this.onItemClickListener;
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemAddClick(position);
                    }

                }
            });

            if (this.data.size() >= this.maxNum) {
                holder.itemView.setVisibility(View.GONE);
            } else {
                holder.itemView.setVisibility(View.VISIBLE);
                holder.itemView.setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
                    public final void onClick(View it) {
                        onItemClickListener = PhotoAdapter.this.onItemClickListener;
                        if (onItemClickListener!= null) {
                            onItemClickListener.onItemAddClick(position);
                        }

                    }
                }));
            }

        } else {

            Glide.with(holder.itemView.getContext()).load((String)this.data.get(position)).into(((PicViewHolder)holder).getPic());
            ((PicViewHolder)holder).getPic().setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
                public final void onClick(View it) {
                    OnItemClickListener var10000 = PhotoAdapter.this.onItemClickListener;
                    if (var10000 != null) {
                        var10000.onItemPicClick(position);
                    }

                }
            }));
            ((PicViewHolder)holder).getDel().setOnClickListener((View.OnClickListener)(new View.OnClickListener() {
                public void onClick(View it) {
                    OnItemClickListener var10000 = PhotoAdapter.this.onItemClickListener;
                    if (var10000 != null) {
                        var10000.onItemDelClick(position);
                    }

                }
            }));
        }

    }

    public int getItemViewType(int position) {
        if (this.data.size() == this.maxNum) {
            return this.PIC_ITEM;
        } else {
            return position + 1 == this.getItemCount() ? this.ADD_ITEM : this.PIC_ITEM;
        }
    }

    public final void setOnMyClickListener(@Nullable OnItemClickListener onClickListener) {
        this.onItemClickListener = onClickListener;
    }

    public PhotoAdapter(@NotNull List data, int maxNum) {
        super();
        this.data = data;
        this.maxNum = maxNum;
        this.ADD_ITEM = 7;
        this.PIC_ITEM = 8;
    }

    // $FF: synthetic method
    public void setOnItemClickListener(OnItemClickListener var1) {
        this.onItemClickListener = var1;
    }


    public class AddViewHolder extends RecyclerView.ViewHolder {
        public AddViewHolder(@NotNull View itemView) {
            super(itemView);

        }
    }


    public  class PicViewHolder extends RecyclerView.ViewHolder {
        @NotNull
        private ImageView pic;
        @NotNull
        private ImageView del;

        @NotNull
        public ImageView getPic() {
            return this.pic;
        }

        @NotNull
        public ImageView getDel() {
            return this.del;
        }

        public PicViewHolder(@NotNull View itemView) {
            super(itemView);
            this.pic = (ImageView)itemView.findViewById(R.id.ivImage);
            this.del = (ImageView)itemView.findViewById(R.id.ivDelete);
        }
    }


    public interface OnItemClickListener {
        void onItemAddClick(int var1);

        void onItemDelClick(int var1);

        void onItemPicClick(int var1);
    }
}

