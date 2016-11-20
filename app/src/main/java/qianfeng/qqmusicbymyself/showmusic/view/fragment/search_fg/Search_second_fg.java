package qianfeng.qqmusicbymyself.showmusic.view.fragment.search_fg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import qianfeng.qqmusicbymyself.BaseFragement;
import qianfeng.qqmusicbymyself.R;
import qianfeng.qqmusicbymyself.showmusic.MainActivity;
import qianfeng.qqmusicbymyself.showmusic.presenter.SecondSearchPresenter;
import qianfeng.qqmusicbymyself.showmusic.view.ISecondSearchView;
import qianfeng.qqmusicbymyself.showmusic.view.adapter.SecondSearchPagerAdapter;
import qianfeng.qqmusicbymyself.showmusic.view.fragment.FindFragment;
import qianfeng.qqmusicbymyself.showmusic.view.fragment.Main_fragment;
import qianfeng.qqmusicbymyself.showmusic.view.fragment.SearchFragment;
import qianfeng.qqmusicbymyself.util.Consts;

/**
 * Created by Administrator on 2016/10/28 0028.
 */
public class Search_second_fg extends BaseFragement implements ISecondSearchView{
    private EditText et_content;
    private ImageView iiv_close;
    private ImageView toolbar_back;
    private TextView toolbar_searchText;
    private TabLayout tabLayout;
    private ViewPager viewpager;
    private List<String> tabLyStringList;

    private SecondSearchPresenter secondSearchPresenter = new SecondSearchPresenter(this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_second_fg, container, false);
        initView(view);

        secondSearchPresenter.start();// 初始化tabLayout中的数组数据

        return view;
    }

    private void initView(View view) {
        et_content = ((EditText) view.findViewById(R.id.et_content));
        iiv_close = ((ImageView) view.findViewById(R.id.iiv));
        toolbar_back = ((ImageView) view.findViewById(R.id.toolbar_back));
        toolbar_searchText = ((TextView) view.findViewById(R.id.toolbar_searchText));
        tabLayout = ((TabLayout) view.findViewById(R.id.tabLayout));
        viewpager = ((ViewPager) view.findViewById(R.id.viewpager));

        et_content.setText(Consts.selectSongName);

        iiv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ll,new SearchFragment()).commit();
            }
        });

        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.ACTIVITY_STATE = 0;
                Log.d("google-my:", "onBackPressed: addFragmentBackPress::");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ll,new Main_fragment()).commit();
            }
        });

    }

    @Override
    public void initTabLayoutAndViewpager(List<String> list) {
        tabLyStringList = list;

        // 在这里给viewpager设置Adapter，还有给TabLayout的titles赋值

        // 这里又是Fragment管理Fragment，要用getChildFragmentManager()来初始化viewpager，否则第二次无法显示viewpager中的Fragment

        List<Fragment> fragments = new ArrayList<>();

        fragments.add(new SingleSongFragment());
        fragments.add(new FindFragment());
        fragments.add(new FindFragment());
        fragments.add(new FindFragment());
        fragments.add(new FindFragment());

        //要用getChildFragmentManager()来初始化viewpager
        SecondSearchPagerAdapter adapter = new SecondSearchPagerAdapter(getChildFragmentManager(), fragments, list);//要用getChildFragmentManager()来初始化viewpager
        viewpager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewpager);

    }
}
