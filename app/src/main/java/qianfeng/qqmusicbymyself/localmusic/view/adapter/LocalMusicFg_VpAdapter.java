package qianfeng.qqmusicbymyself.localmusic.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import qianfeng.qqmusicbymyself.localmusic.model.bean.FourSortSongName_Count;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class LocalMusicFg_VpAdapter extends FragmentPagerAdapter {

    private List<FourSortSongName_Count> list1;
    private List<Fragment> list;

    public LocalMusicFg_VpAdapter(FragmentManager fm, List<FourSortSongName_Count> list1, List<Fragment> list) {
        super(fm);
        this.list1 = list1;
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
    public CharSequence getPageTitle(int position) {
        return list1.get(position).getSort();
    }
}
