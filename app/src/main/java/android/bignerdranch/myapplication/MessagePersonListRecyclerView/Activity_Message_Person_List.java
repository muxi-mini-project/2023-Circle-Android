package android.bignerdranch.myapplication.MessagePersonListRecyclerView;

import android.bignerdranch.myapplication.R;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class Activity_Message_Person_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_person_list);

        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment=new Fragment_Message_Person_list();

        if(fragmentManager==null){
            fragment=new Fragment_Message_Person_list();
            fragmentManager.beginTransaction()
                    .add(R.id.message_person_list_fragment_container,fragment)
                    .commit();
        }
    }
}
