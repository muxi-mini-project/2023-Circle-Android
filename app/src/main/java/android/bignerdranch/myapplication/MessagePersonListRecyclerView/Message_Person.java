package android.bignerdranch.myapplication.MessagePersonListRecyclerView;

import java.util.UUID;

public class Message_Person {
    private UUID mId;
    private String mPersonName;

    public Message_Person(){
        mId=UUID.randomUUID();
    }


//getter and setter
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
}
