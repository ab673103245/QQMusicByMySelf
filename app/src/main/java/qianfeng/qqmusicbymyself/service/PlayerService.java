package qianfeng.qqmusicbymyself.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import qianfeng.qqmusicbymyself.R;
import qianfeng.qqmusicbymyself.util.Consts;
import qianfeng.qqmusicbymyself.util.MusicUtil;
import qianfeng.qqmusicbymyself.util.NetUtil;
import qianfeng.qqmusicbymyself.util.PlayerUtil;

/**
 * Created by Administrator on 2016/10/31 0031.
 */
public class PlayerService extends Service {


    private NotificationManager manager;
    private Notification.Builder builder;
    private RemoteViews remoteViews;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 3:
                {
                    if (isCan) {
                        updataNotificationRemoteViews();
                        sendEmptyMessageDelayed(3,200);

                        final String albumpic_small = PlayerUtil.CURRENT_MUSICBEAN.getAlbumpic_small();
                        String fileName = albumpic_small.substring(albumpic_small.lastIndexOf("/") + 1); // 拿到volley在本地缓存的那张图片，在点击播放歌曲的时候，就已经对歌曲的图片进行缓存
                        Bitmap bitmap3 = BitmapFactory.decodeFile(new File(PlayerService.this.getExternalCacheDir(), fileName).getAbsolutePath());
                        Log.d("google-my:", "updataNotificationRemoteViews: 通知栏执行了网络图片下载?");
                        if (bitmap3 != null) {
                            remoteViews.setImageViewBitmap(R.id.music_thumbnail, bitmap3);
                            isCan = false;
                        }
                    }
                }
                    break;
            }
        }
    };

    private boolean isCan = true;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();

        builder = new Notification.Builder(this);

        // 一点击播放就能看到通知栏上的通知的，那只能在播放音乐这里做？我们是在哪里播放音乐的？我们是在service播放音乐的，这个播放音乐的线程由service管理
        // 第一次启动服务时，会调用onCreate方法，其余的都不会了。
        // 第一次启动服务时，就在这里显示一个自定义通知
        remoteViews = new RemoteViews(this.getPackageName(), R.layout.nf_layout);

        Intent intent3 = new Intent(this, PlayerService.class);
        intent3.putExtra("type", Consts.STOPSERVICE); // 在这里触发intent3里面携带的数据，来停止service里面的音乐播放.
        // getService()获得PendingIntent实例
        PendingIntent pendingIntent = PendingIntent.getService(this, 3, intent3, PendingIntent.FLAG_ONE_SHOT);

        // 给remoteViews里面的控件设置点击事件,一点击了id=R.id.nf_close_btn的控件，
        // 就立即执行PendingIntent里面的getService或者getActivity，具体看你怎么写
        remoteViews.setOnClickPendingIntent(R.id.nf_close_btn, pendingIntent);


        Intent intent4 = new Intent(this, PlayerService.class);
        intent4.putExtra("type", PlayerUtil.STATE_PAUSE_OR_PLAY);
        PendingIntent pendingIntent2 = PendingIntent.getService(this, 4, intent4, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.nf_pause_btn, pendingIntent2);


        Intent intent5 = new Intent(this, PlayerService.class);
        intent5.putExtra("type", Consts.PRESONGSERVICE);
        PendingIntent pendingIntent3 = PendingIntent.getService(this, 5, intent5, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.nf_pre_btn, pendingIntent3);


        Intent intent6 = new Intent(this, PlayerService.class);
        intent6.putExtra("type", Consts.NEXTSONGSERVICE);
        PendingIntent pendingIntent4 = PendingIntent.getService(this, 6, intent6, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.nf_next_btn, pendingIntent4);


        builder.setContent(remoteViews).setSmallIcon(R.drawable.default1);

        // 运行在前台的通知，更不容易被杀死
        startForeground(1, builder.build()); // 1是用来唯一标识这个builder.build()这个通知对象的。

        manager = ((NotificationManager) getSystemService(NOTIFICATION_SERVICE));

    }

    private void initPreSong() {

        if (PlayerUtil.FLAGLIST == 0) // 本地歌曲的FLAG是0，网络歌曲的FLAG是1
        {
            Log.d("google-my:", "initPreSong: PlayerUtil.CURRENTLIST.size():" + PlayerUtil.CURRENTLIST.size());
            Log.d("google-my:", "initPreSong: PlayerUtil.CURRENTPOSITION:" + PlayerUtil.CURRENTPOSITION);
            if (PlayerUtil.CURRENTPOSITION - 1 > 1) // 还没越界
            {
                // 播放上一首歌
                PlayerUtil.CURRENTPOSITION = PlayerUtil.CURRENTPOSITION - 1;
                PlayerUtil.startService(this, PlayerUtil.CURRENTLIST.get(PlayerUtil.CURRENTPOSITION), PlayerUtil.STATE_PLAY);
                PlayerUtil.CURRENT_MUSICBEAN = PlayerUtil.CURRENTLIST.get(PlayerUtil.CURRENTPOSITION);


            } else if (PlayerUtil.CURRENTPOSITION == 2) // 如果一开始点击的就是夜曲，就播放呗，再点击上一首，就是播放夜曲记录的那首歌呗
            {
                PlayerUtil.startService(this, PlayerUtil.CURRENTLIST.get(PlayerUtil.CURRENTPOSITION), PlayerUtil.STATE_PLAY);
                PlayerUtil.CURRENT_MUSICBEAN = PlayerUtil.CURRENTLIST.get(PlayerUtil.CURRENTPOSITION);
            } else { // 但不包括顶部的两个item，它们没有歌曲的url，它们只是一个按钮
                // 播放当前歌曲, 如果越界的话，就播放上次成功播放过的那首
                PlayerUtil.startService(this, PlayerUtil.CURRENT_MUSICBEAN, PlayerUtil.STATE_PLAY);
            }

        } else if (PlayerUtil.FLAGLIST == 1) // 网络歌曲
        {

        }

    }


    private void initNextSong() {
        if (PlayerUtil.FLAGLIST == 0) // 本地歌曲
        {
            if (PlayerUtil.CURRENTPOSITION + 1 < PlayerUtil.CURRENTLIST.size()) {
                PlayerUtil.CURRENTPOSITION = PlayerUtil.CURRENTPOSITION + 1;
                PlayerUtil.CURRENT_MUSICBEAN = PlayerUtil.CURRENTLIST.get(PlayerUtil.CURRENTPOSITION);
                PlayerUtil.startService(this, PlayerUtil.CURRENTLIST.get(PlayerUtil.CURRENTPOSITION), PlayerUtil.STATE_PLAY);
            } else {
                PlayerUtil.startService(this, PlayerUtil.CURRENTLIST.get(PlayerUtil.CURRENTPOSITION), PlayerUtil.STATE_PLAY);
            }

        } else if (PlayerUtil.FLAGLIST == 1)// 网络歌曲
        {

        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        isCan = true;

        updataNotificationRemoteViews();

        int type = intent.getIntExtra("type", 0);
        String urlPath = intent.getStringExtra("urlPath");


        switch (type) {
            case PlayerUtil.STATE_PLAY:
                PlayerUtil.play(this, urlPath);
                break;

            case PlayerUtil.STATE_PAUSE:
                PlayerUtil.pause();
                break;

            case PlayerUtil.STATE_STOP:
                PlayerUtil.stop();
                break;

            case Consts.STOPSERVICE:
                stopSelf(); // stopSelf()来调用自身的onDestory()方法
                // 停止服务之后，为啥还要发送广播？更新 暂停/播放 按钮？
                sendBroadcast(new Intent(Consts.BROADRECEIVER2));
                break;

            case PlayerUtil.STATE_PAUSE_OR_PLAY:
                PlayerUtil.playOrPause();
                updataNotificationRemoteViews();
                sendBroadcast(new Intent(Consts.BROADRECEIVER2));
                break;

            case Consts.PRESONGSERVICE:
                initPreSong();
                updataNotificationRemoteViews();
                sendBroadcast(new Intent(Consts.BROADRECEIVER2));
                break;


            case Consts.NEXTSONGSERVICE:
                initNextSong();
                updataNotificationRemoteViews();
                sendBroadcast(new Intent(Consts.BROADRECEIVER2));
                break;

//            case Consts.UPDATANOTIFICATIONREMOTEVIEWS: // 这里只是更新通知栏而已
//                updataNotificationRemoteViews();
////                mHandler.sendEmptyMessage(0);
//                break;


        }


        return super.onStartCommand(intent, flags, startId);
    }

    private void updataNotificationRemoteViews() {
        remoteViews.setTextViewText(R.id.music_name, PlayerUtil.CURRENT_MUSICBEAN.getSongname());

        if (PlayerUtil.CURRENT_MUSICBEAN.getUrl() != null && !"".equals(PlayerUtil.CURRENT_MUSICBEAN.getUrl())) {  // 加载本地图片
            Bitmap bitmap = MusicUtil.getBitmap(PlayerUtil.CURRENT_MUSICBEAN.getUrl());
            if (bitmap != null) {
                remoteViews.setImageViewBitmap(R.id.music_thumbnail, bitmap);
            } else {
//                Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.default1);
//                remoteViews.setImageViewBitmap(R.id.music_thumbnail, bitmap1);

                final String albumpic_small = PlayerUtil.CURRENT_MUSICBEAN.getAlbumpic_small();
                if (albumpic_small != null && !"".equals(albumpic_small)) // 加载网络图片
                {
                    String fileName = albumpic_small.substring(albumpic_small.lastIndexOf("/") + 1); // 拿到volley在本地缓存的那张图片，在点击播放歌曲的时候，就已经对歌曲的图片进行缓存
                    Bitmap bitmap3 = BitmapFactory.decodeFile(new File(this.getExternalCacheDir(), fileName).getAbsolutePath());
                    Log.d("google-my:", "updataNotificationRemoteViews: 通知栏执行了网络图片下载?");
                    if (bitmap3 != null) {
                        remoteViews.setImageViewBitmap(R.id.music_thumbnail, bitmap3);
                    } else {
                        Log.d("google-my:", "updataNotificationRemoteViews: bitmap == null");
                        Log.d("google-my:", "run: 子线程开始了吗");

                        okdownloadNotificationIcon(albumpic_small);

                    }
                }else //  如果也不是网络图片
                {
                    Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.default1);
                    remoteViews.setImageViewBitmap(R.id.music_thumbnail, bitmap1);
                }

            }
        }

//        final String albumpic_small = PlayerUtil.CURRENT_MUSICBEAN.getAlbumpic_small();
//        if (albumpic_small != null && !"".equals(albumpic_small)) // 加载网络图片
//        {
//            String fileName = albumpic_small.substring(albumpic_small.lastIndexOf("/") + 1); // 拿到volley在本地缓存的那张图片，在点击播放歌曲的时候，就已经对歌曲的图片进行缓存
//            Bitmap bitmap3 = BitmapFactory.decodeFile(new File(this.getExternalCacheDir(), fileName).getAbsolutePath());
//            if (bitmap3 != null) {
//                remoteViews.setImageViewBitmap(R.id.music_thumbnail, bitmap3);
//            } else {
//                Log.d("google-my:", "updataNotificationRemoteViews: bitmap == null");
//                Log.d("google-my:", "run: 子线程开始了吗");
//
//                okdownloadNotificationIcon(albumpic_small);
//
//            }
//        }


        if (PlayerUtil.PLAYERCURRENTSTATE == PlayerUtil.STATE_PLAY) {

            remoteViews.setImageViewBitmap(R.id.nf_pause_btn, BitmapFactory.decodeResource(getResources(), R.drawable.btn_notification_player_stop_pressed));
        } else if (PlayerUtil.PLAYERCURRENTSTATE == PlayerUtil.STATE_PAUSE) {//暂停的时候

            remoteViews.setImageViewBitmap(R.id.nf_pause_btn, BitmapFactory.decodeResource(getResources(), R.drawable.ring_btnplay));
        } else if (PlayerUtil.PLAYERCURRENTSTATE == PlayerUtil.STATE_STOP) {

        }

        manager.notify(1, builder.build());
    }

    private void okdownloadNotificationIcon(String albumpic_small) {
        Request request = new Request.Builder().url(albumpic_small).build();
        NetUtil.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PlayerService.this, "通知栏图片下载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if(response.isSuccessful())
                {

                    final Bitmap bitmap1 = BitmapFactory.decodeStream(response.body().byteStream());

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (bitmap1 != null) {
                                Log.d("google-my:", "updataNotificationRemoteViews: 真的要自己下载通知栏图片了");
                                remoteViews.setImageViewBitmap(R.id.music_thumbnail, bitmap1);
                            } else {
                                Log.d("google-my:", "updataNotificationRemoteViews:http下载图片还下不到呢");
                                Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.default1);
                                remoteViews.setImageViewBitmap(R.id.music_thumbnail, bitmap2);
                            }

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onDestroy() {// 服务一旦被销毁，就会调用这个方法,在这个方法内停止对音乐歌曲的播放。
        super.onDestroy();
        PlayerUtil.stop(); // 服务被杀死时，停止掉MediaPlayer里面播放的歌曲
    }
}
