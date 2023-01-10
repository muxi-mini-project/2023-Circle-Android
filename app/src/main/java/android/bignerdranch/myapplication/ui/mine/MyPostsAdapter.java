package android.bignerdranch.myapplication.ui.mine;

import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.User_Information_Edit.User_Information;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyPostsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MyPosts> mMyPosts;//该Adapter管理的Posts的List
    private User_Information mUser;//该界面所属的user

    public MyPostsAdapter(List<MyPosts> myPosts,User_Information user) {
        mMyPosts = myPosts;
        mUser=user;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder aHolder = new MyPostsHolder(layoutInflater, parent);//创建一个新的MyPostsHolder
        return aHolder;
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyPosts myPosts = mMyPosts.get(position);
        MyPostsHolder postsHolder = (MyPostsHolder) holder;
        postsHolder.bind(myPosts);
    }

    @Override
    public int getItemCount() {
        return mMyPosts.size();
    }

    public class MyPostsHolder extends RecyclerView.ViewHolder {

        private TextView mNameView;
        private TextView mDateView;
        private TextView mContent;

        private MyPosts mMyPosts;

        public MyPostsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_my_posts_layout, parent, false));

            mNameView=(TextView) itemView.findViewById(R.id.publisher_name);
            mDateView=(TextView) itemView.findViewById(R.id.publish_time);
            mContent=(TextView) itemView.findViewById(R.id.publish_content);

            //监听器应在此处设置
        }

        public  void bind(MyPosts myPosts) {//数据绑定
            mMyPosts = myPosts;
            mNameView.setText(mUser.getUser_Name());
            mDateView.setText(mMyPosts.getReleaseTime().toString());
            mContent.setText(mMyPosts.getContent());
        }

    }//Holder内部类
}
