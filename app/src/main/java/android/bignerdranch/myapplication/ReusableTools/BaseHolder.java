package android.bignerdranch.myapplication.ReusableTools;

import android.bignerdranch.myapplication.ui.home.ItemTypeDef;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class BaseHolder extends RecyclerView.ViewHolder {
    private ItemTypeDef.Type type;
    private View mView;

    public BaseHolder(View itemView,ItemTypeDef.Type type){
        super(itemView);
        this.mView=itemView;
        this.type=type;
    }
    public ItemTypeDef.Type getType(){
        return type;
    }
}
