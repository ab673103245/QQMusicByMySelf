package qianfeng.qqmusicbymyself.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import qianfeng.qqmusicbymyself.R;

/**
 * Created by Administrator on 2016/11/4 0004.
 */
public class DownReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle("下载完成");
        builder.setTicker("一首歌曲已经下载完毕");
        builder.setSmallIcon(R.drawable.download_icon);
        builder.setAutoCancel(true);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(4,builder.build());
        manager.cancel(4);
    }
}
