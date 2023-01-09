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
<<<<<<< HEAD:app/src/main/java/android/bignerdranch/myapplication/ui/reminder/ReminderActivity.java
        Fragment fragment=fragmentManager.findFragmentById(R.id.layout_reminder);
=======
        Fragment fragment=fragmentManager.findFragmentById(R.id.reminder_list_fragment_container);
>>>>>>> 2c08dee7fce687e23da1eca265c0fc704528f740:app/src/main/java/android/bignerdranch/myapplication/ReminderListRecyclerView/Activity_Reminder_List.java

        if(fragment==null){
            fragment=new ReminderFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.layout_reminder,fragment)
                    .commit();
        }
    }

}
