package android.bignerdranch.myapplication.User_Information_Edit;


import android.Manifest;
import android.app.Activity;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.bignerdranch.myapplication.R;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.io.FileNotFoundException;

public class Activity_User_Information extends BaseActivity {

    private static final int TAKE_PHOTO = 0X66;
    private static final int PICK_PHOTO = 0X88;

    private ImageButton BackBtn;

    private ImageButton profile_picture;
    private String path;
    private UserImageChange u=new UserImageChange(Activity_User_Information.this);
    private FragmentManager mFragmentManager;
    private Fragment_InformationCard fragment;


    public UserImageChange getU(){
        return  u;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_information_edit);



        mFragmentManager=getSupportFragmentManager();
        fragment=(Fragment_InformationCard) mFragmentManager.findFragmentById(R.id.user_informationcard_fragmentcontainer);

        if(fragment==null){
            fragment=new Fragment_InformationCard();
            mFragmentManager.beginTransaction()
                    .add(R.id.user_informationcard_fragmentcontainer,fragment)
                    .commit();
        }



        BackBtn=(ImageButton) findViewById(R.id.back_btn);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_User_Information.this.finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        profile_picture=Fragment_InformationCard.getProfile_picture();
        if (resultCode == RESULT_OK) {
            if (requestCode == TAKE_PHOTO) {
                if (u.getFile() == null || !u.getFile().exists()) {
                    profile_picture.setImageBitmap(null);
                } else {
                    Bitmap bitmap = u.getScaledBitmap(u.getFile().getPath(), Activity_User_Information.this);
                    profile_picture.setImageBitmap(bitmap);
                    fragment.savePhotos(u.getFile().getPath());
                }

            }else if(requestCode==PICK_PHOTO){
                Uri uri = data.getData();
                Activity_User_Information.this.revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                Log.d("TRy", uri.toString());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    path = u.handleImageOnKitKat(uri);
                } else {
                    path = u.handleImageBeforeKitKat(uri);
                }

                Bitmap bitmap = BitmapFactory.decodeFile(path);
                Log.d("相册",path);
                profile_picture.setImageBitmap(bitmap);
                fragment.savePhotos(path);
            } else {
                Log.d("Demo", "结果无");
            }
        }

    }

    /*动态权限请求*/
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
}
