package android.bignerdranch.myapplication.ui.home.PostsDetailsRecyclerView;

import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseHolder;
import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


public class CommentHolder extends BaseHolder {

    private TextView mNameView;
    private TextView mDateView;
    private TextView mContent;
    private ImageButton mProfile;
    private ImageButton mIsLikes;

    private Comment mComment;

    private Context mContext;

    public CommentHolder(View itemView, ItemTypeDef.Type type, Context context) {
        super(itemView, type);

        mContext = context;

        mNameView = (TextView) itemView.findViewById(R.id.commenter_name);
        mDateView = (TextView) itemView.findViewById(R.id.comment_time);
        mContent = (TextView) itemView.findViewById(R.id.comment_content);
        mProfile = (ImageButton) itemView.findViewById(R.id.profile_pic_comment);

    }

    public void bind(BaseItem item) {
        mComment = (Comment) item;
        mNameView.setText(mComment.getName());
        mDateView.setText(mComment.getTime());
        {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.recyangle)
                    .circleCropTransform();
            Glide.with(mContext)
                    .load("http://" + mComment.getProfilePath())
                    .apply(options)
                    .into(mProfile);
        }//设置头像
        mContent.setText(mComment.getContent());
    }


}
