package qianfeng.qqmusicbymyself.showmusic.view;

import java.util.List;

import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public interface ISingleSongView {
    void initRvSuccess(List<MusicBean> musicBeanList);
    void initRvFailed(String msg);
}
