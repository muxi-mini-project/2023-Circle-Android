package android.bignerdranch.myapplication.Editposts;

import android.bignerdranch.myapplication.BaseActivity;
import android.bignerdranch.myapplication.PostsRecyclerView.Posts;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.User_Information_Edit.User_Information;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class EditpostsActivity extends BaseActivity {

    private Posts mPosts;
    private User_Information user_information=User_Information.getUser_information();

    private ImageButton BackButton;
    private ImageButton ReleaseButton;
    private EditText mPosts_content_field;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mPosts=new Posts();
        setContentView(R.layout.layout_editposts);

        BackButton=(ImageButton) findViewById(R.id.backbutton_editposts);
        ReleaseButton=(ImageButton) findViewById(R.id.releasebutton_editposts);
        mPosts_content_field=(EditText) findViewById(R.id.posts_content_field);

        //返回键的监听器，记得填起来
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //发布键的监听器
        ReleaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosts.setContent(mPosts_content_field.getText().toString());  //传入内容
                mPosts.setFollow(true);
                mPosts.setPublisherName(user_information.getUser_Name().toString());
            }
        });


        //设置EditText的显示方式为多行文本输入
        mPosts_content_field.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        //改变EditText默认的单行模式
        mPosts_content_field.setSingleLine(false);

        //水平滚动设置为False
        mPosts_content_field.setHorizontallyScrolling(false);
    }
}
