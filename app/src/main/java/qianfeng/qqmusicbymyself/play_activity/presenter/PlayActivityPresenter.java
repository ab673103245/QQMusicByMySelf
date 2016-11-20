package qianfeng.qqmusicbymyself.play_activity.presenter;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import java.util.List;

import qianfeng.mylibrary.bean.LrcBean;
import qianfeng.qqmusicbymyself.BasePersenter;
import qianfeng.qqmusicbymyself.localmusic.model.ILocalMusicSongData;
import qianfeng.qqmusicbymyself.localmusic.model.ILocalMusicSongDataImpl;
import qianfeng.qqmusicbymyself.play_activity.IPlayActivityView;
import qianfeng.qqmusicbymyself.play_activity.PlayActivity;
import qianfeng.qqmusicbymyself.play_activity.model.lru_model.IPlayActivityLruDataImpl;
import qianfeng.qqmusicbymyself.play_activity.model.lru_model.OnOkHttpListener;

/**
 * Created by Administrator on 2016/11/2 0002.
 */
public class PlayActivityPresenter implements BasePersenter{
    private IPlayActivityView iPlayActivityView;
    private ILocalMusicSongData iLocalMusicSongData;
    private IPlayActivityLruDataImpl iPlayActivityLruData;
    private Handler mHandler = new Handler();

    public PlayActivityPresenter(IPlayActivityView iPlayActivityView) {
        this.iPlayActivityView = iPlayActivityView;
        iLocalMusicSongData = new ILocalMusicSongDataImpl();
        iPlayActivityLruData = new IPlayActivityLruDataImpl();
    }

    @Override
    public void start() {
        iPlayActivityView.updatePlayerControl();
    }

    @Override
    public void start(Context context) {

    }

    public void loadLrc(int songid)
    {
        iPlayActivityLruData.getLruBeanTextAndTime(songid, new OnOkHttpListener() {
            @Override
            public void okHttpSuccessful(final List<LrcBean> list) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iPlayActivityView.initLru(list);
                    }
                });
            }

            @Override
            public void okHttpFailed(final String msg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(((PlayActivity) iPlayActivityView), msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
