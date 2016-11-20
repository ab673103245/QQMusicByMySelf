package qianfeng.qqmusicbymyself.showmusic.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/28 0028.
 */
public class ISecondSearchDataImpl implements ISecondSearchData {
    @Override
    public List<String> FiveStringArray() {
        List<String> list = new ArrayList<>();
        list.add("单曲");
        list.add("MV");
        list.add("专辑");
        list.add("歌单");
        list.add("歌词");
        return list;
    }
}
