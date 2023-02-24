package android.bignerdranch.myapplication.ui.home.PostsDetailsRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommentLab {
    private static CommentLab sCommentLab;

    private List<Comment> mComments;

    public static CommentLab get(){
        if (sCommentLab==null){
            sCommentLab=new CommentLab();
        }
        return sCommentLab;
    }

    private CommentLab(){
        mComments=new ArrayList<>();
        for (int i=0;i<20;i++){
            Comment comment=new Comment();
            comment.setCommenterName("复读机"+(i+1)+"号");
            comment.setContent("第"+(i+1)+"次测试");
            mComments.add(comment);
        }
    }


    public List<Comment> get_mComment(){return mComments;}

    public Comment getPosts(UUID id){
        for (Comment e:mComments){
            if (e.getId().equals(id))
                return e;
        }
        return null;
    }
}
