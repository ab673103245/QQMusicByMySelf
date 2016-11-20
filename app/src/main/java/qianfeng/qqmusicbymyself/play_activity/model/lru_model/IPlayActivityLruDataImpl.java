package qianfeng.qqmusicbymyself.play_activity.model.lru_model;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import qianfeng.mylibrary.bean.LrcBean;
import qianfeng.qqmusicbymyself.util.Consts;
import qianfeng.qqmusicbymyself.util.JsonParse;
import qianfeng.qqmusicbymyself.util.NetUtil;
import qianfeng.qqmusicbymyself.util.SDCardUtil;

/**
 * Created by Administrator on 2016/11/5 0005.
 */
public class IPlayActivityLruDataImpl implements IPlayActivityLruData {
    @Override
    public void getLruBeanTextAndTime(final int songid, final OnOkHttpListener onOkHttpListener) {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            if (SDCardUtil.isLrcFileExist(songid)) {

                localLoadLrcBean(songid, onOkHttpListener);

            } else {

                Request request = new Request.Builder().url(String.format(Consts.SONGIDFINDLRC, songid)).build();
                NetUtil.getOkHttpClient().newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        onOkHttpListener.okHttpFailed(e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            // 先往本地缓存一份，再用io流把本地的读出来进行解析

                            String lrcFilePath = Environment.getExternalStorageDirectory() + File.separator + "MyLrcQQMusic";

                            File file = new File(lrcFilePath);
                            if (!file.exists()) {
                                file.mkdirs();
                            }

                            String filePath = lrcFilePath + File.separator + songid;

                            InputStream is = response.body().byteStream();

                            FileOutputStream fos = new FileOutputStream(filePath);

                            int len = 0;
                            byte[] b = new byte[1024];
                            while ((len = is.read(b)) != -1) {
                                fos.write(b, 0, len);
                                fos.flush();
                            }

                            localLoadLrcBean(songid, onOkHttpListener);

                        }
                    }
                });

            }// else
        } else  // SD卡没挂载
        {
            Log.d("google-my:", "getLruBeanTextAndTime: SD卡没挂载");
            Request request = new Request.Builder().url(String.format(Consts.SONGIDFINDLRC, songid)).build();
            NetUtil.getOkHttpClient().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onOkHttpListener.okHttpFailed(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        onOkHttpListener.okHttpSuccessful(JsonParse.parseLru2List(response.body().string()));
                    }
                }
            });
        }

    }

    private void localLoadLrcBean(int songid, OnOkHttpListener onOkHttpListener) {
        // 本地加载
        String lrcFilePath = Environment.getExternalStorageDirectory() + File.separator + "MyLrcQQMusic" + File.separator + songid;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(lrcFilePath)));
            String str = null;
            StringBuffer stringBuffer = new StringBuffer();
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str);
            }

            List<LrcBean> lrcBeenList = JsonParse.parseLru2List(stringBuffer.toString());

            onOkHttpListener.okHttpSuccessful(lrcBeenList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            onOkHttpListener.okHttpFailed(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            onOkHttpListener.okHttpFailed(e.getMessage());
        }
    }
}
