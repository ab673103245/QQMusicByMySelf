package qianfeng.qqmusicbymyself.showmusic.model;

import android.content.Context;

import java.util.List;

import qianfeng.qqmusicbymyself.showmusic.model.bean.StringLoveBean;
import qianfeng.qqmusicbymyself.showmusic.model.bean.UserBean;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public interface IMineFgData {
    List<StringLoveBean> getListItemName();
     UserBean getSPUserInfo(Context context);
}
