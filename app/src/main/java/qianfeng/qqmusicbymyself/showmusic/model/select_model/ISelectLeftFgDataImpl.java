package qianfeng.qqmusicbymyself.showmusic.model.select_model;

import java.util.ArrayList;
import java.util.List;

import qianfeng.qqmusicbymyself.showmusic.model.bean.CategoryBean;

/**
 * Created by Administrator on 2016/11/1 0001.
 */
public class ISelectLeftFgDataImpl implements ISelectLeftFgData {
    /*
    3=欧美
5=内地
6=港台
16=韩国
17=日本
18=民谣
19=摇滚
23=销量
26=热歌
     */
    @Override
    public List<CategoryBean> getCategory() {

        List<CategoryBean> list = new ArrayList<>();
        list.add(new CategoryBean("欧美", 3 ));
        list.add(new CategoryBean("内地", 5 ));
        list.add(new CategoryBean("港台", 6 ));
        list.add(new CategoryBean("韩国", 16));
        list.add(new CategoryBean("日本", 17));
        list.add(new CategoryBean("民谣", 18));
        list.add(new CategoryBean("摇滚", 19));
        list.add(new CategoryBean("销量", 23));
        list.add(new CategoryBean("热歌", 26));

        return list;
    }
}
