package qianfeng.qqmusicbymyself.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import qianfeng.qqmusicbymyself.R;
import qianfeng.qqmusicbymyself.util.NetUtil;
import qianfeng.qqmusicbymyself.util.SDCardUtil;

/**
 * Created by Administrator on 2016/11/4 0004.
 */
public class DownService extends IntentService {
    private NotificationManager manager;
    private Notification.Builder builder;
    private Handler mHandler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
        builder = new Notification.Builder(this);

        builder.setSmallIcon(R.drawable.download_icon);
        builder.setContentTitle("正在下载...");
        builder.setContentInfo("已下载: 0%");

        manager = ((NotificationManager) getSystemService(NOTIFICATION_SERVICE));

        startForeground(2, builder.build());
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */

    public DownService() {
        super("name");
    }

    @Override
    protected void onHandleIntent(Intent intent) { // 这是在子线程中执行的！

        // 在下载之前先判断该歌曲存在不存在，如果存在，就不下载了，如果不存在，才去下载


        HttpURLConnection con = null;
        String downUrl = intent.getStringExtra("downUrl");
        final int songid = intent.getIntExtra("songid",7020182);

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (SDCardUtil.isSongExist(songid)) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DownService.this, "亲，该歌曲已经下载过了", Toast.LENGTH_SHORT).show();
                    }
                });
                return;
            }
        }


        final Request request = new Request.Builder().url(downUrl).build();
        NetUtil.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DownService.this, "亲，这首歌是要收费的", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {// 如果请求成功，做什么？先检查SD卡是否挂载了，如果挂载了，再检查该目录存在与否，如果不存在，新建目录，
                    // 是否下载看的是SDCardUtil中的isDownLoad()方法的返回值，如果返回false，才下载。如果是true，就是已下载
                    String filePath = Environment.getExternalStorageDirectory() + File.separator + "MyQQMusic";
                    File file = new File(filePath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    // 能够进入到这里来，一定是没下载的
                    // 没下载，那就下载呗
                    InputStream is = response.body().byteStream();
                    String songFile = songid + ".mp3";

                    FileOutputStream fos = new FileOutputStream(new File(filePath, songFile));
                    int currentLength = 0;
                    int totalLength = (int) response.body().contentLength();

                    int len = 0;
                    byte[] b = new byte[1024];
                    while ((len = is.read(b)) != -1) {
                        fos.write(b, 0, len);
                        fos.flush();

                        currentLength += len;
                        builder.setProgress(totalLength, currentLength, false);
                        builder.setContentInfo("已下载: " + String.format("%.2f", (currentLength * 1.0f / totalLength) * 100) + "%");
                        manager.notify(2, builder.build());
                    }

                    sendBroadcast(new Intent("FINISH.DOWNLOAD"));// 发送下载完成的广播，是静态注册的

                }
            }
        });


    }

}


//        FileOutputStream fos = null;
//        try {
//            URL url = new URL(downUrl);
//            con = (HttpURLConnection) url.openConnection();
//            con.setConnectTimeout(5 * 1000);
//            con.connect();
//            if (con.getResponseCode() == 200) {
//                String filePath = Environment.getExternalStorageDirectory() + File.separator + "MyQQMusic" + File.separator + songid + ".mp3";
//                int currentLength = 0;
//                int totalLength = con.getContentLength();
//
//                builder.setProgress(totalLength, currentLength, false);
//
//                startForeground(1,builder.build());
//
//                fos = new FileOutputStream(filePath);
//                InputStream is = con.getInputStream();
//                int len = 0;
//                byte[] b = new byte[1024];
//                while ((len = is.read(b)) != -1) {
//                    fos.write(b, 0, b.length);
//                    fos.flush();
//                    currentLength += len;
//                    builder.setContentInfo("已下载: " + String.format("%.2f", (currentLength * 1.0f / totalLength) * 100) + "%");
//
//                    manager.notify(1,builder.build());
//                }
//
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



