package qianfeng.qqmusicbymyself.util;

import android.content.Context;
import android.content.SharedPreferences;

import cn.sharesdk.framework.PlatformDb;

/**
 * Created by Administrator on 2016/11/11 0011.
 */
public class LoginUtil {
    public static void savePlatformDb(PlatformDb platformDb, Context context) {
        SharedPreferences sp = context.getSharedPreferences(Consts.SPNAME, Context.MODE_PRIVATE);
        if (platformDb != null) {
            String userName = platformDb.getUserName();
            String userIcon = platformDb.getUserIcon();
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username", userName);
            editor.putString("userface", userIcon);
            editor.commit();
        }else
        {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username","请登录");
            editor.putString("userface","");
            editor.commit();
        }
    }


}
