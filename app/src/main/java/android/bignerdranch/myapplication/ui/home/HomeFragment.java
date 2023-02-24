package android.bignerdranch.myapplication.ui.home;

import android.bignerdranch.myapplication.PostsDetailsRecyclerView.PostsDetailsActivity;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.MyRecyclerItemClickListener;
import android.bignerdranch.myapplication.ReusableTools.SpaceItemDecoration;
import android.bignerdranch.myapplication.ui.home.NewPosts.NewPostsActivity;
import android.content.Intent;
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

public class HomeFragment extends Fragment {
    private RecyclerView mHomeRecyclerView;
    private HomeAdapter mHomeAdapter;
    private ImageButton newPostsBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        View view =inflater.inflate(R.layout.layout_home,container,false);

        mHomeRecyclerView=(RecyclerView) view
                .findViewById(R.id.recyclerview_home);
        mHomeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        newPostsBtn=(ImageButton) view.findViewById(R.id.new_posts_btn);
        newPostsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = NewPostsActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });


        upDateUI();

        return view;
    }

    private void upDateUI() {
        PostsLab postsLab = PostsLab.get();
        List<BaseItem> mList=new ArrayList<>();
        mList.add(new SearchBox());
        for (Posts e:postsLab.get_mPosts()){
            mList.add(e);
        }

        mHomeRecyclerView.addItemDecoration(new SpaceItemDecoration(20));//设置item之间的间隔为20
        mHomeAdapter = new HomeAdapter(mList);//将mList装载入Adapter中
        mHomeRecyclerView.setAdapter(mHomeAdapter);//给该recyclerview设置adapter

        this.mHomeAdapter.setOnItemClickListener(new MyRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent= PostsDetailsActivity.newIntent(getActivity(),mHomeAdapter.getList().get(position).getName()
                ,mHomeAdapter.getList().get(position).getTime()
                ,mHomeAdapter.getList().get(position).getContent());
                startActivity(intent);
            }
        });
    }

}