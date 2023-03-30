package android.bignerdranch.myapplication.ui.reminder;

import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseHolder;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;
import android.bignerdranch.myapplication.ReusableTools.MyRecyclerItemClickListener;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ReminderHolder extends BaseHolder {

    private final ImageView mProfile;
    private final TextView mPersonname;
    private final TextView mRemindercontent;
    private final TextView mReminderdate;
    private final Context mContext;
    private Reminder mReminder;

    public ReminderHolder(View itemView, ItemTypeDef.Type type, MyRecyclerItemClickListener myRecyclerItemClickListener,
                          Context context) {
        super(itemView, type, myRecyclerItemClickListener);

        mContext = context;

        mProfile = itemView.findViewById(R.id.reminder_person_head);
        mPersonname = itemView.findViewById(R.id.reminder_personname);
        mRemindercontent = itemView.findViewById(R.id.reminder_content);
        mReminderdate = itemView.findViewById(R.id.reminder_date);
    }

    public void bind(Reminder reminder, int mInt) {
        mReminder = reminder;
        mPersonname.setText(mReminder.getPersonName());
        switch (mInt) {
            case 1:
                mRemindercontent.setText("给你点赞：" + mReminder.getTitle());
                break;
            case 2:
                mRemindercontent.setText("给你评论：" + mReminder.getContent());
                break;
        }
        mReminderdate.setText(mReminder.getDate());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.recyangle)
                .circleCropTransform();
        Glide.with(mContext)
                .load("http://" + mReminder.getProfile())
                .apply(options)
                .into(mProfile);

    }

}
