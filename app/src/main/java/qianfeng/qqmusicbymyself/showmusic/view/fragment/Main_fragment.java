package qianfeng.qqmusicbymyself.showmusic.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import qianfeng.qqmusicbymyself.BaseFragement;
import qianfeng.qqmusicbymyself.R;
import qianfeng.qqmusicbymyself.showmusic.MainActivity;
import qianfeng.qqmusicbymyself.showmusic.view.adapter.MainFg_viewpager_adapter;


/**
 *
 * Created by Administrator on 2016/10/26 0026.
 */
public class Main_fragment extends BaseFragement {
    private ViewPager viewPager;
    private RelativeLayout toobar;
    private TabLayout tabLayout;
    private ImageView iv_search;
    private View view;
    private List<Fragment> list;
    private String[] titles;

    private FragmentManager manager;
    private ImageView toolbar_back;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 复用这个view，每次都不需要重新加载了，只需要Fragment拿到这个view，继续加载就好了。节省时间，提高效率。
        if (view == null) {
            Log.d("google-my:", "onCreateView: 请问我执行了多少次？");
            view = inflater.inflate(R.layout.main_fragment, container, false);
        }

        // 注意这行是最重要的代码！这是Fragment中有Viewpager，而viewpager中又有Fragment，这属于Fragment管理Fragment的情况。
        manager = getChildFragmentManager();

        initView(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(viewPager != null)
        {
            Log.d("google-my:", "onResume: ");
             viewPager.setCurrentItem(0);
            // 数据显示不出来，是不是因为Fragment和Activity断了detach联系？
            // Fragment管理Fragment是用父Fragment来管理子Fragment
            // 用父Fragment来管理子Fragement
            viewPager.setAdapter(new MainFg_viewpager_adapter(manager,titles,list));
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    private void initView(View view) {
        viewPager = ((ViewPager) view.findViewById(R.id.viewpager));
        toobar = ((RelativeLayout) view.findViewById(R.id.toobar));
        tabLayout = ((TabLayout) view.findViewById(R.id.tabLayout));
        iv_search = ((ImageView) view.findViewById(R.id.iv_search));
        toolbar_back = ((ImageView) view.findViewById(R.id.toolbar_back));
        // 如果点击了Toolbar上的back按钮，立刻退回到Main_fragment中
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.ACTIVITY_STATE = 0;
                Log.d("google-my:", "onBackPressed: addFragmentBackPress::");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ll,new Main_fragment()).commit();
            }
        });

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 从一个Fragment跳转到另一个Fragment
                MainActivity.ACTIVITY_STATE = 1;
//                 看这样替换Fragment，行不行。理论上来说这样是可以，但是性能有点慢，建议使用show和hide方式显示Fragment
                // 明天试试用add可不可以。
                // 在同一个Activity中可能可以使用Fragment的add方法，但是在Fragment中add Fragment
                // No layout manager attached; skipping layout , 还是上下文问题!

                SearchFragment fragment = new SearchFragment();
//                manager.beginTransaction().replace(R.id.ll,fragment).commit();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ll,fragment).commit();
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//
//                if(fragment.isAdded())
//                {
//                    transaction.hide(MainActivity.CURRENTFRAGMENT).show(fragment).commit();
//
//                }else
//                {
//                    transaction.hide(MainActivity.CURRENTFRAGMENT).add(R.id.ll, fragment).commit();
//                }
//
                MainActivity.CURRENTFRAGMENT = fragment;

            }

        });

        list = new ArrayList<>();
        titles = new String[3];
        titles[0] = "我的";
        titles[1] = "音乐馆";
        titles[2] = "发现";

        list.add(new MineFragment());
        list.add(new MusicFragment());
        list.add(new FindFragment());


        viewPager.setAdapter(new MainFg_viewpager_adapter(manager, titles, list));

        viewPager.setOffscreenPageLimit(4);
       

        tabLayout.setupWithViewPager(viewPager);


    }



}
