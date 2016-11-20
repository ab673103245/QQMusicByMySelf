package qianfeng.qqmusicbymyself.showmusic.model;

import java.util.List;

import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;

/**
 * Created by Administrator on 2016/10/26 0026.
 */
public interface OnDataLoadListener {
        // MVP模式，model和view各有一个接口，其中model的这个接口负责数据的读取(从网络中加载数据并解析)
        void onSuccessful(List<MusicBean> list);

        void onFailed(String errorMsg);

}
