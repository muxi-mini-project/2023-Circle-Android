package android.bignerdranch.myapplication.ui.reminder;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReminderLab {
    private static ReminderLab sReminder_lab;

    private List<Reminder> mReminders;

    public static ReminderLab getReminder_lab(Context context){
        if (sReminder_lab == null) {
            sReminder_lab =new ReminderLab(context);
        }
        return sReminder_lab;
    }

    private ReminderLab(Context context){
        mReminders=new ArrayList<>();
        //先生成20个测试用的样例
        for(int i=0;i<=19;i++){
            Reminder reminder=new Reminder();
            reminder.setContent("第"+(i+1)+"次测试");
            reminder.setPersonName((i+1)+"号选手");
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
