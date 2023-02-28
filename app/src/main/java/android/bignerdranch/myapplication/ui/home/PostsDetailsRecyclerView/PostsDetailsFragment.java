package android.bignerdranch.myapplication.ui.home.PostsDetailsRecyclerView;

import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ui.home.Posts;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PostsDetailsFragment extends Fragment {
    private RecyclerView mPostsDetailsRecyclerView;
    private PostsDetailsAdapter mPostsDetailsAdapter;

    private ImageButton mBackBtn;

    private String PostsPublisherName;
    private String PostsTime;
    private String PostsContent;


    public void setPostsPublisherName(String postsPublisherName) {
        PostsPublisherName = postsPublisherName;
    }

    public void setPostsTime(String postsTime) {
        PostsTime = postsTime;
    }

    public void setPostsContent(String postsContent) {
        PostsContent = postsContent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.post_details, container, false);

        mBackBtn = (ImageButton) view.findViewById(R.id.back_btn);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mPostsDetailsRecyclerView = (RecyclerView) view
                .findViewById(R.id.recyclerview_posts_details);
        mPostsDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        upDateUI();

        return view;
    }

    private void upDateUI() {
        CommentLab commentLab = CommentLab.get();
        List<BaseItem> mList = new ArrayList<>();
        mList.add(getMyPosts());
        for (Comment e : commentLab.get_mComment()) {
            mList.add(e);
        }


        mPostsDetailsAdapter = new PostsDetailsAdapter(mList);//将mList装载入Adapter中
        mPostsDetailsRecyclerView.setAdapter(mPostsDetailsAdapter);//给该recyclerview设置adapter
    }

    private Posts getMyPosts() {
        return new Posts(PostsPublisherName, PostsTime, PostsContent);
    }

}