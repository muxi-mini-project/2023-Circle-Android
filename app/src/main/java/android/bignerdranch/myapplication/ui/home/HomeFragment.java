package android.bignerdranch.myapplication.ui.home;

import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseItem;
import android.bignerdranch.myapplication.ReusableTools.SpaceItemDecoration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView mPostsRecyclerView;
    private HomeAdapter mPostsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        View view =inflater.inflate(R.layout.layout_home,container,false);

        mPostsRecyclerView=(RecyclerView) view
                .findViewById(R.id.recyclerview_home);
        mPostsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPostsRecyclerView.setItemViewCacheSize(16);

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

        mPostsRecyclerView.addItemDecoration(new SpaceItemDecoration(20));//设置item之间的间隔为20
        mPostsAdapter = new HomeAdapter(mList);//将mList装载入Adapter中
        mPostsRecyclerView.setAdapter(mPostsAdapter);//给该recyclerview设置adapter
    }

}