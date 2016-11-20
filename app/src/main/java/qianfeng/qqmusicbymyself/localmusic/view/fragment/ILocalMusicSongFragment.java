package qianfeng.qqmusicbymyself.localmusic.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import qianfeng.qqmusicbymyself.BaseFragement;
import qianfeng.qqmusicbymyself.R;
import qianfeng.qqmusicbymyself.localmusic.presenter.LocalMusicSongPresenter;
import qianfeng.qqmusicbymyself.localmusic.view.adapter.LocalMusicSong_RvAdapter;
import qianfeng.qqmusicbymyself.showmusic.MainActivity;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;
import qianfeng.qqmusicbymyself.util.Consts;
import qianfeng.qqmusicbymyself.util.PlayerUtil;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class ILocalMusicSongFragment extends BaseFragement implements ILocalMusicSongView {
    private RecyclerView recyclerView;


    private LocalMusicSongPresenter localMusicSongPresenter = new LocalMusicSongPresenter(this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.localmusic_song_vp_fg_item, container, false);
        initView(view);

        localMusicSongPresenter.start(getActivity());


        return view;
    }

    private void initView(View view) {
        recyclerView = ((RecyclerView) view.findViewById(R.id.recyclerView));

    }

    @Override
    public void initMyListView(final List<MusicBean> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        LocalMusicSong_RvAdapter adapter = new LocalMusicSong_RvAdapter(list, getActivity());
        adapter.setOnType0ItemClickListener(new LocalMusicSong_RvAdapter.OnType0ItemClickListener() {
            @Override
            public void onType0ItemClick(int position) {
                Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show();
                PlayerUtil.CURRENT_MUSICBEAN = list.get(position);
                PlayerUtil.CURRENTLIST = list;

                PlayerUtil.CURRENTPOSITION = position;
                PlayerUtil.FLAGLIST = 0; // 表示本地播放
                PlayerUtil.startService(getActivity(), PlayerUtil.CURRENT_MUSICBEAN, PlayerUtil.STATE_PLAY);
                getActivity().sendBroadcast(new Intent(Consts.BROADRECEIVER1)); // 发送广播更新UI

                { // 把播放过的歌曲存进去本地数据库里面
//                    MusicBeanDao musicBeanDao = ((App) getActivity().getApplication()).getMusicBeanDao();
//                    musicBeanDao.insert(PlayerUtil.CURRENT_MUSICBEAN);
                    Log.d("google-my:", "onType0ItemClick: PlayerUtil.CURRENT_MUSICBEAN)" + PlayerUtil.CURRENT_MUSICBEAN.getSeconds());
                    localMusicSongPresenter.saveHistorySongList(PlayerUtil.CURRENT_MUSICBEAN);
                }

            }

            @Override
            public void onType0ImageViewClick(int position) {
                Toast.makeText(getActivity(), "只是一个Iv被点击", Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnType1ItemClickListener(new LocalMusicSong_RvAdapter.OnType1ItemClickListener() {
            @Override
            public void onType1ItemClick() { // 随机播放全部
                Toast.makeText(getActivity(), "随机播放全部", Toast.LENGTH_SHORT).show();

                PlayerUtil.CURRENTPOSITION = (int) (Math.random() * (list.size() - 2) + 2);
                Log.d("google-my:", "onType1ItemClick:  PlayerUtil.CURRENTPOSITION :" + PlayerUtil.CURRENTPOSITION);
                PlayerUtil.CURRENTLIST = list;
                PlayerUtil.CURRENT_MUSICBEAN = list.get(PlayerUtil.CURRENTPOSITION);
                PlayerUtil.FLAGLIST = 0; // 表示本地播放
                PlayerUtil.startService(getActivity(), PlayerUtil.CURRENT_MUSICBEAN, PlayerUtil.STATE_PLAY);
                {
                    localMusicSongPresenter.saveHistorySongList(PlayerUtil.CURRENT_MUSICBEAN);// 存进播放列表里
                }
                getActivity().sendBroadcast(new Intent(Consts.BROADRECEIVER1)); // 发送广播更新UI

            }
        });

        adapter.setOnType2ItemClickListener(new LocalMusicSong_RvAdapter.OnType2ItemClickListener() {
            @Override
            public void onType2ItemClick() { // 搜索本地歌曲
                Toast.makeText(getActivity(), "搜索本地歌曲", Toast.LENGTH_SHORT).show();
                MainActivity.ACTIVITY_STATE = 2;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ll,new LocalSearchSongFg()).commit();
            }
        });

        recyclerView.setAdapter(adapter);
    }
}
