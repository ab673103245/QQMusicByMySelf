package qianfeng.qqmusicbymyself.localmusic.view;

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
import qianfeng.qqmusicbymyself.localmusic.model.bean.FourSortSongName_Count;
import qianfeng.qqmusicbymyself.localmusic.presenter.LocalMusicFgPersenter;
import qianfeng.qqmusicbymyself.localmusic.view.adapter.LocalMusicFg_VpAdapter;
import qianfeng.qqmusicbymyself.localmusic.view.fragment.ILocalMusicSongFragment;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class LocalMusicFragment extends BaseFragement implements ILocalMusicView {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LocalMusicFgPersenter localMusicFgPersenter = new LocalMusicFgPersenter(this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.localmusic_fg, container, false);
        initView(view);

        localMusicFgPersenter.start();

        return view;
    }

    private void initView(View view) {
        tabLayout = ((TabLayout) view.findViewById(R.id.tabLayout));
        viewPager = ((ViewPager) view.findViewById(R.id.viewpager));

        // 本地歌曲存放在 //data/data/com.android.providers.media/ Media-->(view)  内部存储的系统文件夹com.android.providers.media
// external是智能手机中内置的SD卡，而internal则是机身的内存。video(View)下就是所有的音频文件，包括mp3，mp4

// viewPager.setAdapter(getChildFragmentManager());

    }


    @Override
    public void initTabLayout(List<FourSortSongName_Count> list) {
        List<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(new ILocalMusicSongFragment());
        fragmentList.add(new ILocalMusicSongFragment());
        fragmentList.add(new ILocalMusicSongFragment());
        fragmentList.add(new ILocalMusicSongFragment());

        LocalMusicFg_VpAdapter adapter = new LocalMusicFg_VpAdapter(getChildFragmentManager(), list, fragmentList);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

    }
}
