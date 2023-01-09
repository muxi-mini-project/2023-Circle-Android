package android.bignerdranch.myapplication;

import android.bignerdranch.myapplication.ui.reminder.ReminderActivity;
import android.bignerdranch.myapplication.User_Information_Edit.Activity_User_Information;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Test_Navigation_Activity extends AppCompatActivity {

    private Button mReminder_List_Test_Button;
    private Button mUser_Information_Edit_Test_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_navigation);

        mReminder_List_Test_Button=(Button) findViewById(R.id.reminder_list_test_navigation);
        mReminder_List_Test_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Test_Navigation_Activity.this,ReminderActivity.class);
                startActivity(intent);
            }
        });

        mUser_Information_Edit_Test_Button=(Button) findViewById(R.id.user_information_edit_navigation);
        mUser_Information_Edit_Test_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Test_Navigation_Activity.this, Activity_User_Information.class);
                startActivity(intent);
            }
        });
    }
}