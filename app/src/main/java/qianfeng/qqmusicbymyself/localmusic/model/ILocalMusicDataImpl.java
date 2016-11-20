package qianfeng.qqmusicbymyself.localmusic.model;

import java.util.ArrayList;
import java.util.List;

import qianfeng.qqmusicbymyself.localmusic.model.bean.FourSortSongName_Count;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class ILocalMusicDataImpl implements ILocalMusicData {
    @Override
    public List<FourSortSongName_Count> getFour() {
        List<FourSortSongName_Count> list = new ArrayList<>();
        FourSortSongName_Count fourSortSongName_count1 = new FourSortSongName_Count();
        fourSortSongName_count1.setSort("歌曲");
        FourSortSongName_Count fourSortSongName_count2 = new FourSortSongName_Count();
        fourSortSongName_count2.setSort("歌手");
        FourSortSongName_Count fourSortSongName_count3 = new FourSortSongName_Count();
        fourSortSongName_count3.setSort("专辑");
        FourSortSongName_Count fourSortSongName_count4 = new FourSortSongName_Count();
        fourSortSongName_count4.setSort("文件夹");

        list.add(fourSortSongName_count1);
        list.add(fourSortSongName_count2);
        list.add(fourSortSongName_count3);
        list.add(fourSortSongName_count4);

        return list;
    }
}
