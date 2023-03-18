package android.bignerdranch.myapplication.ui.home.PostsDetailsRecyclerView;

import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.SimpleResult;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseHolder;
import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CommentHolder extends BaseHolder {


    private TextView mNameView;
    private TextView mDateView;
    private TextView mContent;
    private ImageButton mProfile;
    private Button mDeleteButton;

    private Comment mComment;
    private String mPostId;
    private String mToken;
    private Context mContext;

    private Retrofit mRetrofit;
    private Api mApi;

    public CommentHolder(View itemView, ItemTypeDef.Type type, Context context,String token,String postId) {
        super(itemView, type);

        mPostId=postId;
        mToken=token;
        mContext = context;

        {
            mRetrofit = new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mApi = mRetrofit.create(Api.class);
        }

        mNameView = (TextView) itemView.findViewById(R.id.commenter_name);
        mDateView = (TextView) itemView.findViewById(R.id.comment_time);
        mContent = (TextView) itemView.findViewById(R.id.comment_content);
        mProfile = (ImageButton) itemView.findViewById(R.id.profile_pic_comment);

        mDeleteButton = (Button) itemView.findViewById(R.id.delete_comment_button);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog2 = new AlertDialog.Builder(mContext)
                        .setTitle("确定删除这个评论吗？")
                        .setMessage("如果您是这条评论的发布者的话，您可以稍作考虑；否则您没有权限这么做")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Call<SimpleResult> deleteResult = mApi.delComment(mToken, mComment.getID(),mPostId);
                                deleteResult.enqueue(new Callback<SimpleResult>() {
                                    @Override
                                    public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                                        if (!response.body().getMsg().equals("没有权限.")){
                                            Toast.makeText(mContext, "删除评论成功！", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<SimpleResult> call, Throwable t) {
                                        Log.d("TAG", "删除帖子：网络请求失败！");
                                    }
                                });
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("TAG", "取消删除评论");
                            }
                        })
                        .create();
                alertDialog2.show();
            }
        });

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
