package android.bignerdranch.myapplication.User_Information_Edit;

import android.bignerdranch.myapplication.BaseActivity;
import android.bignerdranch.myapplication.R;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class Activity_User_Information extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_information_edit);

        FragmentManager mFragmentManager=getSupportFragmentManager();
        Fragment fragment=mFragmentManager.findFragmentById(R.id.user_informationcard_fragmentcontainer);

        if(fragment==null){
            fragment=new Fragment_InformationCard();
            mFragmentManager.beginTransaction()
                    .add(R.id.user_informationcard_fragmentcontainer,fragment)
                    .commit();
        }
    }

}
