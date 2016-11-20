package qianfeng.qqmusicbymyself.showmusic.model.bean;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class StringLoveBean {
    private int imgs;
    private String songname;
    private String songCount;

    @Override
    public String toString() {
        return "StringLoveBean{" +
                "imgs=" + imgs +
                ", songname='" + songname + '\'' +
                ", songCount='" + songCount + '\'' +
                '}';
    }

    public StringLoveBean(int imgs, String songname, String songCount) {
        this.imgs = imgs;
        this.songname = songname;
        this.songCount = songCount;
    }

    public StringLoveBean() {
    }

    public int getImgs() {
        return imgs;
    }

    public void setImgs(int imgs) {
        this.imgs = imgs;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public String getSongCount() {
        return songCount;
    }

    public void setSongCount(String songCount) {
        this.songCount = songCount;
    }
}
