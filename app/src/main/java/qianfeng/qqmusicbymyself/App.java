package qianfeng.qqmusicbymyself;

import android.app.Application;
import android.net.ConnectivityManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.greenrobot.greendao.database.Database;

import cn.sharesdk.framework.ShareSDK;
import qianfeng.qqmusicbymyself.showmusic.model.bean.DaoMaster;
import qianfeng.qqmusicbymyself.showmusic.model.bean.DaoSession;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBeanDao;


/**
 * Created by Administrator on 2016/11/2 0002.
 */
public class App extends Application {
    private RequestQueue requestQueue;

    private MusicBeanDao musicBeanDao;
    private ConnectivityManager connectivityManager;

    @Override
    public void onCreate() {
        super.onCreate();

        // 使用Volley必须要在App中初始化一个请求队列
        requestQueue = Volley.newRequestQueue(this);

        DaoMaster.DevOpenHelper dbHelper = new DaoMaster.DevOpenHelper(this,"music.db");

        Database db = dbHelper.getWritableDb();

        DaoSession daoSession = new DaoMaster(db).newSession();

        // 通过这个daoSession.getUserDao()：获取到所有的实体类，只要是添加了注解的。但是照这种情况来看，只能获取android.db这个数据库里面的所有实体类啊？
        // 这个userDao是我们不断操作数据库中的表的对象！
        musicBeanDao = daoSession.getMusicBeanDao();

        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        // shareSDK的初始化
        ShareSDK.initSDK(this);
    }

    public RequestQueue getRequestQueue()
    {
        return requestQueue;
    }

    public MusicBeanDao getMusicBeanDao()
    {
        return musicBeanDao;
    }

    public ConnectivityManager getConnectivityManager()
    {
        return connectivityManager;
    }
}
