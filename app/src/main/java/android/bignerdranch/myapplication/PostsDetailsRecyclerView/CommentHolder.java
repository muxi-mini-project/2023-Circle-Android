package android.bignerdranch.myapplication.PostsDetailsRecyclerView;

import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseHolder;
import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class CommentHolder extends BaseHolder {

    private TextView mNameView;
    private TextView mDateView;
    private TextView mContent;
    private ImageButton mIsLikes;

    private Comment mComment;

    public CommentHolder(View itemView, ItemTypeDef.Type type) {
        super(itemView,type);

        mNameView = (TextView) itemView.findViewById(R.id.commenter_name);
        mDateView = (TextView) itemView.findViewById(R.id.comment_time);
        mContent = (TextView) itemView.findViewById(R.id.comment_content);
        mIsLikes =(ImageButton) itemView.findViewById(R.id.comment_likes_btn);

        mIsLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mComment.setLikes(!mComment.isLikes());
                bind(mComment);
            }
        });
    }

    public void bind(BaseItem item) {
        mComment =(Comment) item;
        mNameView.setText(mComment.getName());
        mDateView.setText(mComment.getTime().toString());
        if (mComment.isLikes()){
            mIsLikes.setBackgroundResource(R.mipmap.likes_yes);
        }
        else {
            mIsLikes.setBackgroundResource(R.mipmap.likes_not);
        }
        mContent.setText(mComment.getContent());
    }

}
