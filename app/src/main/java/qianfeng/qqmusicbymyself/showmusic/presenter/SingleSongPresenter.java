package qianfeng.qqmusicbymyself.showmusic.presenter;

import android.content.Context;
import android.os.Handler;

import java.util.List;

import qianfeng.qqmusicbymyself.BasePersenter;
import qianfeng.qqmusicbymyself.showmusic.model.ISingleSongData;
import qianfeng.qqmusicbymyself.showmusic.model.ISingleSongDataImpl;
import qianfeng.qqmusicbymyself.showmusic.model.OnDataLoadListener;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;
import qianfeng.qqmusicbymyself.showmusic.view.ISingleSongView;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class SingleSongPresenter implements BasePersenter{
    private ISingleSongView iSingleSongView;
    private ISingleSongData iSingleSongData;

    private Handler mHandler = new Handler();


    public SingleSongPresenter(ISingleSongView iSingleSongView) {
        this.iSingleSongView = iSingleSongView;
        iSingleSongData = new ISingleSongDataImpl();
    }

    @Override
    public void start() {
        // start的时候调用initRv来给Rv中的适配器提供数据
    }

    @Override
    public void start(Context context) {

    }

    // 真的是在presenter里面new出okHttp的enqueue的实例
    public void start(String keyword)
    {
       iSingleSongData.getSearchData(keyword, new OnDataLoadListener() {
           @Override
           public void onSuccessful(final List<MusicBean> list) { // 记住okHttp的这个网络请求enqueue是在子线程中执行的,所以要用handler更新
               mHandler.post(new Runnable() {
                   @Override
                   public void run() {
                       // 用Data获取到的数据list，来在okHttp的成功拿到数据这里调用View的更新UI方法来更新。
                       iSingleSongView.initRvSuccess(list);
                   }
               });
           }

           @Override
           public void onFailed(final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iSingleSongView.initRvFailed(errorMsg);
                    }
                });
           }
       });

    }

}
