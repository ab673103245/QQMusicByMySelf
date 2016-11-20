package qianfeng.qqmusicbymyself.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;

/**
 * Created by Administrator on 2016/10/31 0031.
 */
public class MusicUtil {

    public static Bitmap getBitmap(String filePath) // 加载一张来自本地或来自网络的图片，url的字符串是不同的
    {
        if (filePath == null || "".equals(filePath) || filePath.startsWith("http")) // 如果是网络图片或者是地址不正确的图片
        {
            return null;
        }

        Bitmap bitmap = null;

        // 加载一张本地图片 file//:
        // 用MediaMetadataRetriever获取本地图片

        MediaMetadataRetriever mediaMetadataRetriever = null;

        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();

            mediaMetadataRetriever.setDataSource(filePath); // 这里是歌曲的url，并不是图片的url
            byte[] bytes = mediaMetadataRetriever.getEmbeddedPicture();

            if (bytes != null && bytes.length > 0) {
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }


        return bitmap;
    }
}
