package qianfeng.qqmusicbymyself.localmusic.presenter;

import android.content.Context;

import java.util.List;

import qianfeng.qqmusicbymyself.BasePersenter;
import qianfeng.qqmusicbymyself.localmusic.model.ILocalMusicSongData;
import qianfeng.qqmusicbymyself.localmusic.model.ILocalMusicSongDataImpl;
import qianfeng.qqmusicbymyself.localmusic.view.fragment.ILocalSearchSongFgView;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;

/**
 * Created by Administrator on 2016/10/31 0031.
 */
public class LocalSearchMusicPersenter implements BasePersenter{

    private ILocalSearchSongFgView iLocalSearchSongFgView;
    private ILocalMusicSongData iLocalMusicSongData;

    public LocalSearchMusicPersenter(ILocalSearchSongFgView iLocalSearchSongFgView) {
        this.iLocalSearchSongFgView = iLocalSearchSongFgView;
        iLocalMusicSongData = new ILocalMusicSongDataImpl();
    }

    @Override
    public void start() {

    }

    @Override
    public void start(Context context) {
        // 这样返回的list集合就是只有歌曲的list集合！不含其他布局的list集合
        List<MusicBean> localSearchMusic = iLocalMusicSongData.getLocalSearchMusic(context);

        iLocalSearchSongFgView.initAutoTv(localSearchMusic);
    }



}
