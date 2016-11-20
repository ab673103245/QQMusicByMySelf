package qianfeng.qqmusicbymyself.beforemusic;

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
import qianfeng.qqmusicbymyself.beforemusic.presenter.BeforeMusicFgPresenter;
import qianfeng.qqmusicbymyself.beforemusic.view.IBeforeMusicFgView;
import qianfeng.qqmusicbymyself.beforemusic.view.adapter.MyRvAdapter;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;
import qianfeng.qqmusicbymyself.util.Consts;
import qianfeng.qqmusicbymyself.util.PlayerUtil;

/**
 * Created by Administrator on 2016/11/3 0003.
 */
public class BeforeMusicFragment extends BaseFragement implements IBeforeMusicFgView {

    private RecyclerView recyclerView;
    private BeforeMusicFgPresenter beforeMusicFgPresenter = new BeforeMusicFgPresenter(this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.before_fg_layout, container, false);

        initView(view);

        beforeMusicFgPresenter.start(getActivity());

        return view;
    }

    private void initView(View view) {
        recyclerView = ((RecyclerView) view.findViewById(R.id.recyclerView));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initRv(final List<MusicBean> list) {
        if (list != null && list.size() > 0) {
            MyRvAdapter adapter = new MyRvAdapter(list, getActivity());
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new MyRvAdapter.OnItemClickListener() {
                @Override
                public void onClick(int position) {
                    PlayerUtil.CURRENTLIST = list;
                    PlayerUtil.CURRENTPOSITION = position;
                    PlayerUtil.CURRENT_MUSICBEAN = list.get(position);
                    PlayerUtil.startService(getActivity(),PlayerUtil.CURRENT_MUSICBEAN,PlayerUtil.STATE_PLAY);

                    {
                        beforeMusicFgPresenter.saveMusicBean(getActivity(),PlayerUtil.CURRENT_MUSICBEAN);
                    }

                    getActivity().sendBroadcast(new Intent(Consts.BROADRECEIVER1)); // 发送广播更新UI
                }
            });
        } else {
            Toast.makeText(getActivity(), "music.db里面没有数据啦", Toast.LENGTH_SHORT).show();
        }
    }
}
