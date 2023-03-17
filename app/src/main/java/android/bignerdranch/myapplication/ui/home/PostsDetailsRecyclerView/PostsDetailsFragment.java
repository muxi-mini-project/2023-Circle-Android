package android.bignerdranch.myapplication.ui.home.PostsDetailsRecyclerView;

import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.ComplexResult;
import android.bignerdranch.myapplication.ApiAbout.SimpleResult;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.StringTool;
import android.bignerdranch.myapplication.ReusableTools.MyRecyclerItemClickListener;
import android.bignerdranch.myapplication.ui.home.Posts.Posts;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostsDetailsFragment extends Fragment {
    private RecyclerView mPostsDetailsRecyclerView;
    private PostsDetailsAdapter mPostsDetailsAdapter;

    private ImageButton mBackBtn;
    private Button mDeleteButton;
    private Button mCommentBtn;
    private EditText mCommentEdit;

    private Retrofit mRetrofit;
    private Api mApi;

    private List<BaseItem> mList = new ArrayList<>();

    private String[] data;//评论id数组
    private String mPostsID;
    private String mToken;


    public void setPostsID(String id) {
        mPostsID = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.layout_post_details, container, false);

        {
            mRetrofit = new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mApi = mRetrofit.create(Api.class);
        }//初始化Retrofit

        BaseActivity homeActivity = (BaseActivity) getActivity();//得到一个可以调用getMyToken的对象
        mToken = homeActivity.getMyToken();

        mPostsDetailsRecyclerView = (RecyclerView) view
                .findViewById(R.id.recyclerview_posts_details);
        mPostsDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mDeleteButton = (Button) view.findViewById(R.id.delete_button);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog2 = new AlertDialog.Builder(getContext())
                        .setTitle("确定删除这个帖子吗？")
                        .setMessage("您可以稍作考虑")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Call<SimpleResult> deleteResult = mApi.delPost(mToken, mPostsID);
                                deleteResult.enqueue(new Callback<SimpleResult>() {
                                    @Override
                                    public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                                        Toast.makeText(getActivity(), "删除帖子成功！", Toast.LENGTH_SHORT).show();
                                        onStop();
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
                                Log.d("TAG", "取消删除帖子");
                            }
                        })
                        .create();
                alertDialog2.show();
            }
        });

        mBackBtn = (ImageButton) view.findViewById(R.id.back_btn);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mCommentEdit = (EditText) view.findViewById(R.id.comment_reply_edit);

        mCommentBtn = (Button) view.findViewById(R.id.comment_button);
        mCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCommentEdit.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "请输入评论！", Toast.LENGTH_SHORT).show();
                } else {
                    Call<SimpleResult> commentResult= mApi.commentPost(mToken,mPostsID,false,1,
                            mCommentEdit.getText().toString());
                    commentResult.enqueue(new Callback<SimpleResult>() {
                        @Override
                        public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                            Toast.makeText(getActivity(),"评论成功！",Toast.LENGTH_SHORT).show();
                            mCommentEdit.setText("");
                        }

                        @Override
                        public void onFailure(Call<SimpleResult> call, Throwable t) {
                            Log.d("TAG","评论失败");
                        }
                    });
                }
            }
        });


        upDateUI();

        return view;
    }

    private void upDateUI() {
        Posts item = new Posts();
        Call<ComplexResult> seekPostsResult = mApi.seekPosts(mPostsID, mToken);
        seekPostsResult.enqueue(new Callback<ComplexResult>() {
            @Override
            public void onResponse(Call<ComplexResult> call, Response<ComplexResult> response) {
                {
                    if (!StringTool.getJsonString(response.body().getData(),"file_path1").equals("")){
                        for (int i=1;!StringTool.getJsonString(response.body().getData(),"file_path"+i).equals("");i++){
                            Log.d("TAG","file_path"+i);
                            item.addPicPath(StringTool.getJsonString(response.body().getData(),"file_path"+i));
                        }
                    }
                    item.setName(StringTool.getJsonString(response.body().getData(), "author_name"));
                    item.setContent(StringTool.getJsonString(response.body().getData(), "content"));
                    item.setTime(StringTool.getJsonString(response.body().getData(), "UpdatedAt"));
                    item.setProfilePath(StringTool.getJsonString(response.body().getData(), "avatar_path"));
                    item.setID(StringTool.getJsonString(response.body().getData(), "ID"));
                    item.setTitle(StringTool.getJsonString(response.body().getData(),"title"));
                    mList.add(item);
                }//接收当前帖子的数据

                Call<SimpleResult> getCommentResult = mApi.CommentOfPosts(mToken, item.getID());
                getCommentResult.enqueue(new Callback<SimpleResult>() {
                    @Override
                    public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                        if (response.body() != null) {
                            data = response.body().getData();
                        } else {
                            data = new String[0];
                        }
                        if (data != null) {
                            setAdapterAbout();
                        }
                    }

                    @Override
                    public void onFailure(Call<SimpleResult> call, Throwable t) {
                        Log.d("TAG", "查找评论id数组：网络请求失败！");
                    }
                });

            }

            @Override
            public void onFailure(Call<ComplexResult> call, Throwable t) {
                Log.d("TAG", "帖子详情：请求失败");
            }
        });

    }

    private void setAdapterAbout() {
        CommentLab commentLab = CommentLab.get(data.length);
        for (Comment e : commentLab.getComments()) {
            mList.add(e);
        }
        mPostsDetailsAdapter = new PostsDetailsAdapter(mList, getContext(), mToken, data);//将mList装载入Adapter中
        mPostsDetailsRecyclerView.setAdapter(mPostsDetailsAdapter);//给该recyclerview设置adapter
        mPostsDetailsAdapter.setOnItemClickListener(new MyRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });

    }
}


