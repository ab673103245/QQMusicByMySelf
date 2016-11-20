package qianfeng.qqmusicbymyself.showmusic.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import qianfeng.qqmusicbymyself.BaseFragement;
import qianfeng.qqmusicbymyself.R;

/**
 * Created by 王松 on 2016/10/17.
 */

public class FindFragment extends BaseFragement {
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.mine_fg_layout, container, false);
        }
        return view;
    }
}
