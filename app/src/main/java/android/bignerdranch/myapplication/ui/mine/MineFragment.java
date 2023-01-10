package android.bignerdranch.myapplication.ui.mine;

import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.SpaceItemDecoration;
import android.bignerdranch.myapplication.User_Information_Edit.Activity_User_Information;
import android.bignerdranch.myapplication.User_Information_Edit.User_Information;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MineFragment extends Fragment {
    private RecyclerView mPostsRecyclerView;
    private MyPostsAdapter mMyPostsAdapter;
    private LinearLayout mUserInformation;
    private TextView mUserName;
    private TextView mUserSignature;


    private User_Information mUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){
        View view =inflater.inflate(R.layout.layout_mine,container,false);

        mUser=new User_Information();

        mUserInformation=(LinearLayout)view
                .findViewById(R.id.user_information);
        mUserInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Activity_User_Information.class);
                startActivity(intent);
            }
        });

        mUserName=(TextView) view
                .findViewById(R.id.user_name);
        mUserSignature=(TextView)view
                .findViewById(R.id.user_signature);

        mPostsRecyclerView=(RecyclerView) view
                .findViewById(R.id.recyclerview_mine);
        mPostsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        upDateUI();

        return view;
    }

    private void upDateUI() {
        MyPostsLab myPostsLab = MyPostsLab.get();
        List<MyPosts> myPostsList = myPostsLab.get_mPosts();

        mPostsRecyclerView.addItemDecoration(new SpaceItemDecoration(20));//设置item之间的间隔为20
        mMyPostsAdapter = new MyPostsAdapter(myPostsList,mUser);//将myPostsList装载入Adapter中
        mPostsRecyclerView.setAdapter(mMyPostsAdapter);//给该recyclerview设置adapter
    }

}