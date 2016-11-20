package qianfeng.qqmusicbymyself.showmusic.presenter;

import android.content.Context;

import java.util.List;

import qianfeng.qqmusicbymyself.BasePersenter;
import qianfeng.qqmusicbymyself.showmusic.model.IMineFgData;
import qianfeng.qqmusicbymyself.showmusic.model.IMineFgDataImpl;
import qianfeng.qqmusicbymyself.showmusic.model.bean.StringLoveBean;
import qianfeng.qqmusicbymyself.showmusic.view.IMineFgView;
import qianfeng.qqmusicbymyself.showmusic.view.fragment.MineFragment;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class MineFgPresenter implements BasePersenter{

    private IMineFgView iMineFgView;
    private IMineFgData iMineFgData;

    // 一般okHttp接口的实例化(enqueue.new Callback(){} )和handler的实例化，都是在presenter里面完成的！
    // 但是这里没有实例化okHttp的接口监听，所以就不需要Handler。
//    private Handler mHandler = new Handler();

    public MineFgPresenter(IMineFgView iMineFgView) {
        this.iMineFgView = iMineFgView;
        iMineFgData = new IMineFgDataImpl();
    }

    @Override
    public void start() {
        // 先拿到数据
        List<StringLoveBean> listItemName = iMineFgData.getListItemName();
        // 再显示
        iMineFgView.initMyListView(listItemName);
    }

    @Override
    public void start(Context context) {

    }

    // Presenter中的所有方法返回值都是void的
    public void loadUserInfoFromSP(Context context)
    {
        iMineFgView.initLoginlayout(iMineFgData.getSPUserInfo(((MineFragment) iMineFgView).getActivity()));
    }



}
