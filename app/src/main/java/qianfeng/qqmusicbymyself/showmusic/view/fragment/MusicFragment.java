package qianfeng.qqmusicbymyself.showmusic.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import qianfeng.qqmusicbymyself.BaseFragement;
import qianfeng.qqmusicbymyself.R;
import qianfeng.qqmusicbymyself.showmusic.view.adapter.MusicFg_VpAdapter;
import qianfeng.qqmusicbymyself.showmusic.view.fragment.select_fg.LeftFragment;
import qianfeng.qqmusicbymyself.showmusic.view.fragment.select_fg.RightFragment;
import qianfeng.qqmusicbymyself.showmusic.view.fragment.select_fg.SelectFragment;

/**
 * Created by 王松 on 2016/10/17.
 */

public class MusicFragment extends BaseFragement {
    private ViewPager viewpager;
    private TabLayout tabLayout;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.music_fg_layout, container, false);
        }
        initView(view);
        return view;
    }

    private void initView(View view) {
        viewpager = ((ViewPager) view.findViewById(R.id.viewpager));
        tabLayout = ((TabLayout) view.findViewById(R.id.tabLayout));
        String[] titles = new String[]{"精选", "排行", "歌单", "电台", "MV"};

        List<Fragment> fragments = new ArrayList<>();

        fragments.add(new SelectFragment());
        fragments.add(new LeftFragment());
        fragments.add(new RightFragment());
        fragments.add(new SelectFragment());
        fragments.add(new SelectFragment());

//        fragments.add(new RankFragment());

//        fragments.add(new PlayListFragment());
//        fragments.add(new RadioStationFragment());
//        fragments.add(new MVFragment());

        // Fragment中有Fragment，就要用getChildManager来管理子Fragment
        viewpager.setAdapter(new MusicFg_VpAdapter(getChildFragmentManager(), fragments, titles));

        tabLayout.setupWithViewPager(viewpager);

    }
}
