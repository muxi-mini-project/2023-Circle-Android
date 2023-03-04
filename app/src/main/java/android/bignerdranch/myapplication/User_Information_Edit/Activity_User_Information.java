package android.bignerdranch.myapplication.User_Information_Edit;

import android.bignerdranch.myapplication.ApiAbout.Api;
import android.bignerdranch.myapplication.ApiAbout.ApiResult;
import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.SignInActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_User_Information extends BaseActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_information_edit);

        user_information = User_Information.getUser_information();
        informationInitialize();


        BackBtn=(ImageButton) findViewById(R.id.back_btn);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_User_Information.this.finish();
            }
        });


    }

    private void informationInitialize(){
        mIsMaleButton = (ImageButton) findViewById(R.id.ismale);
        mIsFemaleButton = (ImageButton) findViewById(R.id.isfemale);
        mUnselectedButton = (ImageButton) findViewById(R.id.unselected);

        //初始化性别按钮的
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
        //初始化到此为止


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

        mUser_name_field=(EditText)findViewById(R.id.user_name_field);
        mSignature_field=(EditText)findViewById(R.id.signature_field);

        mRetrofit=new Retrofit.Builder().baseUrl("http://43.138.61.49:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi=mRetrofit.create(Api.class);
        ConfirmBtn=(Button) findViewById(R.id.confirm_button);
        ConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((mUser_name_field.getText().toString().trim().equals(""))) {
                    Toast.makeText(Activity_User_Information.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                }
                Call<ApiResult> apiResult = mApi.putMyMsg(getMyToken(),user_information.getUserSex() .toString(),mUser_name_field.getText().toString(),mSignature_field.getText().toString());
                apiResult.enqueue(new Callback<ApiResult>() {
                    @Override
                    public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                        Toast.makeText(Activity_User_Information.this,"修改成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ApiResult> call, Throwable t) {

                    }
                });
            }
        });
    }
}
