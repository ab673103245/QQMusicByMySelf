package qianfeng.qqmusicbymyself.beforemusic.model;

import android.content.Context;
import android.util.Log;

import java.util.List;

import qianfeng.qqmusicbymyself.App;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBeanDao;

/**
 * Created by Administrator on 2016/11/3 0003.
 */
public class IBeforeMusicDataImpl implements IBeforeMusicData {

    private boolean isInsert = false;

    @Override
    public List<MusicBean> loadMusicBeanList(Context context) {
        MusicBeanDao musicBeanDao = ((App) context.getApplicationContext()).getMusicBeanDao();
        List<MusicBean> list = musicBeanDao.queryBuilder().orderDesc(MusicBeanDao.Properties.Id).list();
        return list;
    }

    @Override
    public void saveMusicBeanList(Context context, MusicBean musicBean) {
        MusicBeanDao musicBeanDao = ((App) context.getApplicationContext()).getMusicBeanDao();
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
