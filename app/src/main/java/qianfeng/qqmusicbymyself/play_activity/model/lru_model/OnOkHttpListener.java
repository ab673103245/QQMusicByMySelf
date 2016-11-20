package qianfeng.qqmusicbymyself.play_activity.model.lru_model;

import java.util.List;

import qianfeng.mylibrary.bean.LrcBean;

/**
 * Created by Administrator on 2016/11/5 0005.
 */
public interface OnOkHttpListener {
    void okHttpSuccessful(List<LrcBean> list);
    void okHttpFailed(String msg);
}
