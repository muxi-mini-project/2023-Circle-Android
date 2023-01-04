package android.bignerdranch.myapplication.PostsRecyclerView;

import android.bignerdranch.myapplication.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Posts> mPosts;//该Adapter管理的Posts的List

    public PostsAdapter(List<Posts> posts) {
        mPosts = posts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.posts_layout, parent, false);
        RecyclerView.ViewHolder aHolder = new PostsHolder(layoutInflater, parent);;
        return aHolder;
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Posts posts = mPosts.get(position);
        PostsHolder postsHolder = (PostsHolder) holder;
        postsHolder.bind(posts);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class PostsHolder extends RecyclerView.ViewHolder {

        private TextView mNameView;
        private TextView mDateView;
        private Button mIsFollow;
        private TextView mContent;

        private Posts mPosts;

        public PostsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.posts_layout, parent, false));

            mNameView=(TextView) itemView.findViewById(R.id.publisher_name);
            mDateView=(TextView) itemView.findViewById(R.id.publish_time);
            mIsFollow=(Button) itemView.findViewById(R.id.is_follow);
            mContent=(TextView) itemView.findViewById(R.id.publish_content);

            mIsFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPosts.setFollow(!mPosts.isFollow());
                    bind(mPosts);
                }
            });
        }

        public void bind(Posts posts) {
            mPosts = posts;
            mNameView.setText(mPosts.getName());
            mDateView.setText(mPosts.getReleaseTime().toString());
            if (mPosts.isFollow()) {
                mIsFollow.setText("已关注");
            } else {
                mIsFollow.setText("未关注");
            }
            mContent.setText(mPosts.getContent());
        }

    }//Holder内部类
}
