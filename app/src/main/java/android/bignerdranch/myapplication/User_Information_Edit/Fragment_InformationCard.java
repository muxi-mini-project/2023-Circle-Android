package android.bignerdranch.myapplication.User_Information_Edit;

import android.bignerdranch.myapplication.R;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Fragment_InformationCard extends Fragment {

    private User_Information mUser_information;

    private TextView mTitle;
    private TextView mWords;
    private ImageButton mIsMaleButton;
    private ImageButton mIsFemaleButton;
    private ImageButton mUnselectedButton;
    private TextView Tip1;
    private TextView Tip2;
    private EditText mUser_name_label;
    private EditText mSignature_label;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mUser_information=new User_Information();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.informationcard,container,false);

        mIsMaleButton=(ImageButton)view.findViewById(R.id.ismale);
        mIsFemaleButton=(ImageButton)view.findViewById(R.id.isfemale);
        mUnselectedButton=(ImageButton)view.findViewById(R.id.unselected);

        //初始化性别按钮的
        if(mUser_information.isMale()){
            mIsMaleButton.setBackgroundResource(R.drawable.ismale_yes);
            mIsFemaleButton.setBackgroundResource(R.drawable.isfemale_no);
            mUnselectedButton.setBackgroundResource(R.drawable.unselected_no);
        }else if(mUser_information.isFemale()){
            mIsMaleButton.setBackgroundResource(R.drawable.ismale_no);
            mIsFemaleButton.setBackgroundResource(R.drawable.isfemale_yes);
            mUnselectedButton.setBackgroundResource(R.drawable.unselected_no);
        }else if(mUser_information.isUnselected_Sex()){
            mIsMaleButton.setBackgroundResource(R.drawable.ismale_no);
            mIsFemaleButton.setBackgroundResource(R.drawable.isfemale_no);
            mUnselectedButton.setBackgroundResource(R.drawable.unselected_yes);
        }
        //初始化到此为止


        mIsMaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser_information.setMale(true);
                mUser_information.setFemale(false);
                mUser_information.setUnselected_Sex(false);

                mIsMaleButton.setBackgroundResource(R.drawable.ismale_yes);
                mIsFemaleButton.setBackgroundResource(R.drawable.isfemale_no);
                mUnselectedButton.setBackgroundResource(R.drawable.unselected_no);
            }
        });


        mIsFemaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser_information.setMale(false);
                mUser_information.setFemale(true);
                mUser_information.setUnselected_Sex(false);

                mIsMaleButton.setBackgroundResource(R.drawable.ismale_no);
                mIsFemaleButton.setBackgroundResource(R.drawable.isfemale_yes);
                mUnselectedButton.setBackgroundResource(R.drawable.unselected_no);
            }
        });


        mUnselectedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser_information.setMale(false);
                mUser_information.setFemale(false);
                mUser_information.setUnselected_Sex(true);

                mIsMaleButton.setBackgroundResource(R.drawable.ismale_no);
                mIsFemaleButton.setBackgroundResource(R.drawable.isfemale_no);
                mUnselectedButton.setBackgroundResource(R.drawable.unselected_yes);
            }
        });

        mUser_name_label=(EditText) view.findViewById(R.id.user_name_label);
        mUser_name_label.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser_information.setUser_Name_Title(s.toString());
                mUser_information.setUser_Name(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mSignature_label=(EditText) view.findViewById(R.id.signature_label);
        mSignature_label.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser_information.setSignature(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }
}
