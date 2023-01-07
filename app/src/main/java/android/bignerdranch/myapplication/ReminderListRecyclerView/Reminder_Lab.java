package android.bignerdranch.myapplication.ReminderListRecyclerView;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Reminder_Lab {
    private static Reminder_Lab sReminder_lab;

    private List<Reminder> mReminders;

    public static Reminder_Lab getReminder_lab(Context context){
        if (sReminder_lab == null) {
            sReminder_lab =new Reminder_Lab(context);
        }
        return sReminder_lab;
    }

    private Reminder_Lab(Context context){
        mReminders=new ArrayList<>();
        //先生成20个测试用的样例
        for(int i=0;i<=19;i++){
            Reminder reminder=new Reminder();
            reminder.setPersonName(i+"号选手");
            mReminders.add(reminder);
        }
    }

    public List<Reminder> getReminders() {
        return mReminders;
    }

    public Reminder getReminder(UUID id){
        for(Reminder reminder:mReminders){
            if(reminder.getId().equals(id)) {
                return reminder;
            }
        }
        return null;
    }
}
