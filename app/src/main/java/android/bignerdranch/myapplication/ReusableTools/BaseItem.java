package android.bignerdranch.myapplication.ReusableTools;

import java.util.UUID;

public abstract class BaseItem {
    private UUID mUUID;
    private String name;
    private String time;
    private String content;

    public abstract UUID getId();
    public abstract String getName();
    public abstract String getContent();
    public abstract String getTime();

    public abstract int typeCode();
}
