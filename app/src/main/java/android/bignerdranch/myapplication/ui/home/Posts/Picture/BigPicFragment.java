package android.bignerdranch.myapplication.ui.home.Posts.Picture;

import android.bignerdranch.myapplication.R;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import uk.co.senab.photoview.PhotoView;

public class BigPicFragment extends Fragment {
    private String mPath;
    private PhotoView mPhotoView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photo_dialog, container, false);

        mPhotoView=(PhotoView) view.findViewById(R.id.photo);

        bind();

        return view;
    }

    public void setPath(String path){
        mPath=path;
    }

    private void bind(){
        Glide.with(getContext())
                .load("http://" + mPath)
                .centerCrop()
                .into(mPhotoView);
    }
}
