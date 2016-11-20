package qianfeng.qqmusicbymyself.util;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.List;

import qianfeng.qqmusicbymyself.service.PlayerService;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;

/**
 * Created by Administrator on 2016/10/31 0031.
 */
public class PlayerUtil {
    public static int PLAYERCURRENTSTATE;
    public static final int STATE_STOP = 0;
    public static final int STATE_PLAY = 1; // 是状态，也是播放动作。
    public static final int STATE_PAUSE = 2;
    public static final int STATE_PAUSE_OR_PLAY = 4;

    public static MediaPlayer player;

    public static MusicBean CURRENT_MUSICBEAN;

    public static List<MusicBean> CURRENTLIST;
    public static int FLAGLIST = 0; // 本地歌曲flag是0,网络歌曲flag是1

    public static int CURRENTPOSITION = -1;



    public static void play(Context context, String urlPath) {
        if (player == null) {
            initPlayer(context);
        }

        player.reset();

        try {
//            PLAYERCURRENTSTATE = STATE_PLAY; // 异步准备有延迟，所以更新状态还是得放在主线程中。
            if(SDCardUtil.isSongExist(PlayerUtil.CURRENT_MUSICBEAN.getSongid())) // 用当前那个静态的MusicBean作为判断的标准啊！
            {
                // 播放从网络下载而来的歌曲
                String filePath = Environment.getExternalStorageDirectory() + File.separator + "MyQQMusic" + File.separator + PlayerUtil.CURRENT_MUSICBEAN.getSongid() + ".mp3";
                player.setDataSource(filePath);

            }else { // 播放网络歌曲或原本的本地歌曲
                player.setDataSource(urlPath);
            }
            player.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void pause() {
        if (player != null) {
            PLAYERCURRENTSTATE = STATE_PAUSE;
            player.pause();
        }
    }

    public static void stop() {
        if (player != null) {
            PLAYERCURRENTSTATE = STATE_STOP;
            player.stop();
            player.release();
            player = null;
        }
    }

    public static void playOrPause() {
        if (player != null && player.isPlaying()) {
            player.pause();
            PLAYERCURRENTSTATE = STATE_PAUSE;
        } else if (player != null && !player.isPlaying()) {
            player.start();
            PLAYERCURRENTSTATE = STATE_PLAY;

        }
    }

    private static void initPlayer(final Context context) {
        player = new MediaPlayer();
        // 监听异步准备的方法也在这里初始化
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                PLAYERCURRENTSTATE = STATE_PLAY;
                context.sendBroadcast(new Intent(Consts.BROADRECEIVER3));
            }
        });
    }

    public static void startService(Context context, MusicBean musicBean, int type) {


        PlayerUtil.CURRENT_MUSICBEAN = musicBean;

        Intent intent = new Intent(context, PlayerService.class);
        intent.putExtra("type",type); // 传入启动服务的type，在service能够判断是哪个动作。
        intent.putExtra("urlPath",musicBean.getUrl());
        context.startService(intent);
    }

}
