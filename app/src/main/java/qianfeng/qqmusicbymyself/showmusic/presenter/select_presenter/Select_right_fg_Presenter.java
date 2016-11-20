package qianfeng.qqmusicbymyself.showmusic.presenter.select_presenter;

import android.content.Context;
import android.os.Handler;

import java.util.List;

import qianfeng.qqmusicbymyself.BasePersenter;
import qianfeng.qqmusicbymyself.showmusic.model.OnDataLoadListener;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;
import qianfeng.qqmusicbymyself.showmusic.model.select_model.ISelectRightFgData;
import qianfeng.qqmusicbymyself.showmusic.model.select_model.ISelectRightFgDataImpl;
import qianfeng.qqmusicbymyself.showmusic.view.fragment.select_fg.ISelectRightFgView;

/**
 * Created by Administrator on 2016/11/2 0002.
 */
public class Select_right_fg_Presenter implements BasePersenter{
    private ISelectRightFgView iSelectRightFgView;
    private ISelectRightFgData iSelectRightFgData;
    private Handler mHandler = new Handler();

    public Select_right_fg_Presenter(ISelectRightFgView iSelectRightFgView) {
        this.iSelectRightFgView = iSelectRightFgView;
        iSelectRightFgData = new ISelectRightFgDataImpl();
    }

    public void start(int id)
    {
        iSelectRightFgData.getNetSong(id, new OnDataLoadListener() {
            @Override
            public void onSuccessful(final List<MusicBean> list) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iSelectRightFgView.initRv(list);
                    }
                });

            }

            @Override
            public void onFailed(String errorMsg) {

            }
        });
    }

    @Override
    public void start() {

    }

    @Override
    public void start(Context context) {

    }

}
