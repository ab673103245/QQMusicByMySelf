package qianfeng.qqmusicbymyself.localmusic.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import qianfeng.qqmusicbymyself.BaseFragement;
import qianfeng.qqmusicbymyself.R;
import qianfeng.qqmusicbymyself.localmusic.presenter.LocalSearchMusicPersenter;
import qianfeng.qqmusicbymyself.localmusic.view.adapter.AutoCompleteTvAdapter;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;

/**
 * Created by Administrator on 2016/10/31 0031.
 */
public class LocalSearchSongFg extends BaseFragement implements ILocalSearchSongFgView {
    private ImageView iv_close;
    private AutoCompleteTextView autoCompleteTv;

    private LocalSearchMusicPersenter localSearchMusicPersenter = new LocalSearchMusicPersenter(this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.localsearchsong_fg, container, false);
        initView(view);

        localSearchMusicPersenter.start(getActivity());

        return view;

    }

    private void initView(View view) {
        iv_close = ((ImageView) view.findViewById(R.id.iiv));
        autoCompleteTv = ((AutoCompleteTextView) view.findViewById(R.id.autoCompleteTv));

    }

    @Override
    public void initAutoTv(List<MusicBean> list) {


        List<MusicBean> list2 = new ArrayList<>();

        MusicBean e = new MusicBean();
        e.setSingername("无尘恩");
        list2.add(e);

        MusicBean e1 = new MusicBean();
        e.setSingername("啊额");
        list2.add(e1);

        MusicBean e2 = new MusicBean();
        e2.setSingername("孙尚香");
        list2.add(e2);

        MusicBean e3 = new MusicBean();
        e3.setSingername("马云");
        list2.add(e3);


        autoCompleteTv.setAdapter(new AutoCompleteTvAdapter(list2, getActivity()));

    }
}
