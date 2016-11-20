package qianfeng.qqmusicbymyself.localmusic.presenter;

import android.content.Context;
import android.util.Log;

import java.util.List;

import qianfeng.qqmusicbymyself.App;
import qianfeng.qqmusicbymyself.BasePersenter;
import qianfeng.qqmusicbymyself.localmusic.model.ILocalMusicSongData;
import qianfeng.qqmusicbymyself.localmusic.model.ILocalMusicSongDataImpl;
import qianfeng.qqmusicbymyself.localmusic.view.fragment.ILocalMusicSongFragment;
import qianfeng.qqmusicbymyself.localmusic.view.fragment.ILocalMusicSongView;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBeanDao;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class LocalMusicSongPresenter implements BasePersenter {
    private ILocalMusicSongView iLocalMusicSongView;
    private ILocalMusicSongData iLocalMusicSongData;

    private boolean isInsert = false;
    private MusicBean currentMusicBean;

    public LocalMusicSongPresenter(ILocalMusicSongView iLocalMusicSongView) {
        this.iLocalMusicSongView = iLocalMusicSongView;
        iLocalMusicSongData = new ILocalMusicSongDataImpl();
    }

    @Override
    public void start() {

    }

    @Override
    public void start(Context context) {
        iLocalMusicSongView.initMyListView(iLocalMusicSongData.getLocalMusic(context));
    }


    public void saveHistorySongList(MusicBean musicBean) {

        MusicBeanDao musicBeanDao = ((App) ((ILocalMusicSongFragment) iLocalMusicSongView).getActivity().getApplication()).getMusicBeanDao();

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
                if(musicBean.getSongname().equals(musicBean1.getSongname()) && musicBean.getSingername().equals(musicBean1.getSingername()))
                {
                    // 如果表中已有的话，那就更新此条数据
                    isInsert = true;
                    {
                        MusicBean musicBean2 = new MusicBean();
                        musicBean2.setSongname(musicBean.getSongname());
                        musicBean2.setSongid(musicBean.getSongid());
                        musicBean2.setSingername(musicBean.getSingername());
                        musicBean2.setAlbumpic_small(musicBean.getAlbumpic_small());
                        musicBean2.setUrl(musicBean.getUrl());
                        musicBean2.setSeconds(musicBean.getSeconds());
                        musicBean2.setDownUrl(musicBean.getDownUrl());
                        int playcount = musicBean.getPlaycount();
                        playcount++;
                        musicBean2.setPlaycount(playcount);
                        musicBeanDao.delete(musicBean1);
                        musicBeanDao.insert(musicBean2);
                    }
                    break;
                }
            }

            if(!isInsert)
            {
                musicBeanDao.insert(musicBean);
            }

////            // 验重,歌曲id或者歌名和歌手名一致
//            currentMusicBean = list.get(list.size() - 1);
////            //|| (currentMusicBean.getSongname().equals(musicBean.getSongname()) && currentMusicBean.getSingername().equals(musicBean.getSingername()))
//            if(currentMusicBean.getSongid() == musicBean.getSongid() ){
////                // 如果和最新一条数据相等的话，就什么也不做
//            }else if((currentMusicBean.getSongname().equals(musicBean.getSongname()) && currentMusicBean.getSingername().equals(musicBean.getSingername())))
//            {
////
//            }else
//            {
////                 否则的话还是要验重啊！
//                //              // 验完重之后才添加啊！
//                for (int i = 0; i < list.size(); i++) {
//                    isInsert = false;
//                    // 如果表中已有数据，就将其id增大，继续插入
//                    //|| (musicBean.getSongname().equals(list.get(i).getSongname()) && musicBean.getSingername().equals(list.get(i).getSingername()))
//                    if(musicBean.getSongid() == list.get(i).getSongid())
//                    {
//                        isInsert = true;
//                        MusicBean musicBean1 = new MusicBean();
//                        musicBean1.setSongid(list.get(i).getSongid());
//                        musicBean1.setSongname(list.get(i).getSongname());
//                        musicBean1.setSingername(list.get(i).getSingername());
//                        musicBean1.setUrl(list.get(i).getUrl());
//                        musicBean1.setAlbumpic_small(list.get(i).getAlbumpic_small());
//
//                        musicBean1.setDownUrl(list.get(i).getDownUrl());
//                        musicBean1.setAlbumname(list.get(i).getAlbumname());
//                        musicBean1.setAlbumid(list.get(i).getAlbumid());
//                        musicBean1.setAlbummid(list.get(i).getAlbummid());
//
//                        musicBeanDao.delete(list.get(i));
//                        musicBeanDao.insert(musicBean1);
//
////                                    UserSearchHistory userSearchHistory1 = new UserSearchHistory();
////                                    userSearchHistory1.setSearchHistory(list.get(i).get());
////                                    userSearchHistoryDao.delete(list1.get(i));// 先删除旧数据
////                                    userSearchHistoryDao.insert(userSearchHistory1); // 再添加新数据
//                        break;// 跳出for循环
//                    }
//                    if(!isInsert)
//                    {
//                        musicBeanDao.insert(musicBean);
//                    }
//                    Log.d("google-my:", "saveHistorySongList: saveHistorySongList: what ? saveMusicBeanDao代码全部执行完?");
////
//            }
//                      Log.d("google-my:", "LocalMusicFgPersenter : saveHistorySongList: what ? saveMusicBeanDao代码全部执行完?");
//
//            }

        }
//
//        }
    }


    public List<MusicBean> loadMusicBeanDao() {
        MusicBeanDao musicBeanDao = ((App) ((ILocalMusicSongFragment) iLocalMusicSongView).getActivity().getApplication()).getMusicBeanDao();

        List<MusicBean> list = musicBeanDao.queryBuilder().orderDesc(MusicBeanDao.Properties.Id).list();

        return list;

    }


}
