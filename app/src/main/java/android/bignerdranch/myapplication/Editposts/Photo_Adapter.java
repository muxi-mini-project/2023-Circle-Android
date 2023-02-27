package android.bignerdranch.myapplication.Editposts;

import android.app.Activity;
import android.bignerdranch.myapplication.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Photo_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Uri> Adapter_UriList;    //存放每个图片的Uri的adapter中间人
    private final LayoutInflater inflater;
    private Activity context;

    private static final int HOLDER_TYPE_ONE = 0x00001;
    private static final int HOLDER_TYPE_TWO = 0x00002;

    public Photo_Adapter(Activity context,List<Uri> mList){
        this.context = context;
        this.Adapter_UriList = mList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType){
            case HOLDER_TYPE_ONE:
                return new Photo_Holder_1(inflater.inflate(R.layout.layout_added_photo,parent,false));
            case HOLDER_TYPE_TWO:
                return new Photo_Holder_2(inflater.inflate(R.layout.layout_add_photo,parent,false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof Photo_Holder_1){
            bindPhoto_Holder_1((Photo_Holder_1) holder,position);
        }else if(holder instanceof Photo_Holder_2){
            bindPhoto_Holder_2((Photo_Holder_2) holder,position);
        }
    }

    @Override
    public int getItemCount() {
        return Adapter_UriList.size()+1;
    }

    //通过glide将图片添加到viewholder内
    private void bindPhoto_Holder_1(Photo_Holder_1 holder,int position){
        Glide.with(context)
                .load(Adapter_UriList.get(position))
                .centerCrop()
                .into(holder.imageview1);
    }

    //第二种viewholder(内置＋号)的bind方法
    private void bindPhoto_Holder_2(final Photo_Holder_2 holder,int position){
        if(listSize()==9){
            holder.imageview2.setVisibility(View.GONE);//集合长度大于等于9张时，隐藏 图片
        }

        //添加图片的＋号的监听器已经在layout的onclick里实现了（调用popup方法）

    }

    //第一类ViewHolder,已添加图片
    class Photo_Holder_1 extends RecyclerView.ViewHolder {

        private final ImageView imageview1;

        public Photo_Holder_1(View itemView) {
            super(itemView);
            imageview1=(ImageView) itemView.findViewById(R.id.added_photo);
        }
    }

    //第二类ViewHolder，未添加图片，是个＋号
    class Photo_Holder_2 extends RecyclerView.ViewHolder{

        private final ImageView imageview2;

        public Photo_Holder_2(View itemView){
            super(itemView);
            imageview2=(ImageView) itemView.findViewById(R.id.add_photo);
        }
    }

    //将activity的UriList数据通过形参loarMoreDatas存入Adapter_UriList内，从而使adapter能够处理数据（类似于上拉加载数据）
    public void addMoreItem(List<Uri> loarMoreDatas) {
        Adapter_UriList.addAll(loarMoreDatas);
        notifyDataSetChanged();
    }

    //得到集合长度
    public int listSize() {
        int size = Adapter_UriList.size();
        return size;
    }


}
