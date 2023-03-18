package android.bignerdranch.myapplication.ReusableTools;

public class ItemTypeDef {//用于recyclerview中多种item

    public static final int ITEM_SEARCH_BOX = 1;
    public static final int ITEM_POSTS = 2;
    public static final int ITEM_COMMENT = 3;
    public static final int ITEM_FOLLOWER = 4;
    public static final int ITEM_PIC=5;
    public static final int ITEM_REMINDER=6;

    public enum Type {
        SEARCH_BOX(ITEM_SEARCH_BOX),
        POSTS(ITEM_POSTS),
        COMMENT(ITEM_COMMENT),
        FOLLOWER(ITEM_FOLLOWER),
        PIC(ITEM_PIC),
        REMINDER(ITEM_REMINDER);
        int code;

        Type(int code) {
            this.code = code;
        }

        public static Type getItemTypeByCode(int code) {
            switch (code) {
                case ITEM_SEARCH_BOX:
                    return Type.SEARCH_BOX;
                case ITEM_POSTS:
                    return Type.POSTS;
                case ITEM_COMMENT:
                    return Type.COMMENT;
                case ITEM_FOLLOWER:
                    return Type.FOLLOWER;
                case ITEM_PIC:
                    return Type.PIC;
                case ITEM_REMINDER:
                    return Type.REMINDER;
            }
            return null;
        }

        public int getCode() {
            return code;
        }
    }
}
