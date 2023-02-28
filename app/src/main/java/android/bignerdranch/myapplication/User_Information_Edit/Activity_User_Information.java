package android.bignerdranch.myapplication.User_Information_Edit;

import android.bignerdranch.myapplication.ReusableTools.BaseActivity;
import android.bignerdranch.myapplication.R;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class Activity_User_Information extends BaseActivity {

    private ImageButton BackBtn;

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
        BackBtn=(ImageButton) findViewById(R.id.back_btn);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_User_Information.this.finish();
            }
        });
    }


}
