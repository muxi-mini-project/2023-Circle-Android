package android.bignerdranch.myapplication;

public class User_information {
    private String mName;
    private boolean mSex;             //True为男,False为女
    private String mSignature;

    public User_information(String name, boolean sex, String signature){
        mName=name;
        mSex=sex;
        mSignature=signature;
    }


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public boolean isSex() {
        return mSex;
    }

    public void setSex(Boolean sex) {
        mSex = sex;
    }

    public String getSignature() {
        return mSignature;
    }

    public void setSignature(String signature) {
        mSignature = signature;
    }
}
