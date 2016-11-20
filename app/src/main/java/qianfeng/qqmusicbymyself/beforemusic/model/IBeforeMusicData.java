package qianfeng.qqmusicbymyself.beforemusic.model;

import android.content.Context;

import java.util.List;

import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;

/**
 * Created by Administrator on 2016/11/3 0003.
 */
public interface IBeforeMusicData {
    List<MusicBean> loadMusicBeanList(Context context);
    void saveMusicBeanList(Context context,MusicBean musicBean);
}
