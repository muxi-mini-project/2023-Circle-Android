package android.bignerdranch.myapplication.ui.home;

import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseHolder;
import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class PostsHolder extends BaseHolder {

    private TextView mNameView;
    private TextView mDateView;
    private ImageButton mIsFollow;
    private TextView mContent;
    private ImageButton mIsLikes;

    private Posts mPosts;

    public PostsHolder(View itemView, ItemTypeDef.Type type) {
        super(itemView,type);

        mNameView = (TextView) itemView.findViewById(R.id.publisher_name);
        mDateView = (TextView) itemView.findViewById(R.id.publish_time);
        mContent = (TextView) itemView.findViewById(R.id.publish_content);
        mIsFollow = (ImageButton) itemView.findViewById(R.id.is_followed_btn);
        mIsLikes =(ImageButton) itemView.findViewById(R.id.is_likes_btn);

        mIsLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosts.setLikes(!mPosts.isLikes());
                bind(mPosts);
            }
        });

        mIsFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosts.setFollow(!mPosts.isFollow());
                bind(mPosts);
            }
        });
    }

    public void bind(BaseItem item) {
        mPosts =(Posts) item;
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
