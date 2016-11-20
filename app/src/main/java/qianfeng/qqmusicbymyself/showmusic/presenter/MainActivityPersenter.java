package qianfeng.qqmusicbymyself.showmusic.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.List;

import qianfeng.qqmusicbymyself.App;
import qianfeng.qqmusicbymyself.BasePersenter;
import qianfeng.qqmusicbymyself.showmusic.MainActivity;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBeanDao;
import qianfeng.qqmusicbymyself.showmusic.view.IMainActivityView;
import qianfeng.qqmusicbymyself.util.Consts;
import qianfeng.qqmusicbymyself.util.PlayerUtil;

/**
 * Created by Administrator on 2016/10/31 0031.
 */
public class MainActivityPersenter implements BasePersenter{

    // 只有在Presenter中才可以拿到上下文，在这里进行sp的读写
    private IMainActivityView iMainActivityView;
    private static SharedPreferences sp = null;

    public MainActivityPersenter(IMainActivityView iMainActivityView) {
        this.iMainActivityView = iMainActivityView;
    }

    @Override
    public void start() {

    }

    @Override
    public void start(Context context) {
        iMainActivityView.updataUI(loadSP(context));
    }

    public MusicBean loadSP(Context context)
    {
        MusicBean musicBean = null;
        if(sp == null)
        {
            sp = context.getSharedPreferences(Consts.SPNAME,Context.MODE_PRIVATE);
        }

        if(sp != null)
         {
             String songName = sp.getString("songName","QQ音乐,陪伴你我");
             Log.d("google-my:", "loadSP: songName:" + songName);
             String songImg = sp.getString("songImg","");
             Log.d("google-my:", "loadSP: songImg:" + songImg);
             String singerName = sp.getString("singerName","");
             Log.d("google-my:", "loadSP: singerName:" + singerName);

             String fromNetImg = sp.getString("fromNetImg", "");

             int seconds = sp.getInt("seconds", 0);

             int songid = sp.getInt("songid", 0);

             musicBean = new MusicBean();

             musicBean.setSongname(songName);
             musicBean.setUrl(songImg);
             musicBean.setSeconds(seconds);
             musicBean.setSongid(songid);
             musicBean.setAlbumpic_small(fromNetImg);
             musicBean.setSingername(singerName);

             PlayerUtil.CURRENT_MUSICBEAN = musicBean;

         }

        return musicBean;
    }

    public void saveSP(Context context)
    {

        sp.edit().putString("songName",PlayerUtil.CURRENT_MUSICBEAN.getSongname())
                .putString("songImg",PlayerUtil.CURRENT_MUSICBEAN.getUrl()) //在本地歌曲中，其实是MusicPath()，音乐文件的路径，用于获取CustomImageView歌曲图片
                .putString("fromNetImg",PlayerUtil.CURRENT_MUSICBEAN.getAlbumpic_small())
                .putString("singerName",PlayerUtil.CURRENT_MUSICBEAN.getSingername())
                .putInt("seconds",PlayerUtil.CURRENT_MUSICBEAN.getSeconds())
                .putInt("songid",PlayerUtil.CURRENT_MUSICBEAN.getSongid())
                .commit();

    }

    private boolean isInsert = false;

    public void saveMusicBeanDao(MusicBean musicBean)
    {
        MusicBeanDao musicBeanDao = ((App) ((MainActivity) iMainActivityView).getApplication()).getMusicBeanDao();

        List<MusicBean> list = musicBeanDao.queryBuilder().orderDesc(MusicBeanDao.Properties.Id).list();

        if (list != null && list.size() == 0) {

            Log.d("google-my:", "saveHistorySongList:songid:: " + musicBean.getSongid());
            Log.d("google-my:", "saveHistorySongList:songid:: " + musicBean.getSongid());
            Log.d("google-my:", "saveHistorySongList:songid:: ---------------------------");

            musicBeanDao.insert(musicBean);

        } else if (list != null && list.size() > 0) {

            // 提示：本地歌曲的id全部都是一样的
            for (int i = 0; i < list.size(); i++) {
                MusicBean musicBean1 = list.get(i);
                isInsert = false;
                if (musicBean.getSongname().equals(musicBean1.getSongname()) && musicBean.getSingername().equals(musicBean1.getSingername())) {
                    // 如果表中已有的话，那就更新此条数据
                    isInsert = true;
                    {
                        MusicBean musicBean2 = new MusicBean();
                        musicBean2.setSongname(musicBean1.getSongname());
                        musicBean2.setSongid(musicBean1.getSongid());
                        musicBean2.setSingername(musicBean1.getSingername());
                        musicBean2.setAlbumpic_small(musicBean1.getAlbumpic_small());
                        musicBean2.setUrl(musicBean1.getUrl());
                        int playcount = musicBean.getPlaycount();
                        playcount++;
                        musicBean2.setPlaycount(playcount);
                        musicBean2.setDownUrl(musicBean1.getDownUrl());
                        musicBeanDao.delete(musicBean1);
                        musicBeanDao.insert(musicBean2);
                    }
                    break;
                }
            }

            if (!isInsert) {
                musicBeanDao.insert(musicBean);
            }
        }
    }


}
