package android.bignerdranch.myapplication.ui.reminder;

import java.util.Date;
import java.util.UUID;

public class Reminder {
    private UUID mId;                         //通知的编码
    private String mPersonName;               //存储通知相关的对象
    private Date mDate;                       //存储通知时间
    private String mContent;


    public Reminder(){
        mId=UUID.randomUUID();
        mDate=new Date();
    }


//getter and setter


    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getPersonName() {
        return mPersonName;
    }

    public void setPersonName(String personName) {
        mPersonName = personName;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
