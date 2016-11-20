package qianfeng.qqmusicbymyself.showmusic.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.List;

import qianfeng.qqmusicbymyself.BaseFragement;

/**
 * Created by Administrator on 2016/10/26 0026.
 */
public class MainFg_viewpager_adapter extends FragmentPagerAdapter {

    private String[] titles;
    private List<Fragment> list;

    public MainFg_viewpager_adapter(FragmentManager fm, String[] titles, List<Fragment> list) {
        super(fm);
        this.titles = titles;
        this.list = list;
    }

    public MainFg_viewpager_adapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 如何在ViewPager中保留View
        Log.d("google-my:", "destroyItem: ");

    }

//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        Log.d("google-my:", "instantiateItem: ");
//        return super.instantiateItem(container, position);
//
//    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BaseFragement fragment = (BaseFragement) super.instantiateItem(container, position);
//        fragment.initData(titleList.get(position).getCategory());
        return fragment;
    }

    // ViewPager中动态添加Fragment，即从另一个Fragment返回来的时候，这个方法是必须的
    // 动态在ViewPager创建Fragment的时候，是否都是利用工厂方法的？
    @Override
    public int getItemPosition(Object object) { // 这个方法是必须的
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
