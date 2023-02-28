package android.bignerdranch.myapplication.ui.home.EditPosts;

import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.SignInResult;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.bignerdranch.myapplication.ui.home.Posts;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.User_Information_Edit.User_Information;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditPostsActivity extends BaseActivity {

    public static final int TAKE_PHOTO=1;
    public static final int CROP_PHOTO=2;

    private Posts mPosts;
    private User_Information user_information;

    private Retrofit mRetrofit;
    private Api mApi;

    private ImageButton BackButton;
    private ImageButton ReleaseButton;
    private EditText EditTitle;
    private EditText EditContent;

    private Button add_photos;
    private Uri imageUri;
    private ImageView picture;
    private List<Uri> UriList;   //存放每个图片的Uri

    private RecyclerView mPhotosRecyclerview;
    private Photo_Adapter adapter;

    //点击添加图片的＋号，建立一个弹窗
    public void Popup(View view){

        View popupview_photo_pictures=getLayoutInflater().inflate(R.layout.popupview_photo_pictures,null);
        Button Take_Photos=popupview_photo_pictures.findViewById(R.id.take_photos);
        Button ChooseFromAlbum=popupview_photo_pictures.findViewById(R.id.choose_from_album);
        picture=(ImageView)findViewById(R.id.add_photo);

        PopupWindow window=new PopupWindow(popupview_photo_pictures,ViewGroup.LayoutParams.WRAP_CONTENT
                ,ViewGroup.LayoutParams.WRAP_CONTENT,true);
        window.showAsDropDown(view);

        Take_Photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputImage=new File(Environment.getExternalStorageDirectory(),"tempImage.jpg");
                try {
                    if(outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }catch (IOException e) {
                    e.printStackTrace();
                }

                imageUri=Uri.fromFile(outputImage);
                UriList.add(imageUri);
                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);//启动相机程序
                window.dismiss();

            }
        });

        ChooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputImage=new File(Environment.getExternalStorageDirectory(),"output_image.jpg");
                try{
                    if(outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                imageUri=Uri.fromFile(outputImage);
                UriList.add(imageUri);
                Intent intent=new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                intent.putExtra("crop",true);
                intent.putExtra("scale",true);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,CROP_PHOTO);
                window.dismiss();
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mPosts=new Posts();
        user_information=User_Information.getUser_information();
        setContentView(R.layout.layout_editposts);

        mPhotosRecyclerview=(RecyclerView)findViewById(R.id.recyclerview_photos);
        mPhotosRecyclerview.setLayoutManager(new GridLayoutManager(this,3));
        adapter=new Photo_Adapter(EditPostsActivity.this,UriList);
        mPhotosRecyclerview.setAdapter(adapter);

        BackButton=(ImageButton) findViewById(R.id.backbutton_editposts);
        ReleaseButton=(ImageButton) findViewById(R.id.releasebutton_editposts);
        EditContent=(EditText) findViewById(R.id.posts_content_field);
        EditTitle=(EditText)findViewById(R.id.posts_title_field);

        //返回键的监听器，记得填起来
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //设置EditText的显示方式为多行文本输入
        EditContent.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        //改变EditText默认的单行模式
        EditContent.setSingleLine(false);

        //水平滚动设置为False
        EditContent.setHorizontallyScrolling(false);

        //创建一个指向该url的retrofit
        mRetrofit=new Retrofit.Builder().baseUrl("http://43.138.61.49:8899/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi=mRetrofit.create(Api.class);
        //发布键的监听器
        ReleaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<SignInResult> apiResult=mApi.publishPosts(getToken(),"no","日常唠嗑",EditTitle.getText().toString(),EditContent.getText().toString());
                apiResult.enqueue(new Callback<SignInResult>() {
                    @Override
                    public void onResponse(Call<SignInResult> call, Response<SignInResult> response) {
                        Toast.makeText(EditPostsActivity.this,response.body().getMsg(),Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<SignInResult> call, Throwable t) {
                        Toast.makeText(EditPostsActivity.this,"请求失败！",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public static Intent newIntent(Context packageContext) {
        return  new Intent(packageContext, EditPostsActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO);//启动裁剪程序
                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                        adapter.addMoreItem(UriList);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
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
