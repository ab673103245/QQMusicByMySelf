package qianfeng.qqmusicbymyself.showmusic.view.fragment.select_fg;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import qianfeng.qqmusicbymyself.BaseFragement;
import qianfeng.qqmusicbymyself.R;
import qianfeng.qqmusicbymyself.service.DownService;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;
import qianfeng.qqmusicbymyself.showmusic.presenter.select_presenter.Select_right_fg_Presenter;
import qianfeng.qqmusicbymyself.showmusic.view.adapter.Right_fg_Rv_Adapter;
import qianfeng.qqmusicbymyself.util.Consts;
import qianfeng.qqmusicbymyself.util.PlayerUtil;

//import qianfeng.qqmusicbymyself.BR;

/**
 * Created by Administrator on 2016/11/1 0001.
 */
public class RightFragment extends BaseFragement implements ISelectRightFgView {

    private RecyclerView recyclerView;
    private Select_right_fg_Presenter select_right_fg_presenter = new Select_right_fg_Presenter(this);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_right_fg_layout, container, false);
        initView(view);

        select_right_fg_presenter.start(3);

        return view;
    }

    private void initView(View view) {
        recyclerView = ((RecyclerView) view.findViewById(R.id.recyclerView));
    }


    @Override
    public void initRv(final List<MusicBean> list) {
        // 在这里才真正初始化 MusicBean.loader
//        MusicBean.loader = new ImageLoader((((App) getActivity().getApplication()).getRequestQueue()), new BitmapCache(getActivity()));

        // 这里的recycleView使用的是一个泛型的<>javaBean,使用MVM可以使用这个泛型的适配器
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


//        recyclerView.setAdapter(new Right_fg_Rv_Adapter<MusicBean>(list, getActivity(), R.layout.right_fg_rv_item,));
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(activeNetworkInfo == null || !activeNetworkInfo.isAvailable())
        {
            return;
        }
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
            if (list != null && list.size() > 0) {
                Right_fg_Rv_Adapter adapter = new Right_fg_Rv_Adapter(list, getActivity());
                adapter.setOnClickListener(new Right_fg_Rv_Adapter.OnClickListener() {
                    @Override
                    public void clickReItem(int position) {
                        // 一点击了item，马上进行流媒体的连接
                        Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show();
                        PlayerUtil.CURRENTLIST = list;
                        PlayerUtil.CURRENT_MUSICBEAN = list.get(position);
                        PlayerUtil.CURRENTPOSITION = position;
                        PlayerUtil.FLAGLIST = 1; // 表明是从网络上获取的List集合
                        Toast.makeText(getActivity(), list.get(position).getUrl() + "", Toast.LENGTH_SHORT).show();

                        PlayerUtil.startService(getActivity(), list.get(position), PlayerUtil.STATE_PLAY);
                        getActivity().sendBroadcast(new Intent(Consts.BROADRECEIVER1)); // 发送广播更新UI

                        // 并下载歌曲,下载歌词。
                        Intent downService = new Intent(getActivity(), DownService.class);
                        downService.putExtra("songid",PlayerUtil.CURRENT_MUSICBEAN.getSongid());
                        downService.putExtra("downUrl",PlayerUtil.CURRENT_MUSICBEAN.getDownUrl());
                        getActivity().startService(downService);

                        // 应该是UI的图片出了问题了。


                        //                PlayerUtil.startService(getActivity(),list.get(position).getUrl(),PlayerUtil.STATE_PLAY);

                        //                getActivity().sendBroadcast(new Intent(Consts.BROADRECEIVER1)); // 发送广播更新UI
                    }
                });
                recyclerView.setAdapter(adapter);

            }else
            {
                Toast.makeText(getActivity(), "RightFragment暂无数据，okhttp请求不到数据", Toast.LENGTH_SHORT).show();
            }
        }else
        {
            Toast.makeText(getActivity(), "请检查手机网络状态", Toast.LENGTH_SHORT).show();
        }


    }

    public void updataRightId(int id) {
        select_right_fg_presenter.start(id);
    }

}
