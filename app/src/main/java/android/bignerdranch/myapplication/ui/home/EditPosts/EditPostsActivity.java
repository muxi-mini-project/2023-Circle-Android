package android.bignerdranch.myapplication.ui.home.EditPosts;

import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.SimpleResult;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.bignerdranch.myapplication.User_Information_Edit.UserImageChange;
import android.bignerdranch.myapplication.ui.home.Posts;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.User_Information_Edit.User_Information;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.widget.Toast;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditPostsActivity extends BaseActivity {

    private static final int TAKE_PHOTO = 0X99;
    private static final int PICK_PHOTO = 0X98;
    private Posts mPosts;
    private User_Information user_information;
    private Retrofit mRetrofit;
    private Api mApi;
    private ImageButton BackButton;
    private ImageButton ReleaseButton;
    private EditText EditTitle;
    private EditText EditContent;
    private Button add_photos;
    private List<Uri> UriList = new ArrayList<>();   //?????????????????????Uri
    private String path;

    private RecyclerView mPhotosRecyclerview;
    private Photo_Adapter adapter;
    private UserImageChange u = new UserImageChange(EditPostsActivity.this);

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, EditPostsActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosts = new Posts();
        user_information = User_Information.getUser_information();
        setContentView(R.layout.layout_editposts);

        mPhotosRecyclerview = (RecyclerView) findViewById(R.id.recyclerview_photos);
        mPhotosRecyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new Photo_Adapter(EditPostsActivity.this, UriList);
        mPhotosRecyclerview.setAdapter(adapter);

        BackButton = (ImageButton) findViewById(R.id.backbutton_editposts);
        ReleaseButton = (ImageButton) findViewById(R.id.releasebutton_editposts);
        EditContent = (EditText) findViewById(R.id.posts_content_field);
        EditTitle = (EditText) findViewById(R.id.posts_title_field);

        //???????????????????????????????????????
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //??????EditText????????????????????????????????????
        EditContent.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        //??????EditText?????????????????????
        EditContent.setSingleLine(false);

        //?????????????????????False
        EditContent.setHorizontallyScrolling(false);

        //?????????????????????url???retrofit
        mRetrofit = new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = mRetrofit.create(Api.class);
        //?????????????????????
        ReleaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EditContent.getText().toString().trim().equals("")) {
                    Toast.makeText(EditPostsActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
                } else {
                    Call<SimpleResult> apiResult = mApi.publishPostsNotPic(getMyToken(), "no", "????????????", EditTitle.getText().toString(), EditContent.getText().toString());
                    apiResult.enqueue(new Callback<SimpleResult>() {
                        @Override
                        public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                            Toast.makeText(EditPostsActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<SimpleResult> call, Throwable t) {
                            Toast.makeText(EditPostsActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Permission.checkPermission(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
