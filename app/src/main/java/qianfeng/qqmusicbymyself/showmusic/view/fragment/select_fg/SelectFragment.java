package qianfeng.qqmusicbymyself.showmusic.view.fragment.select_fg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import qianfeng.qqmusicbymyself.BaseFragement;
import qianfeng.qqmusicbymyself.R;

/**
 * Created by Administrator on 2016/11/1 0001.
 */
public class SelectFragment extends BaseFragement{

    // 只有静态注册的Fragment，在Fragment被销毁时，里面的View还没被销毁，当下次被创建时，这个View可以被复用。记住，只有静态注册的Fragment可以这样用。
    View view = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.select_fg_layout, container, false);
        }
        initView(view);
        return view;
    }

    private void initView(View view) {
        FragmentManager manager = getChildFragmentManager();

        LeftFragment left_fg = ((LeftFragment) manager.findFragmentById(R.id.left_fg));
        RightFragment right_fg = ((RightFragment) manager.findFragmentById(R.id.right_fg));

        left_fg.setRightFg(right_fg); // 把右边Fragment的引用通过ChildFragment管理器传进去左边的Fragment中

    }


}
