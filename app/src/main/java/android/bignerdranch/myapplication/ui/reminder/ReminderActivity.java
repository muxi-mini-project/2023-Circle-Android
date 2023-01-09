package android.bignerdranch.myapplication.ui.reminder;

import android.bignerdranch.myapplication.BaseActivity;
import android.bignerdranch.myapplication.R;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ReminderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_reminder);

        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment=fragmentManager.findFragmentById(R.id.layout_reminder);

        if(fragment==null){
            fragment=new ReminderFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.layout_reminder,fragment)
                    .commit();
        }
    }

}
