package android.bignerdranch.myapplication.MessagePersonListRecyclerView;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Message_Person_Lab {
    private static Message_Person_Lab sMessage_person_lab;

    private List<Message_Person> mMessage_persons;

    public static Message_Person_Lab getMessage_person_lab(Context context){
        if (sMessage_person_lab == null) {
            sMessage_person_lab=new Message_Person_Lab(context);
        }
        return sMessage_person_lab;
    }

    private Message_Person_Lab(Context context){
        mMessage_persons=new ArrayList<>();
        //先生成5个测试用的样例
        for(int i=0;i<=4;i++){
            Message_Person message_person=new Message_Person();
            message_person.setPersonName(i+"号选手");
            mMessage_persons.add(message_person);
        }
    }

    public List<Message_Person> getMessage_Persons() {
        return mMessage_persons;
    }

    public Message_Person getMessage_Person(UUID id){
        for(Message_Person message_person:mMessage_persons){
            if(message_person.getId().equals(id)) {
                return message_person;
            }
        }
        return null;
    }
}
