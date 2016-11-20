package qianfeng.qqmusicbymyself.play_activity.model.lru_model;

/**
 * Created by Administrator on 2016/11/5 0005.
 */
public interface IPlayActivityLruData {
    void getLruBeanTextAndTime(int songid, OnOkHttpListener onOkHttpListener);
}
