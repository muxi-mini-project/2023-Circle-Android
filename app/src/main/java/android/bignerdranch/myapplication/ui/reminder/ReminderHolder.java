package android.bignerdranch.myapplication.ui.reminder;

import android.bignerdranch.myapplication.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ReminderHolder extends RecyclerView.ViewHolder {

    private ImageView mProfile;
    private TextView mPersonname;
    private TextView mRemindercontent;
    private TextView mReminderdate;

    private Reminder mReminder;

    private Context mContext;

    public ReminderHolder(LayoutInflater inflater, ViewGroup parent, Context context) {
        super(inflater.inflate(R.layout.item_reminder, parent, false));

        mContext=context;

        mProfile=(ImageView) itemView.findViewById(R.id.reminder_person_head);
        mPersonname = (TextView) itemView.findViewById(R.id.reminder_personname);
        mRemindercontent = (TextView) itemView.findViewById(R.id.reminder_content);
        mReminderdate = (TextView) itemView.findViewById(R.id.reminder_date);
    }

    public void bind(Reminder reminder) {
        mReminder = reminder;
        mPersonname.setText(mReminder.getPersonName());
        mRemindercontent.setText("给你点赞："+mReminder.getTitle());
        mReminderdate.setText(mReminder.getDate());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.recyangle)
                .circleCropTransform();
        Glide.with(mContext)
                .load("http://"+mReminder.getProfile())
                .apply(options)
                .into(mProfile);

    }
}
