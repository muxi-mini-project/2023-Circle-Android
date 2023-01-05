package android.bignerdranch.myapplication.PostsRecyclerView;

import android.bignerdranch.myapplication.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostsFragment extends Fragment {

    private RecyclerView mPostsRecyclerView;
    private PostsAdapter mPostsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        View view =inflater.inflate(R.layout.posts_recyclerview,container,false);

        mPostsRecyclerView=(RecyclerView) view
                .findViewById(R.id.posts_recyclerview);
        mPostsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        upDateUI();

        return view;
    }

    private void upDateUI() {
        PostsLab postsLab = PostsLab.get();
        List<Posts> postsList = postsLab.get_mPosts();

        mPostsRecyclerView.addItemDecoration(new SpaceItemDecoration(20));//设置item之间的间隔为20
        mPostsAdapter = new PostsAdapter(postsList);//将postsList装载入Adapter中
        mPostsRecyclerView.setAdapter(mPostsAdapter);//给该recyclerview设置adapter
    }
}
