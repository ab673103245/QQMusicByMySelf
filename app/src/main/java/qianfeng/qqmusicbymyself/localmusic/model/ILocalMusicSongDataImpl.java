package qianfeng.qqmusicbymyself.localmusic.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class ILocalMusicSongDataImpl implements ILocalMusicSongData {

    // 在这里调用data/data/com.android.provider.media/exteral.db/video(View)提供的数据库，
    // 专门查找本机的所有音频文件
    // 这里不需要在配置清单文件里面配置,但是需要上下文Context。

    @Override
    public List<MusicBean> getLocalMusic(Context context) {
        ContentResolver resolver = context.getContentResolver();

        List<MusicBean> list = new ArrayList<>();

        // 查看里面的exteral.db数据库
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()) {
            // 根据数据库中的字段的值，拿到自己想要的音频文件的路径字符串值
            String musicPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));// 在_data里面分类就能够得到本地音乐的文件夹的分类
            String display_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String albumName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

            MusicBean musicBean = new MusicBean();
            musicBean.setSongname(display_name);
            musicBean.setAlbumname(albumName);
            musicBean.setSingername(artist);
            musicBean.setSeconds((int) (duration / 1000));
            musicBean.setUrl(musicPath); // url:是本机的路径，还有网络在线播放的url
            musicBean.setType(0);
            list.add(musicBean);

        }

        cursor.close(); // 如果不关cursor是会出现问题的! app不稳定了，有时候会出错

        // 把MyListView的另外两种布局也加上，待会是ListView显示多种布局
        MusicBean musicBean2 = new MusicBean();
        musicBean2.setType(-2);
        list.add(musicBean2);

        MusicBean musicBean1 = new MusicBean();
        musicBean1.setType(-1);
        list.add(musicBean1);

        if (list != null && list.size() > 0) {
            resetList(list);
        }

        return list;
    }

    @Override
    public List<MusicBean> getLocalSearchMusic(Context context) {
        ContentResolver resolver = context.getContentResolver();

        List<MusicBean> list = new ArrayList<>();

        // 查看里面的exteral.db数据库
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()) {
            // 根据数据库中的字段的值，拿到自己想要的音频文件的路径字符串值
            String musicPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));// 在_data里面分类就能够得到本地音乐的文件夹的分类
            String display_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String albumName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

            MusicBean musicBean = new MusicBean();
            musicBean.setSongname(display_name);
            musicBean.setAlbumname(albumName);
            musicBean.setSingername(artist);
            musicBean.setSeconds((int) (duration / 1000));
            musicBean.setUrl(musicPath); // url:是本机的路径，还有网络在线播放的url
            musicBean.setType(0);
            list.add(musicBean);

        }

        cursor.close(); // 如果不关cursor是会出现问题的! app不稳定了，有时候会出错


        if (list != null && list.size() > 0) {
            resetList(list);
        }

        return list;
    }

    private void resetList(List<MusicBean> list2) {

        // 对list重新排序不需要返回值，直接在原地址上操作
        Collections.sort(list2, new Comparator<MusicBean>() {
            @Override
            public int compare(MusicBean o1, MusicBean o2) {
                return o1.getType().compareTo(o2.getType());
            }
        });

    }


}
