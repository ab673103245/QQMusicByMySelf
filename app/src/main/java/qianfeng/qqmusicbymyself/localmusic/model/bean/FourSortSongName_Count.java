package qianfeng.qqmusicbymyself.localmusic.model.bean;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class FourSortSongName_Count {
    private String sort;
    private int count;

    public FourSortSongName_Count(String sort, int count) {
        this.sort = sort;
        this.count = count;
    }

    public FourSortSongName_Count() {
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
