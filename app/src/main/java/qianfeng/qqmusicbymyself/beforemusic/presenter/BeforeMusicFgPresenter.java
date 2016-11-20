package qianfeng.qqmusicbymyself.beforemusic.presenter;

import android.content.Context;

import java.util.List;

import qianfeng.qqmusicbymyself.BasePersenter;
import qianfeng.qqmusicbymyself.beforemusic.model.IBeforeMusicData;
import qianfeng.qqmusicbymyself.beforemusic.model.IBeforeMusicDataImpl;
import qianfeng.qqmusicbymyself.beforemusic.view.IBeforeMusicFgView;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;

/**
 * Created by Administrator on 2016/11/3 0003.
 */
public class BeforeMusicFgPresenter implements BasePersenter{

    private IBeforeMusicFgView iBeforeMusicFgView;
    private IBeforeMusicData iBeforeMusicData;

    public BeforeMusicFgPresenter(IBeforeMusicFgView iBeforeMusicFgView) {
        this.iBeforeMusicFgView = iBeforeMusicFgView;
        iBeforeMusicData = new IBeforeMusicDataImpl();
    }


    @Override
    public void start() {

    }

    @Override
    public void start(Context context) {
        List<MusicBean> musicBeen = iBeforeMusicData.loadMusicBeanList(context);
        iBeforeMusicFgView.initRv(musicBeen);
    }

    public void saveMusicBean(Context context,MusicBean musicBean)
    {
        iBeforeMusicData.saveMusicBeanList(context,musicBean);
    }



}
