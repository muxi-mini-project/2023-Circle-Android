package android.bignerdranch.myapplication.ReusableTools;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class BaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private ItemTypeDef.Type type;
    private View mView;
    private MyRecyclerItemClickListener myRecyclerItemClickListener;

    @Override
    public void onClick(View view) {
        if(myRecyclerItemClickListener != null){
            myRecyclerItemClickListener.onItemClick(view,getPosition());
        }
    }

    public BaseHolder(View itemView,ItemTypeDef.Type type,MyRecyclerItemClickListener myRecyclerItemClickListener){
        super(itemView);
        this.mView=itemView;
        this.type=type;
        this.myRecyclerItemClickListener=myRecyclerItemClickListener;
        mView.setOnClickListener(this);
    }
    public BaseHolder(View itemView,ItemTypeDef.Type type){
        super(itemView);
        this.mView=itemView;
        this.type=type;
    }


    public ItemTypeDef.Type getType(){
        return type;
    }
}
