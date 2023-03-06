package android.bignerdranch.myapplication.User_Information_Edit;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ui.home.EditPosts.EditPostsActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Fragment_InformationCard extends Fragment {

    private static final int TAKE_PHOTO = 0X66;
    private static final int PICK_PHOTO = 0X88;

    private ImageButton mIsMaleButton;
    private ImageButton mIsFemaleButton;
    private ImageButton mUnselectedButton;
    private EditText mUser_name_field;
    private EditText mSignature_field;

    private Uri imageUri=Profile_picture_Uri.getProfile_picture_Uri();
    private static ImageButton profile_picture;
    public static SharedPreferences sharedPreferences;
    private Activity_User_Information activity=new Activity_User_Information();
    private UserImageChange u=activity.getU();


    private User_Information user_information;


    public static ImageButton getProfile_picture(){
        return profile_picture;
    }


    //sharedPreference是在本地存储头像图像，每次进入可以通过它来使之前已经设定好的头像呈现
    private void init_profile_picture() {
        sharedPreferences = getActivity().getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        String string = sharedPreferences.getString("getFilePath", null);
        if (string != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(string);
            profile_picture.setImageBitmap(bitmap);
        }
    }


    //推出后保存图片
    public void savePhotos(String filePath) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("getFilePath",filePath);
        editor.commit();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user_information = User_Information.getUser_information();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.informationcard, container, false);
        profile_picture=(ImageButton) view.findViewById(R.id.profile_picture);
        init_profile_picture();
        profile_picture=Fragment_InformationCard.getProfile_picture();
        profile_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u.showDialog();
            }
        });


        mIsMaleButton = (ImageButton) view.findViewById(R.id.ismale);
        mIsFemaleButton = (ImageButton) view.findViewById(R.id.isfemale);
        mUnselectedButton = (ImageButton) view.findViewById(R.id.unselected);



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


        mUser_name_field = (EditText) view.findViewById(R.id.user_name_field);
        mUser_name_field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                user_information.setUser_Name_Title(s.toString());
                user_information.setUser_Name(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mSignature_field = (EditText) view.findViewById(R.id.signature_field);
        mSignature_field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                user_information.setSignature(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        return view;
    }

}
