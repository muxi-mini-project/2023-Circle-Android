package android.bignerdranch.myapplication.ui.mine.User_Information_Edit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.bignerdranch.myapplication.R;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import java.io.File;

public class UserImageChange {
    private final Activity activity;
    //private Context context;
    private static File file;
    private Uri uri;
    private final String fileName = "IMG_head.jpg";
    private static final int TAKE_PHOTO = 0X66;
    private static final int PICK_PHOTO = 0X88;

    public UserImageChange(Activity activity) {
        this.activity = activity;
    }

    /**
     * 点击出现弹窗
     */
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        AlertDialog dialog = builder.create();
        View v = View.inflate(activity, R.layout.dialog, null);
        TextView CameraText = (TextView) v.findViewById(R.id.take_photo);
        TextView AlbumText = (TextView) v.findViewById(R.id.album);
        TextView CancelText = (TextView) v.findViewById(R.id.cancel);
        CameraText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartCamera();
                dialog.dismiss();
            }
        });
        AlbumText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoosePhoto();
                dialog.dismiss();
            }
        });
        CancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.setView(v);
        dialog.show();

    }


    /**
     * 调用相机
     */
    public void StartCamera(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("Demo","进行到high");
            TakePhoto_high();   //高版本的SDK相机的调用方法
        }else{
            Log.d("Demo","进行到low");
            TakePhoto_low();   //低版本的SDK相机的调用方法
        }

    }

    /*  针对低版本的SDK */
    private void TakePhoto_low() {
        file = new File(activity.getFilesDir(), fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();     //先获得父文件，再获得路径
        }
        uri = Uri.fromFile(file);
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent1.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent1, TAKE_PHOTO);


    }

    /*针对android6.0后的所有版本，使用FileProvider来处理uri*/
    private void TakePhoto_high() {
        file = new File(activity.getFilesDir(), fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        uri = FileProvider.getUriForFile(activity, "com.bignerdranch.android.myapplication.fileprovider", file);
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent1.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent1, TAKE_PHOTO);
    }

    /**
     * 调用相册经行选择
     */

    private void ChoosePhoto() {
        Intent intent2 = new Intent(Intent.ACTION_PICK);
        file = new File(activity.getFilesDir(), fileName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            uri = FileProvider.getUriForFile(activity, "com.bignerdranch.android.myapplication.fileprovider", file);   //高版本，使用FileProvider
        }else{
            uri = Uri.fromFile(file);     //低版本，直接得到uri
        }
        intent2.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, uri);
        intent2.setDataAndType(uri, "image/*");
        activity.startActivityForResult(intent2, PICK_PHOTO);
    }





    /**
     * 回调处理方法
     */

    //低版本的获得image的path的方法
    public String handleImageBeforeKitKat(Uri uri) {
        String path = getImagePath(uri, null);
        return path;
    }

    //高版本获得image的path的方法
    //image分别有三种形式：documents、media（多媒体）、contents，分三种情况获得image的path
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)   //作用是让以下代码编译通过
    public  String handleImageOnKitKat(Uri uri) {
        String path = null;
        if (DocumentsContract.isDocumentUri(activity, uri)) {
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


    //cursor是游标，movetofirst是移到第一个，从第一个开始检索
    @SuppressLint("Range")
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = activity.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    //缩放图片
    private static Bitmap getScaledBitmap(String path, int destWidth, int destHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();  //options可以获得bitmap的数据并进行处理
        options.inJustDecodeBounds = true;           //decodeFile时不返回bitmap
        BitmapFactory.decodeFile(path,options);      //decode是解码的意思，通过path找到图像，并进行解码，把数据存入option中
        float srcWidth = options.outWidth;           //src代表原本的图像，通过option把图像的宽高放到srcWidth和srcHeight中
        float srcHeight = options.outHeight;

        int inSampleSize = 1;                        //缩放比例

        //dest即为destination，目标图像大小，要把原图像的大小缩放为目标图像的大小
        if(srcHeight>destHeight||srcWidth>destWidth){
            float ww = srcWidth/destWidth;           //宽度比例
            float hh = srcHeight/destHeight;         //高度比例
            inSampleSize = Math.round(hh>ww?hh:ww);  //取宽高比例中最大的那个设为缩放比例
        }

        options = new BitmapFactory.Options();       //覆盖之前的options，decodeFile时返回bitmap
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFile(path,options);
    }


    public static Bitmap getScaledBitmap(String path, Activity activity){        //同名方法，重载
        Point size = new Point();                                                //通过point来获得目标宽高
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return getScaledBitmap(path,size.x,size.y);
    }

    public File getFile(){
        return file;
    }

}
