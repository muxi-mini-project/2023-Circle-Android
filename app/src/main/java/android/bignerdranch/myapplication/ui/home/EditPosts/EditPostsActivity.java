package android.bignerdranch.myapplication.ui.home.EditPosts;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.SimpleResult;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.bignerdranch.myapplication.User_Information_Edit.UserImageChange;
import android.bignerdranch.myapplication.ui.home.Posts;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.User_Information_Edit.User_Information;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


import android.widget.TextView;

import android.widget.Toast;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


//目前能实现的是：点击“添加图片”的按钮后，能够一张一张地添加图片，最多显示9张。但是不可以删除图片，重新进入内容编辑界面即可让所有已添加的图片消失。同时也不能点击图片进行图片详情预览。
public class EditPostsActivity extends BaseActivity implements PhotoAdapter.OnItemClickListener {

    private Posts mPosts;
    private User_Information user_information;
    private Retrofit mRetrofit;
    private Api mApi;
    private ImageButton BackButton;
    private ImageButton ReleaseButton;
    private EditText EditTitle;
    private EditText EditContent;

    private TextView take_photo;
    private TextView choose_from_album;
    private TextView cancel;
    private Button add_photo;      //添加图片的按钮


    private PhotoAdapter photoadapter;
    private RecyclerView photo_recyclerView;
    private GridLayoutManager gridManager;
    private ArrayList<String> imagePathList=new ArrayList<String>();  //存储所有的图片的路径，和photoadapter相联系
    private String mFilePath;      //存相机拍出来的照的路径


    private final int TAKE_PHOTO = 1;  //拍照
    private final int PICK_PHOTO = 2; //相册选取

    private PopupWindow popupWindow;
    private View popupView;





    /**
     * 调用相机
     */
    public void StartCamera(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("Demo","进行到high");
            TakePhoto_high();;
        }else{
            Log.d("Demo","进行到low");
            TakePhoto_low();
        }

    }

    /*  针对低版本的SDK */
    private void TakePhoto_low() {
        String fileName=Math.random()*100+".jpg";   //生成一个随机的filename
        File file = new File(EditPostsActivity.this.getFilesDir(), fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri uri = Uri.fromFile(file);
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent1.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent1, TAKE_PHOTO);


    }

    /*针对android6.0后的所有版本，使用FileProvider来处理uri*/
    private void TakePhoto_high() {
        String fileName=Math.random()*100+".jpg";      //生成一个随机的filename（每张图片的filename不可重复）
        File file = new File(EditPostsActivity.this.getFilesDir(), fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri uri = FileProvider.getUriForFile(EditPostsActivity.this, "com.bignerdranch.android.myapplication.fileprovider", file);
        mFilePath=file.getPath();                   //获得路径
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent1.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent1, TAKE_PHOTO);            //开启相机
    }




    /**
     * todo 对拍照、相册选择图片的返回结果进行处理
     * @param requestCode 返回码，用于确定是哪个 Activity 返回的数据
     * @param resultCode 返回结果，一般如果操作成功返回的是 RESULT_OK
     * @param data 返回对应 activity 返回的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            // 表示 调用照相机拍照返回
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        FileInputStream is = new FileInputStream(mFilePath);
                        // 把流解析成bitmap,此时就得到了清晰的原图
                        Bitmap imageBitmap = BitmapFactory.decodeStream(is);
                        Bitmap newImageBitmap = scaleBitmap(imageBitmap,(float)0.5); //压缩图片
                        imagePathList.add(mFilePath);                  //将排出的照片的路径存入imagePathList中
                        photoadapter.notifyDataSetChanged();           //与imagePathList相联系的photoadapter进行数据的刷新，使得添加的图片能够显示在recyclerview中

                                          //此处调用接口把图片上传到服务器
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            //从相册中选择图片返回
            case PICK_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        Uri uri = data.getData();              //获得uri
                        Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                        Bitmap newImageBitmap = scaleBitmap(imageBitmap,(float)0.5); //压缩图片
                        String path=handleImageOnKitKat(uri);            //通过这个方法获得相册中选择的图片的图片路径
                        imagePathList.add(path);                         //将得到的照片的路径存入imagePathList中
                        photoadapter.notifyDataSetChanged();             //与imagePathList相联系的photoadapter进行数据的刷新，使得添加的图片能够显示在recyclerview中

                                          //此处调用接口把图片上传到服务器
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }


    /**
     * todo  uri 转 file 没用到？或许网络请求的时候可以用到，通过uri转换成file形式（看后端需要接收什么数据）
     * @param uri
     * @return
     */
    public String UriToFile(Uri uri) {
        String[] filePc = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, filePc, null, null, null);
        cursor.moveToFirst();
        Log.i(TAG, "UriToFile: 22"+cursor);
        int col = cursor.getColumnIndex(filePc[0]);
        String pic = cursor.getString(col);
        cursor.close();
        return pic;
    }

    /**
     * todo 压缩图片
     * @param origin
     * @param ratio
     * @return
     */
    public Bitmap scaleBitmap(Bitmap origin, float ratio) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        return newBM;
    }

    /**
     * todo 创建弹窗(用于上传图片方式选择）
     * author wang
     * *@param view
     */
    private void createPopupWindow() {
        if(popupView==null){
            popupView = getLayoutInflater().inflate(R.layout.dialog,null);
        }
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);
//        popupWindow.showAsDropDown(view, view.getWidth(),view.getHeight());
        popupWindow.showAtLocation(findViewById(R.id.layout_editposts), Gravity.BOTTOM,0,0);  //底部显示弹窗
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.white));

        setAlpha(0.3f);
        //把背景还原
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setAlpha(1.0f);
            }
        });

        initPopupView();

        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                //6.0才用动态权限
                StartCamera();
            }
        });

        choose_from_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                ChoosePhoto();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    /**
     * todo 初始化弹窗的控件
     */
    private void initPopupView() {
        take_photo = popupView.findViewById(R.id.take_photo);
        choose_from_album = popupView.findViewById(R.id.album);
        cancel = popupView.findViewById(R.id.cancel);

    }

    /**
     * todo 自定义方法，遮罩层
     * @param f
     */
    private void setAlpha(float f) {
        WindowManager.LayoutParams lp =getWindow().getAttributes();
        lp.alpha = f;
        getWindow().setAttributes(lp);
    }

    /**
     * todo handler
     */


    /**
     * todo 上传图片(api)
     */


    /**
     * todo 创建适配器
     */
    private void createAdapter(){
        gridManager = new GridLayoutManager(EditPostsActivity.this,3);
        photo_recyclerView.setLayoutManager(gridManager);
        photoadapter = new PhotoAdapter(imagePathList,9);
        photo_recyclerView.setAdapter(photoadapter);
        itemClick();
    }
    /**
     * todo 点击图片进行放大预览    因为工具库无法导入而暂时寄咯
     */
    private void itemClick() {
        /**省略**/
    }


    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, EditPostsActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_editposts);



        BackButton = (ImageButton) findViewById(R.id.backbutton_editposts);
        ReleaseButton = (ImageButton) findViewById(R.id.releasebutton_editposts);
        EditContent = (EditText) findViewById(R.id.posts_content_field);
        EditTitle = (EditText) findViewById(R.id.posts_title_field);

        //添加图片的按钮
        add_photo=(Button) findViewById(R.id.add_button);
        add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //权限检查
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                    if (!checkPermission()) {
                        requestPermissions(PICK_PHOTO);
                    }
                    if (!checkPermission()) {
                        requestPermissions(TAKE_PHOTO);
                    }
                }

                //出现弹窗
                createPopupWindow();
            }
        });

        //创建recyclerview和adapter
        photo_recyclerView=(RecyclerView) findViewById(R.id.photo_recyclerView);
        createAdapter();

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
        mRetrofit = new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = mRetrofit.create(Api.class);
        //发布键的监听器
        ReleaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EditContent.getText().toString().trim().equals("")) {
                    Toast.makeText(EditPostsActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                } else {
                    Call<SimpleResult> apiResult = mApi.publishPostsNotPic(getMyToken(), "no", "日常唠嗑", EditTitle.getText().toString(), EditContent.getText().toString());
                    apiResult.enqueue(new Callback<SimpleResult>() {
                        @Override
                        public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                            Toast.makeText(EditPostsActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<SimpleResult> call, Throwable t) {
                            Toast.makeText(EditPostsActivity.this, "请求失败！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }



    //打开相册的方法
    private void ChoosePhoto() {
        Uri uri;
        File file;
        String fileName=Math.random()*100+".jpg";    //来个随机的filename
        Intent intent2 = new Intent(Intent.ACTION_PICK);
        file = new File(EditPostsActivity.this.getFilesDir(), fileName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            uri = FileProvider.getUriForFile(EditPostsActivity.this, "com.bignerdranch.android.myapplication.fileprovider", file);
        }else{
            uri = Uri.fromFile(file);
        }
        intent2.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, uri);
        intent2.setDataAndType(uri, "image/*");
        startActivityForResult(intent2, PICK_PHOTO);
    }


    //高版本下获得从相册中选择的图片的路径的方法（低版本现在也没啥人用了，就不写了）
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public  String handleImageOnKitKat(Uri uri) {
        String path = null;
        if (DocumentsContract.isDocumentUri(EditPostsActivity.this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if (uri.getAuthority().equals("com.android.providers.media.documents")) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                path = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if (uri.getAuthority().equals("com.android.providers.downloads.documents")) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content: //downloads/public_downloads"), Long.valueOf(docId));
                path = getImagePath(contentUri, null);
            }
        } else if (uri.getScheme().equalsIgnoreCase("content")) {
            path = getImagePath(uri, null);

        } else if (uri.getScheme().equalsIgnoreCase("file")) {
            path = uri.getPath();

        }
        return path;
    }

    @SuppressLint("Range")
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = EditPostsActivity.this.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


    public boolean checkPermission() {
        boolean haveCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean haveWritePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        return haveCameraPermission && haveWritePermission;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissions(int request) {
        switch (request) {
            case TAKE_PHOTO:
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, TAKE_PHOTO);
                break;
            case PICK_PHOTO:
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_PHOTO);
                break;
        }
    }

    @Override
    public void onItemAddClick(int var1) {

    }

    @Override
    public void onItemDelClick(int position){

    }

    @Override
    public void onItemPicClick(int var1) {

    }


}
