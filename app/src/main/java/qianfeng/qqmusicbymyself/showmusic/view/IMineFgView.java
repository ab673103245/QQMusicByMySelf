package qianfeng.qqmusicbymyself.showmusic.view;

import java.util.List;

import qianfeng.qqmusicbymyself.showmusic.model.bean.StringLoveBean;
import qianfeng.qqmusicbymyself.showmusic.model.bean.UserBean;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public interface IMineFgView  {
    void initMyListView(List<StringLoveBean> list);
    void initLoginlayout(UserBean userBean);
}
