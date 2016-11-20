package qianfeng.qqmusicbymyself.localmusic.model;

import android.content.Context;

import java.util.List;

import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public interface ILocalMusicSongData {
    List<MusicBean> getLocalMusic(Context context);
    List<MusicBean> getLocalSearchMusic(Context context);
}
