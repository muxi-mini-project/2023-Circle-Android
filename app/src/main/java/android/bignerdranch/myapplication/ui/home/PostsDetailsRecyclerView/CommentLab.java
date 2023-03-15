package android.bignerdranch.myapplication.ui.home.PostsDetailsRecyclerView;

import android.bignerdranch.myapplication.ui.home.Posts;
import android.bignerdranch.myapplication.ui.home.PostsLab;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
