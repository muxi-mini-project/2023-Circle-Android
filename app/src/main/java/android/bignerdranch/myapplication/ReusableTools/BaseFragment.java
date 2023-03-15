package android.bignerdranch.myapplication.ReusableTools;

import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    private String[] mData;
    private String[] mList;

    public abstract void setData(String[] data);

    public abstract void setAdapterAbout();

}
