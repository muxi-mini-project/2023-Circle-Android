package android.bignerdranch.myapplication.User_Information_Edit;


import android.Manifest;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.bignerdranch.myapplication.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.ComplexResult;
import android.bignerdranch.myapplication.ApiAbout.SimpleResult;
import android.bignerdranch.myapplication.ReusableTools.StringTool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;

public class Activity_User_Information extends BaseActivity {

    private static final int TAKE_PHOTO = 0X66;
    private static final int PICK_PHOTO = 0X88;
    public static SharedPreferences sharedPreferences;
    private final String fileName = "IMG_selfControl.jpg";
    private ImageButton BackBtn;
    private Button ConfirmBtn;
    private ImageButton mIsMaleButton;
    private ImageButton mIsFemaleButton;
    private ImageButton mUnselectedButton;
    private EditText mUser_name_field;
    private EditText mSignature_field;
    private User_Information user_information;
    private Retrofit mRetrofit;
    private Api mApi;
    private ImageButton profile_picture;
    private String path;
    private UserImageChange u = new UserImageChange(Activity_User_Information.this);

    //????????????????????????????????????
    //??????sharedPreferences????????????image???path??????image???bitmap????????????(decode)????????????????????????????????????
    private void Init_Profile_picture() {
        sharedPreferences = this.getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        String string = sharedPreferences.getString("getFilePath", null);
        if (string != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(string);
            profile_picture.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_information_edit);
        user_information = User_Information.getUser_information();

        mRetrofit = new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = mRetrofit.create(Api.class);

        //??????????????????????????????????????????????????????????????????????????????????????????????????????
        profile_picture = findViewById(R.id.profile_picture);
        Init_Profile_picture();
        informationInitialize();

        profile_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //????????????
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!checkPermission()) {
                        requestPermissions(PICK_PHOTO);
                    }
                    if (!checkPermission()) {
                        requestPermissions(TAKE_PHOTO);
                    }
                }
                //??????showDialog?????????????????????
                u.showDialog();
            }
        });


        BackBtn = (ImageButton) findViewById(R.id.back_btn);
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
        if (resultCode == RESULT_OK) {
            //???????????????
            if (requestCode == TAKE_PHOTO) {
                //????????????u?????????file
                if (u.getFile() == null || !u.getFile().exists()) {
                    profile_picture.setImageBitmap(null);
                } else {
                    Bitmap bitmap = u.getScaledBitmap(u.getFile().getPath(), Activity_User_Information.this);                                                 //??????????????????bitmap????????????
                    profile_picture.setImageBitmap(bitmap);                      //???????????????????????????
                    savePhotos(u.getFile().getPath());                           //????????????????????????                                 //??????????????????????????????????????????????????????                             //sharedPreference???
                    File profile=new File(u.getFile().getPath());
                    MultipartBody.Part part=MultipartBody.Part.createFormData("file",fileName,
                            RequestBody.create(MediaType.parse("image/*"), profile));
                    Call<SimpleResult> apiResult=mApi.putMyProfile(getMyToken(),"yes",part);
                    apiResult.enqueue(new Callback<SimpleResult>() {
                        @Override
                        public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                            Log.d("TAG",response.body().getMsg()+"??????");
                        }

                        @Override
                        public void onFailure(Call<SimpleResult> call, Throwable t) {
                            System.out.println("?????????????????????");
                        }
                    });
                }

                //??????????????????????????????
            } else if (requestCode == PICK_PHOTO) {
                Uri uri = data.getData();
                Activity_User_Information.this.revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                Log.d("TRy", uri.toString()+"url ");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //?????????
                    path = u.handleImageOnKitKat(uri);                        //???????????????path?????????
                } else {
                    path = u.handleImageBeforeKitKat(uri);                     //???????????????path?????????
                }

                Bitmap bitmap = BitmapFactory.decodeFile(path);                 //????????????bitmap????????????
                Log.d("??????", path);
                profile_picture.setImageBitmap(bitmap);
                savePhotos(path);
                File profile=new File(path);
                MultipartBody.Part part=MultipartBody.Part.createFormData("file",getProfileType(path),
                        RequestBody.create(MediaType.parse("image/*"), profile));
                Call<SimpleResult> apiResult=mApi.putMyProfile(getMyToken(),"yes",part);
                apiResult.enqueue(new Callback<SimpleResult>() {
                    @Override
                    public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                        Log.d("TAG",response.body().getMsg()+"??????");
                    }

                    @Override
                    public void onFailure(Call<SimpleResult> call, Throwable t) {
                        System.out.println("?????????????????????");
                    }
                });
                //??????????????????????????????????????????????????????
            } else {
                Log.d("Demo", "?????????");
            }
        }
    }

    /*????????????????????????????????????*/
    public void savePhotos(String filePath) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("getFilePath", filePath);
        editor.commit();
    }

    /*??????????????????*/
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

    private void informationInitialize() {
        mIsMaleButton = (ImageButton) findViewById(R.id.ismale);
        mIsFemaleButton = (ImageButton) findViewById(R.id.isfemale);
        mUnselectedButton = (ImageButton) findViewById(R.id.unselected);

        //????????????????????????
        if (user_information.getUserSex() == UserSex.Male) {
            mIsMaleButton.setBackgroundResource(R.drawable.ismale_yes);
            mIsFemaleButton.setBackgroundResource(R.drawable.isfemale_no);
            mUnselectedButton.setBackgroundResource(R.drawable.unselected_no);
        } else if (user_information.getUserSex() == UserSex.Female) {
            mIsMaleButton.setBackgroundResource(R.drawable.ismale_no);
            mIsFemaleButton.setBackgroundResource(R.drawable.isfemale_yes);
            mUnselectedButton.setBackgroundResource(R.drawable.unselected_no);
        } else if (user_information.getUserSex() == UserSex.Unselected) {
            mIsMaleButton.setBackgroundResource(R.drawable.ismale_no);
            mIsFemaleButton.setBackgroundResource(R.drawable.isfemale_no);
            mUnselectedButton.setBackgroundResource(R.drawable.unselected_yes);
        }
        //?????????????????????


        mIsMaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_information.setUserSex(UserSex.Male);

                mIsMaleButton.setBackgroundResource(R.drawable.ismale_yes);
                mIsFemaleButton.setBackgroundResource(R.drawable.isfemale_no);
                mUnselectedButton.setBackgroundResource(R.drawable.unselected_no);
            }
        });
        mIsFemaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_information.setUserSex(UserSex.Female);

                mIsMaleButton.setBackgroundResource(R.drawable.ismale_no);
                mIsFemaleButton.setBackgroundResource(R.drawable.isfemale_yes);
                mUnselectedButton.setBackgroundResource(R.drawable.unselected_no);
            }
        });
        mUnselectedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_information.setUserSex(UserSex.Unselected);

                mIsMaleButton.setBackgroundResource(R.drawable.ismale_no);
                mIsFemaleButton.setBackgroundResource(R.drawable.isfemale_no);
                mUnselectedButton.setBackgroundResource(R.drawable.unselected_yes);
            }
        });

        mUser_name_field = (EditText) findViewById(R.id.user_name_field);
        mSignature_field = (EditText) findViewById(R.id.signature_field);

        Call<ComplexResult> apiResult1 = mApi.getMyMsg(getMyToken());
        apiResult1.enqueue(new Callback<ComplexResult>() {
            @Override
            public void onResponse(Call<ComplexResult> call, Response<ComplexResult> response) {
                mUser_name_field.setText(StringTool.getJsonString(response.body().getData(), "Name"));
                mSignature_field.setText(StringTool.getJsonString(response.body().getData(), "Signature"));
            }

            @Override
            public void onFailure(Call<ComplexResult> call, Throwable t) {
                Toast.makeText(Activity_User_Information.this, "?????????????????????", Toast.LENGTH_SHORT).show();
            }
        });


        ConfirmBtn = (Button) findViewById(R.id.confirm_button);
        ConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((mUser_name_field.getText().toString().trim().equals(""))) {
                    Toast.makeText(Activity_User_Information.this, "??????????????????", Toast.LENGTH_SHORT).show();
                } else {
                    Call<SimpleResult> apiResult2 = mApi.putMyMsg(getMyToken(), user_information.getUserSex().toString(), mUser_name_field.getText().toString(), mSignature_field.getText().toString());
                    apiResult2.enqueue(new Callback<SimpleResult>() {
                        @Override
                        public void onResponse(Call<SimpleResult> call, Response<SimpleResult> response) {
                            Toast.makeText(Activity_User_Information.this, "????????????", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<SimpleResult> call, Throwable t) {

                        }
                    });
                }
            }
        });


    }
    private String getProfileType(String path){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        String type = options.outMimeType;
        return type;
    }
}
