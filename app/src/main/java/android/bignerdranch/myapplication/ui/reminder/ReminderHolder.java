package android.bignerdranch.myapplication.ui.reminder;

import android.bignerdranch.myapplication.R;
import android.bignerdranch.myapplication.ReusableTools.BaseHolder;
import android.bignerdranch.myapplication.ReusableTools.ItemTypeDef;
import android.bignerdranch.myapplication.ReusableTools.MyRecyclerItemClickListener;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ReminderHolder extends BaseHolder {

    private ImageView mProfile;
    private TextView mPersonname;
    private TextView mRemindercontent;
    private TextView mReminderdate;

    private Reminder mReminder;

    private Context mContext;

    public ReminderHolder(View itemView, ItemTypeDef.Type type, MyRecyclerItemClickListener myRecyclerItemClickListener,
                          Context context) {
        super(itemView,type,myRecyclerItemClickListener);

        mContext=context;

        mProfile=itemView.findViewById(R.id.reminder_person_head);
        mPersonname =itemView.findViewById(R.id.reminder_personname);
        mRemindercontent = itemView.findViewById(R.id.reminder_content);
        mReminderdate =itemView.findViewById(R.id.reminder_date);
    }

    public void bind(Reminder reminder) {
        if (mReminder==null){
            mReminder = reminder;
        }
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
