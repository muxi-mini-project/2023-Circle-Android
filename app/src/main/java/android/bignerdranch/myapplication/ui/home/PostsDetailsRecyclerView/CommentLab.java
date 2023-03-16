package android.bignerdranch.myapplication.ui.home.PostsDetailsRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CommentLab {

    private List<Comment> mCommentList;

    private CommentLab(List<Comment> mComments){
        mCommentList=mComments;
    }

    public static CommentLab get(int dataLength) {
        List<Comment> mComment= new ArrayList<>();
        for (int i=0;i<dataLength;i++) {
            Comment comment=new Comment();
            comment.setContent("");
            comment.setName("用户名");
            mComment.add(comment);
        }
        return new CommentLab(mComment);
    }

    public List<Comment> getComments(){
        return mCommentList;
    }
}
