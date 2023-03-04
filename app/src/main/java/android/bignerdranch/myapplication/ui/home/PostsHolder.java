package android.bignerdranch.myapplication.ui.home;

import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseHolder;
import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;
import android.bignerdranch.myapplication.ReusableTools.MyRecyclerItemClickListener;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class PostsHolder extends BaseHolder implements View.OnClickListener{



    private TextView mNameView;
    private TextView mDateView;
    private ImageButton mIsFollow;
    private TextView mContent;
    private ImageButton mIsLikes;

    private Posts mPosts;
    private String mPostsId;
    private String mToken;

    public PostsHolder(View itemView, ItemTypeDef.Type type,MyRecyclerItemClickListener myRecyclerItemClickListener,String token) {
        super(itemView,type,myRecyclerItemClickListener);

        {mToken=token;//传入token
        mNameView = (TextView) itemView.findViewById(R.id.publisher_name);
        mDateView = (TextView) itemView.findViewById(R.id.publish_time);
        mContent = (TextView) itemView.findViewById(R.id.publish_content);
        mIsFollow = (ImageButton) itemView.findViewById(R.id.is_followed_btn);
        mIsLikes =(ImageButton) itemView.findViewById(R.id.is_likes_btn);}

        mIsLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //改变mPosts，并再次加载posts
                mPosts.setLikes(!mPosts.isLikes());
                bind(mPosts,mPostsId);
            }
        });

        mIsFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //同理
                mPosts.setFollow(!mPosts.isFollow());
                bind(mPosts,mPostsId);
            }
        });
    }
    //给帖子列表（Home界面使用的构造器）

    public PostsHolder(View itemView, ItemTypeDef.Type type) {
        super(itemView,type);

        {mNameView = (TextView) itemView.findViewById(R.id.publisher_name);
        mDateView = (TextView) itemView.findViewById(R.id.publish_time);
        mContent = (TextView) itemView.findViewById(R.id.publish_content);
        mIsFollow = (ImageButton) itemView.findViewById(R.id.is_followed_btn);
        mIsLikes =(ImageButton) itemView.findViewById(R.id.is_likes_btn);}//给设置帖子各项数据

        mIsLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosts.setLikes(!mPosts.isLikes());
                bind(mPosts,mPostsId);
            }
        });

        mIsFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosts.setFollow(!mPosts.isFollow());
                bind(mPosts,mPostsId);
            }
        });
    }//给帖子详情界面使用的构造器

    public void bind(BaseItem item,String id) {
        mPosts =(Posts) item;
        mPostsId=id;//传入当前这个帖子的id
        mNameView.setText(mPosts.getPublisherName());
        mDateView.setText(mPosts.getReleaseTime().toString());
        if (mPosts.isFollow()) {
            mIsFollow.setBackgroundResource(R.mipmap.is_follow_followed);
        } else {
            mIsFollow.setBackgroundResource(R.mipmap.is_follow_not_followed);
        }
        if (mPosts.isLikes()){
            mIsLikes.setBackgroundResource(R.mipmap.likes_yes);
        }
        else {
            mIsLikes.setBackgroundResource(R.mipmap.likes_not);
        }
        mContent.setText(mPosts.getContent());
    }

}
