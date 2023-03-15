package android.bignerdranch.myapplication.ui.reminder;

import java.util.ArrayList;
import java.util.List;

public class ReminderLab {

    private List<Reminder> mReminderList;


    private ReminderLab(List<Reminder> mReminders) {
        mReminderList = mReminders;
    }

    public static ReminderLab get(int dataLength) {
        List<Reminder> mReminders = new ArrayList<>();
        for (int i = 0; i < dataLength; i++) {
            Reminder reminder = new Reminder();
            reminder.setTitle("");
            reminder.setPersonName("用户名");
            mReminders.add(reminder);
        }
        return new ReminderLab(mReminders);
    }

    public List<Reminder> getReminders() {
        return mReminderList;
    }
}
