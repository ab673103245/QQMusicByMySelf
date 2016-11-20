package qianfeng.qqmusicbymyself.showmusic.view.fragment.search_fg;

import android.content.Intent;
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
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;
import qianfeng.qqmusicbymyself.showmusic.presenter.SingleSongPresenter;
import qianfeng.qqmusicbymyself.showmusic.view.ISingleSongView;
import qianfeng.qqmusicbymyself.showmusic.view.adapter.SingleSongRvAdapter;
import qianfeng.qqmusicbymyself.util.Consts;
import qianfeng.qqmusicbymyself.util.PlayerUtil;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class SingleSongFragment extends BaseFragement implements ISingleSongView{
    private RecyclerView recyclerView;

    private SingleSongPresenter singleSongPresenter = new SingleSongPresenter(this);

    // 只有静态注册的Fragment需要这个if(view==null)验证
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.singlesonglist_fg, container, false);
        initView(view);

        //用start方法开启网络请求
        singleSongPresenter.start(Consts.selectSongName);

        return view;
    }

    private void initView(View view) {
        recyclerView = ((RecyclerView) view.findViewById(R.id.recyclerView));
    }

    @Override
    public void initRvSuccess(final List<MusicBean> musicBeanList) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SingleSongRvAdapter adapter = new SingleSongRvAdapter(musicBeanList,getActivity());

        adapter.setOnItemCilckListener(new SingleSongRvAdapter.OnItemCilckListener() {
            @Override
            public void onItemCilck(int position) {
                Toast.makeText(getActivity(), musicBeanList.get(position).getUrl() + ":::0", Toast.LENGTH_SHORT).show();

                // 那么就是在这里进行歌曲的点击播放
                PlayerUtil.CURRENTLIST = musicBeanList;
                PlayerUtil.CURRENTPOSITION = position;
                PlayerUtil.CURRENT_MUSICBEAN = musicBeanList.get(position);
                PlayerUtil.FLAGLIST = 1;
                PlayerUtil.startService(getActivity(), PlayerUtil.CURRENT_MUSICBEAN, PlayerUtil.STATE_PLAY);

                getActivity().sendBroadcast(new Intent(Consts.BROADRECEIVER1)); // 发送广播更新UI

//                // 并下载歌曲,下载歌词。
//                Intent downService = new Intent(getActivity(), DownService.class);
//                downService.putExtra("songid",PlayerUtil.CURRENT_MUSICBEAN.getSongid());
//                downService.putExtra("downUrl",PlayerUtil.CURRENT_MUSICBEAN.getDownUrl());
//                getActivity().startService(downService);

            }

            @Override
            public void onAddIvClick(int position) {
                Toast.makeText(getActivity(), position + ":::1", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownIvClick(int position) {
                Toast.makeText(getActivity(), position + ":::2", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);

    }

    @Override
    public void initRvFailed(String msg) {
        Toast.makeText(getActivity(), "NetWord Fail!", Toast.LENGTH_SHORT).show();
    }
}
