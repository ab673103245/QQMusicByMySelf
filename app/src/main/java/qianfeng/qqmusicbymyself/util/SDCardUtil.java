package qianfeng.qqmusicbymyself.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2016/11/4 0004.
 */
public class SDCardUtil {
    public static boolean isSongExist(int songid)
    {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) // 如果SD卡挂载了
        {
            String filePath = Environment.getExternalStorageDirectory() + File.separator + "MyQQMusic" + File.separator + songid + ".mp3";
            File file = new File(filePath);
            if(file.exists())
            {
                return true;
            }
        }
        return false;
    }

    public static boolean isLrcFileExist(int songid)
    {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            String lrcFilePath = Environment.getExternalStorageDirectory() + File.separator + "MyLrcQQMusic" + File.separator + songid;
            File file = new File(lrcFilePath);
            if(file.exists())
            {
                return true;
            }
        }

        return false;
    }



}
