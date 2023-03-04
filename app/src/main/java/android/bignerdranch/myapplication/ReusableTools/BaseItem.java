package android.bignerdranch.myapplication.ReusableTools;

import java.util.UUID;

public abstract class BaseItem {
    private String name;
    private String time;
    private String content;

    public abstract String getName();
    public abstract String getContent();
    public abstract String getTime();

    public void setContent(String content) {
        this.content = content;
    }

    public abstract int typeCode();
}
