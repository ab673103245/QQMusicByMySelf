package qianfeng.qqmusicbymyself.showmusic.model.bean;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import qianfeng.qqmusicbymyself.R;


/**
 * Created by 王松 on 2016/10/17.
 */

@Entity
public class MusicBean {
    @Id
    private Long id;
    @Property
    private String songname;
    @Property
    private int seconds; // 歌曲时长
    @Property
    private String albummid;
    @Property
    private int songid;
    @Property
    private int singerid;
    @Property
    private String albumpic_big;
    @Property
    private String albumpic_small;
    @Property
    private String downUrl;
    @Property
    private String url;
    @Property
    private String singername;
    @Property
    private int albumid;

    @Property
    private Integer type; // type是用来做-2 -1 0那里的listView的多种item显示的
    @Property
    private int playcount;

    public static ImageLoader loader;

    @Override
    public String toString() {
        return "MusicBean{" +
                "id=" + id +
                ", songname='" + songname + '\'' +
                ", seconds=" + seconds +
                ", albummid='" + albummid + '\'' +
                ", songid=" + songid +
                ", singerid=" + singerid +
                ", albumpic_big='" + albumpic_big + '\'' +
                ", albumpic_small='" + albumpic_small + '\'' +
                ", downUrl='" + downUrl + '\'' +
                ", url='" + url + '\'' +
                ", singername='" + singername + '\'' +
                ", albumid=" + albumid +
                ", type=" + type +
                ", playcount=" + playcount +
                ", keyword='" + keyword + '\'' +
                ", albumname='" + albumname + '\'' +
                '}';
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    private String keyword;
    private String albumname;

    public String getAlbumname() {
        return albumname;
    }

    public void setAlbumname(String albumname) {
        this.albumname = albumname;
    }

    public int getPlaycount() {
        return playcount;
    }

    public void setPlaycount(int playcount) {
        this.playcount = playcount;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    //    public void onItemClick(View view)
//    {
//        PlayerUtil.startService(view.getContext(),this,PlayerUtil.PLAY);
//    }

//    public void preMusic(View view) {
//        if (PlayerUtil.CURRENTPOSITION > 0) {
//            PlayerUtil.CURRENTPOSITION = PlayerUtil.CURRENTPOSITION - 1;
//            PlayerUtil.CurrentMusicBean = PlayerUtil.CURRENT_PLAY_LIST.get(PlayerUtil.CURRENTPOSITION);
//            PlayerUtil.startService(view.getContext(), PlayerUtil.CurrentMusicBean, PlayerUtil.PLAY);
//            updateMusicInfo();
//        }
//    }
//
//    private void updateMusicInfo() {
//        String songname = PlayerUtil.CurrentMusicBean.getSongname();
//        Log.d("google-my:", "updateMusicInfo: " + songname);
//        setSongname(songname);
//        String singername = PlayerUtil.CurrentMusicBean.getSingername();
//        Log.d("google-my:", "updateMusicInfo: " + singername);
//        setSingername(singername);
//        String albumpic_small = PlayerUtil.CurrentMusicBean.getAlbumpic_small();
//        Log.d("google-my:", "updateMusicInfo: " + albumpic_small);
//        setAlbumpic_small(albumpic_small);
//    }
//
//    public void nextMusic(View view) {
//        if (PlayerUtil.CURRENTPOSITION < PlayerUtil.CURRENT_PLAY_LIST.size() - 1) {
//            PlayerUtil.CURRENTPOSITION = PlayerUtil.CURRENTPOSITION + 1;
//            PlayerUtil.CurrentMusicBean = PlayerUtil.CURRENT_PLAY_LIST.get(PlayerUtil.CURRENTPOSITION);
//            PlayerUtil.startService(view.getContext(), PlayerUtil.CurrentMusicBean, PlayerUtil.PLAY);
//            updateMusicInfo();
//        }
//    }


    @BindingAdapter("bind:albumpic_small")
    public static void getNetImage(ImageView iv, String url) {  // 这个iv 就是在xml中使用了这个属性的ImageView对象

        if (url == null || "".equals(url) || !url.startsWith("http")) {
            return;
        }

//        Picasso.with(iv.getContext()).load(url).resize(iv.getWidth(),iv.getHeight()).centerCrop().into(iv);
        // 换用Vollery加载图片
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv, R.drawable.default1, R.drawable.default1);
        loader.get(url, listener); // 难道vollery会自己调用你提供的实现了

    }

    public MusicBean() {

    }

    public MusicBean(String songname, int seconds, String albummid, int songid, int singerid, String albumpic_big, String albumpic_small, String downUrl, String url, String singername, int albumid) {
        this.songname = songname;
        this.seconds = seconds;
        this.albummid = albummid;
        this.songid = songid;
        this.singerid = singerid;
        this.albumpic_big = albumpic_big;
        this.albumpic_small = albumpic_small;
        this.downUrl = downUrl;
        this.url = url;
        this.singername = singername;
        this.albumid = albumid;
    }

    @Generated(hash = 1145604706)
    public MusicBean(Long id, String songname, int seconds, String albummid, int songid, int singerid, String albumpic_big, String albumpic_small, String downUrl, String url, String singername,
            int albumid, Integer type, int playcount, String keyword, String albumname) {
        this.id = id;
        this.songname = songname;
        this.seconds = seconds;
        this.albummid = albummid;
        this.songid = songid;
        this.singerid = singerid;
        this.albumpic_big = albumpic_big;
        this.albumpic_small = albumpic_small;
        this.downUrl = downUrl;
        this.url = url;
        this.singername = singername;
        this.albumid = albumid;
        this.type = type;
        this.playcount = playcount;
        this.keyword = keyword;
        this.albumname = albumname;
    }

    //class MusicBean extends BaseObservable 这个是注解的重点啊！
//    @Bindable
    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
        //notifyPropertyChanged(BR.songname);
        // 每当get方法上的@Bindable上的值改变时，就会用这个方法去更新xml中的属性，先get改变，再调用set去改变xml文件中的属性。
//        notifyPropertyChanged(BR.songname);
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public String getAlbummid() {
        return albummid;
    }

    public void setAlbummid(String albummid) {
        this.albummid = albummid;
    }

    public int getSongid() {
        return songid;
    }

    public void setSongid(int songid) {
        this.songid = songid;
    }

    public int getSingerid() {
        return singerid;
    }

    public void setSingerid(int singerid) {
        this.singerid = singerid;
    }

    public String getAlbumpic_big() {
        return albumpic_big;
    }

    public void setAlbumpic_big(String albumpic_big) {
        this.albumpic_big = albumpic_big;
    }

    //    @Bindable
    public String getAlbumpic_small() {
        return albumpic_small;
    }

    public void setAlbumpic_small(String albumpic_small) {
        this.albumpic_small = albumpic_small;
//        notifyPropertyChanged(BR.albumpic_small);
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //    @Bindable
    public String getSingername() {
        return singername;
    }

    public void setSingername(String singername) {
        this.singername = singername;
//        notifyPropertyChanged(BR.singername);
    }

    public int getAlbumid() {
        return albumid;
    }

    public void setAlbumid(int albumid) {
        this.albumid = albumid;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
