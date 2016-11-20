package qianfeng.qqmusicbymyself.showmusic.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import qianfeng.qqmusicbymyself.R;
import qianfeng.qqmusicbymyself.showmusic.model.bean.StringLoveBean;
import qianfeng.qqmusicbymyself.showmusic.model.bean.UserBean;
import qianfeng.qqmusicbymyself.util.Consts;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class IMineFgDataImpl implements IMineFgData {
    @Override
    public List<StringLoveBean> getListItemName() {
        // 从本地数据库中读取一些内容。
        List<StringLoveBean> list = new ArrayList<>();

        list.add( new StringLoveBean(R.drawable.qingsongxuanlv,"轻松旋律","26首"));
        list.add(  new StringLoveBean(R.drawable.jindianlaoge,"经典老歌","41首"));
        list.add(  new StringLoveBean(R.drawable.djwuqu,"DJ舞曲","19首"));
        list.add(  new StringLoveBean(R.drawable.yueyu,"粤语","6首"));
        list.add(new StringLoveBean(R.drawable.wozuiaiting,"我最爱听","27首"));

        return list;
    }

    @Override
    public UserBean getSPUserInfo(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences(Consts.SPNAME2, Context.MODE_PRIVATE);
        String username = sp.getString("username","请登录");
        String userface = sp.getString("userface", "");

        UserBean userBean = new UserBean();
        userBean.setUsername(username);
        userBean.setUserface(userface);

        return userBean;
    }
}
