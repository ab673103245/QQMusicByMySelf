package qianfeng.qqmusicbymyself.play_activity;

import java.util.List;

import qianfeng.mylibrary.bean.LrcBean;

/**
 * Created by Administrator on 2016/11/2 0002.
 */
public interface IPlayActivityView {
    void updatePlayerControl();
    void initLru(List<LrcBean> list);
}
