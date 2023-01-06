package android.bignerdranch.myapplication.ReminderListRecyclerView;

import android.bignerdranch.myapplication.R;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class Activity_Reminder_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_list);

        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment=new Fragment_Reminder_List();

        if(fragment==null){
            fragment=new Fragment_Reminder_List();
            fragmentManager.beginTransaction()
                    .add(R.id.reminder_list_fragment_container,fragment)
                    .commit();
        }
    }
}
