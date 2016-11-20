package qianfeng.qqmusicbymyself.play_activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.lenve.customshapeimageview.CustomShapeImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;
import qianfeng.mylibrary.LrcView;
import qianfeng.mylibrary.bean.LrcBean;
import qianfeng.qqmusicbymyself.R;
import qianfeng.qqmusicbymyself.play_activity.presenter.PlayActivityPresenter;
import qianfeng.qqmusicbymyself.service.DownService;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;
import qianfeng.qqmusicbymyself.showmusic.presenter.MainActivityPersenter;
import qianfeng.qqmusicbymyself.showmusic.view.IMainActivityView;
import qianfeng.qqmusicbymyself.util.Consts;
import qianfeng.qqmusicbymyself.util.MusicUtil;
import qianfeng.qqmusicbymyself.util.PlayerUtil;

public class PlayActivity extends AppCompatActivity implements IMainActivityView, IPlayActivityView {

    private CustomShapeImageView music_thumbnail;
    private TextView music_name;
    private TextView singerName;
    private TextView music_name2;
    private ImageView playOrPause;
    private TextView currentTimeTv;
    private TextView totalTimeTv;
    private SeekBar seekBar;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
    private MainActivityPersenter mainActivityPersenter = new MainActivityPersenter(this);

    private PlayActivityPresenter playActivityPresenter = new PlayActivityPresenter(this);
    private boolean isFirst = true;
    private int PLAYERSTATE = -1;
    private static long flagTime = -1;
    private boolean fromPreOrNext = false;
    private boolean canIn = true;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {

                    //|| PlayerUtil.player.isPlaying()
                    if (PlayerUtil.PLAYERCURRENTSTATE == PlayerUtil.STATE_PLAY || PlayerUtil.player.isPlaying()) { // 只有在STATE_PLAY状态的时候，PlayerUtil.player这个对象才不为空,才可以调用里面的getCurrentPosition方法
                        // 他妈的痹这么幼稚，只要在这个PlayActivity一启动的时候，就在onCreate方法中直接发送handler消息更新UI不就好了吗？高手？
                        seekBar.setProgress(PlayerUtil.player.getCurrentPosition());
                        seekBar.setMax(PlayerUtil.CURRENT_MUSICBEAN.getSeconds() * 1000);
                        flagTime = PlayerUtil.player.getCurrentPosition();
                        currentTimeTv.setText(dateFormat.format(new Date(PlayerUtil.player.getCurrentPosition())));
                        mHandler.sendEmptyMessageDelayed(0, 200);
                    } else {
                        if (flagTime != -1 && !fromPreOrNext) {
                            seekBar.setProgress(PlayerUtil.player.getCurrentPosition());
                            seekBar.setMax(PlayerUtil.CURRENT_MUSICBEAN.getSeconds() * 1000);
                            currentTimeTv.setText(dateFormat.format(new Date(PlayerUtil.player.getCurrentPosition())));//flagTime?
                        } else if (fromPreOrNext) {
                            seekBar.setProgress(PlayerUtil.player.getCurrentPosition());
                            seekBar.setMax(PlayerUtil.CURRENT_MUSICBEAN.getSeconds() * 1000);
                            currentTimeTv.setText(dateFormat.format(new Date(PlayerUtil.player.getCurrentPosition())));
                            mHandler.sendEmptyMessage(0);// 提醒更新UI
                        }
                    }
                }
                break;

                case 1: {

                    if (PlayerUtil.player != null && canIn) {
                        if (PlayerUtil.CURRENT_MUSICBEAN.getSeconds() == 0) {
                            int seconds = PlayerUtil.player.getDuration() / 1000;
                            Toast.makeText(PlayActivity.this, seconds + ":看看记录的seconds是啥:", Toast.LENGTH_SHORT).show();
                            PlayerUtil.CURRENT_MUSICBEAN.setSeconds(seconds);
                        }
                        sendEmptyMessageDelayed(1, 200);
                        if (PlayerUtil.CURRENT_MUSICBEAN.getSeconds() != 0) {
                            canIn = false;
                            totalTimeTv.setText(dateFormat.format(new Date(PlayerUtil.CURRENT_MUSICBEAN.getSeconds() * 1000)));
                        }
                    }

                }

                break;
            }
        }
    };
    private LrcView lrcView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        initView();


        playActivityPresenter.start();

    }

    private void updataView() {


    }

    private void updataIcon() {
        if (PlayerUtil.PLAYERCURRENTSTATE == PlayerUtil.STATE_PLAY) {
            playOrPause.setImageResource(R.drawable.search_stop_btn);
        }
//        else if (PlayerUtil.PLAYERCURRENTSTATE == PlayerUtil.STATE_PAUSE) {
//            playOrPause.setImageResource(R.drawable.search_stop_btn);
//        }
        else {
            playOrPause.setImageResource(R.drawable.ring_btnplay);
        }
    }

    private void initView() {

        music_thumbnail = ((CustomShapeImageView) findViewById(R.id.music_thumbnail));

        music_name = ((TextView) findViewById(R.id.music_name));
        singerName = ((TextView) findViewById(R.id.singerName));
        music_name2 = ((TextView) findViewById(R.id.music_name2));

        playOrPause = ((ImageView) findViewById(R.id.play_or_pause));
        currentTimeTv = ((TextView) findViewById(R.id.current_time));
        currentTimeTv.setText("00:00");
        totalTimeTv = (TextView) findViewById(R.id.total_time);
        seekBar = ((SeekBar) findViewById(R.id.seek_bar));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
//                    seekBar.setProgress(progress);
                    PlayerUtil.player.seekTo(progress);
//                    currentTimeTv.setText(dateFormat.format(new Date(progress)));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                PLAYERSTATE = PlayerUtil.PLAYERCURRENTSTATE;
                // 开始拖动的时候，暂停歌曲播放
                PlayerUtil.player.pause();
                PlayerUtil.PLAYERCURRENTSTATE = PlayerUtil.STATE_PAUSE;

                playActivityPresenter.start();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if (PlayerUtil.player != null && !PlayerUtil.player.isPlaying()) {
                    if (PLAYERSTATE == PlayerUtil.STATE_PLAY) {
                        PlayerUtil.player.start();
                        PlayerUtil.PLAYERCURRENTSTATE = PlayerUtil.STATE_PLAY;
                    } else if (PLAYERSTATE == PlayerUtil.STATE_PAUSE) {
                        PlayerUtil.player.pause();
                        PlayerUtil.PLAYERCURRENTSTATE = PlayerUtil.STATE_PAUSE;
                    } else {
                        Toast.makeText(PlayActivity.this, "拖动的这是什么鬼", Toast.LENGTH_SHORT).show();
                    }
                }

//                PlayerUtil.playOrPause();

                // 因为我之前在Handler那里设置了 if (PlayerUtil.PLAYERCURRENTSTATE == PlayerUtil.STATE_PLAY)，所以我在拖动的时候，音乐盒播放是暂停的，UI是更新不了的，即使后来我再播放，也无法唤醒这个Handler消息，我只能重新start一次
                sendBroadcast(new Intent(Consts.BROADRECEIVER3));
                playActivityPresenter.start();
            }
        });

        LinearLayout right_menu = ((LinearLayout) findViewById(R.id.right_menu));
        right_menu.getLayoutParams().width = getResources().getDisplayMetrics().widthPixels;
        right_menu.requestLayout();

        LinearLayout left_menu = ((LinearLayout) findViewById(R.id.left_menu));
        left_menu.getLayoutParams().width = getResources().getDisplayMetrics().widthPixels;
        left_menu.requestLayout();

        lrcView = ((LrcView) findViewById(R.id.lrcView));


    }

    @Override
    public void updatePlayerControl() { // 这个才是我们这个Activity的Presenter!!

        if (PlayerUtil.PLAYERCURRENTSTATE == PlayerUtil.STATE_PLAY) {
            playOrPause.setImageResource(R.drawable.search_stop_btn);
        } else {
            playOrPause.setImageResource(R.drawable.ring_btnplay);
        }


        if (PlayerUtil.CURRENT_MUSICBEAN != null) {
            if (PlayerUtil.FLAGLIST == 1) { // 表明歌曲是从网络上听的

                Picasso.with(this).load(PlayerUtil.CURRENT_MUSICBEAN.getAlbumpic_small()).placeholder(R.drawable.default1).error(R.mipmap.ic_launcher).into(music_thumbnail);

            } else if (PlayerUtil.FLAGLIST == 0) {
                if (MusicUtil.getBitmap(PlayerUtil.CURRENT_MUSICBEAN.getUrl()) != null && PlayerUtil.CURRENT_MUSICBEAN.getAlbumpic_small() == null) {
                    music_thumbnail.setImageBitmap(MusicUtil.getBitmap(PlayerUtil.CURRENT_MUSICBEAN.getUrl()));
                } else if (PlayerUtil.CURRENT_MUSICBEAN.getAlbumpic_small() != null) { // 网络图片
                    String albumpic_small = PlayerUtil.CURRENT_MUSICBEAN.getAlbumpic_small();
                    String picPath = albumpic_small.substring(albumpic_small.lastIndexOf("/") + 1);
                    Bitmap bitmap1 = BitmapFactory.decodeFile(new File(this.getExternalCacheDir(), picPath).getAbsolutePath());
                    if (bitmap1 != null) {
                        music_thumbnail.setImageBitmap(bitmap1);
                        Log.d("google-my:", "updataUI: 网络图片缓存成功");
                    } else {
                        Log.d("google-my:", "updataUI: 网络图片在本地找不到");
                        music_thumbnail.setImageResource(R.drawable.default1);
                    }

                } else {
                    music_thumbnail.setImageResource(R.drawable.default1);
                }

            } else {
                Log.d("google-my:", "updataView: if (PlayerUtil.CURRENT_MUSICBEAN != null ==== true)");
            }

            if (PlayerUtil.CURRENT_MUSICBEAN.getSongname() != null && !"".equals(PlayerUtil.CURRENT_MUSICBEAN.getSongname())) {
                music_name.setText(PlayerUtil.CURRENT_MUSICBEAN.getSongname());
            } else {
                music_name.setText("");
            }

            if (PlayerUtil.CURRENT_MUSICBEAN.getSongname() != null && !"".equals(PlayerUtil.CURRENT_MUSICBEAN.getSongname())) {
                music_name2.setText(PlayerUtil.CURRENT_MUSICBEAN.getSongname());
            } else {
                music_name2.setText("");
            }

            if (PlayerUtil.CURRENT_MUSICBEAN.getSingername() != null && !"".equals(PlayerUtil.CURRENT_MUSICBEAN.getSingername())) {
                singerName.setText(PlayerUtil.CURRENT_MUSICBEAN.getSingername());
            } else {
                singerName.setText("");
            }


//
            if (PlayerUtil.CURRENT_MUSICBEAN.getSeconds() == 0 || PlayerUtil.CURRENT_MUSICBEAN.getSeconds() < 0) {
                PlayerUtil.CURRENT_MUSICBEAN.setSeconds(PlayerUtil.player.getDuration() / 1000);
                totalTimeTv.setText(dateFormat.format(PlayerUtil.CURRENT_MUSICBEAN.getSeconds() * 1000));
                mHandler.sendEmptyMessage(1);
            } else {
                totalTimeTv.setText(dateFormat.format(new Date(PlayerUtil.CURRENT_MUSICBEAN.getSeconds() * 1000)));
            }


            seekBar.setMax(PlayerUtil.CURRENT_MUSICBEAN.getSeconds() * 1000);


        } else {
            Log.d("google-my:", "updataView: PlayerUtil.CURRENT_MUSICBEAN 还需从sp中加载[]");

            MusicBean musicBean = mainActivityPersenter.loadSP(this);
            if (MusicUtil.getBitmap(musicBean.getUrl()) != null && musicBean.getAlbumpic_small() == null) {
                music_thumbnail.setImageBitmap(MusicUtil.getBitmap(mainActivityPersenter.loadSP(this).getUrl()));
            } else if (musicBean.getAlbumpic_small() != null) { // 代表是网络图片
                String albumpic_small = musicBean.getAlbumpic_small();
                String picPath = albumpic_small.substring(albumpic_small.lastIndexOf("/") + 1);
                Bitmap bitmap1 = BitmapFactory.decodeFile(new File(this.getExternalCacheDir(), picPath).getAbsolutePath());
                if (bitmap1 != null) {
                    music_thumbnail.setImageBitmap(bitmap1);
                    Log.d("google-my:", "updataUI: 网络图片缓存成功");
                } else {
                    Log.d("google-my:", "updataUI: 网络图片在本地找不到");
                    music_thumbnail.setImageResource(R.drawable.default1);
                }
            } else {
                music_thumbnail.setImageResource(R.drawable.default1);
            }

//           MusicUtil.getBitmap(PlayerUtil.CURRENT_MUSICBEAN.getUrl())

//           Log.d("google-my:", "updataView: MainActivityPersenter.loadSP(this).getUrl():" + MainActivityPersenter.loadSP(this).getUrl());

            { // 设置动画
                RotateAnimation rotateAnimation = new RotateAnimation(0, 359);
                rotateAnimation.setDuration(20 * 1000);
                rotateAnimation.setRepeatMode(Animation.RESTART);
                rotateAnimation.setRepeatCount(Animation.INFINITE);
                music_thumbnail.startAnimation(rotateAnimation);
            }

            updataIcon();

            music_name.setText(mainActivityPersenter.loadSP(this).getSongname());

            singerName.setText(mainActivityPersenter.loadSP(this).getSingername());
            music_name2.setText(mainActivityPersenter.loadSP(this).getSongname());
            seekBar.setMax(mainActivityPersenter.loadSP(this).getSeconds() * 1000);


        }

        // 下载或加载本地歌词！
        playActivityPresenter.loadLrc(PlayerUtil.CURRENT_MUSICBEAN.getSongid());

        mHandler.sendEmptyMessage(0);


    }

    @Override
    public void initLru(List<LrcBean> list) {

        // 要在这里初始化歌词
        lrcView.setList(list);
        lrcView.setPlayer(PlayerUtil.player); // 为了封装成一个更好的工具类，MediaPlayer和list都是从外面传进来的

        // 调用这个方法重绘，因为xml文件刚加载时，list是null的，然后就return掉了onDraw方法，就不能执行延迟100毫秒重绘的
        // 所以需要在这里数据下载完成之后，调用刷新视图，重绘一次的方法
        lrcView.init();

    }

    @Override
    public void updataUI(MusicBean musicBean) {// MainActivityPersenter!!!

    }

    @Override
    public void updataUIBottom() {

    }


    public void playOrPause(View view) {

//        PlayerUtil.startService(this, PlayerUtil.CURRENT_MUSICBEAN.getUrl(), PlayerUtil.STATE_PAUSE_OR_PLAY);
//        sendBroadcast(new Intent(Consts.BROADRECEIVER3));
//        mHandler.sendEmptyMessage(0);

// 这里也要进行Handler消息的发送，而无关性能问题！


        Log.d("google-my:", "playOrPause: STATE:[]  " + PlayerUtil.PLAYERCURRENTSTATE);
        if (PlayerUtil.PLAYERCURRENTSTATE == PlayerUtil.STATE_STOP) {
            playOrPause.setImageResource(R.drawable.search_stop_btn);
            Log.d("google-my:", "playOrPause: STATE:[]stop:  " + PlayerUtil.PLAYERCURRENTSTATE);
            PlayerUtil.startService(this, PlayerUtil.CURRENT_MUSICBEAN, PlayerUtil.STATE_PLAY);

        } else if (PlayerUtil.PLAYERCURRENTSTATE == PlayerUtil.STATE_PAUSE) {
            playOrPause.setImageResource(R.drawable.search_stop_btn);
            PlayerUtil.startService(this, PlayerUtil.CURRENT_MUSICBEAN, PlayerUtil.STATE_PAUSE_OR_PLAY);
            Log.d("google-my:", "playOrPause: STATE:[] pause: " + PlayerUtil.PLAYERCURRENTSTATE);
//            updataIcon();

//            updataUI(PlayerUtil.CURRENT_MUSICBEAN);// 这样更新的速度太慢了
        } else if (PlayerUtil.PLAYERCURRENTSTATE == PlayerUtil.STATE_PLAY) {
            playOrPause.setImageResource(R.drawable.ring_btnplay);
            PlayerUtil.startService(this, PlayerUtil.CURRENT_MUSICBEAN, PlayerUtil.STATE_PAUSE_OR_PLAY);
            Log.d("google-my:", "playOrPause: STATE:[] else: " + PlayerUtil.PLAYERCURRENTSTATE);
//            updataIcon();
        }
        sendBroadcast(new Intent(Consts.BROADRECEIVER3));

        mHandler.sendEmptyMessage(0);

//

    }


    public void preMusic(View view) {
        if (PlayerUtil.FLAGLIST == 0) // 如果是本地歌曲
        {
            if (PlayerUtil.CURRENTPOSITION - 1 > 1) {
                PlayerUtil.CURRENTPOSITION = PlayerUtil.CURRENTPOSITION - 1;
                PlayerUtil.CURRENT_MUSICBEAN = PlayerUtil.CURRENTLIST.get(PlayerUtil.CURRENTPOSITION);
                playActivityPresenter.start();// 调用updatePlayerControl()更新本Activity中的UI而已
                PlayerUtil.startService(this, PlayerUtil.CURRENT_MUSICBEAN, PlayerUtil.STATE_PLAY);
                fromPreOrNext = true;
                mHandler.sendEmptyMessage(0);

                sendBroadcast(new Intent(Consts.BROADRECEIVER1));// 更新第一个Activity的UI
            }
        } else if (PlayerUtil.FLAGLIST == 1) // 如果是网络歌曲
        {
            if (PlayerUtil.CURRENTPOSITION - 1 > -1) {
                PlayerUtil.CURRENTPOSITION = PlayerUtil.CURRENTPOSITION - 1;
                PlayerUtil.CURRENT_MUSICBEAN = PlayerUtil.CURRENTLIST.get(PlayerUtil.CURRENTPOSITION);
                playActivityPresenter.start();// 调用updatePlayerControl()更新本Activity中的UI而已
                PlayerUtil.startService(this, PlayerUtil.CURRENT_MUSICBEAN, PlayerUtil.STATE_PLAY);
                fromPreOrNext = true;
                mHandler.sendEmptyMessage(0);

                sendBroadcast(new Intent(Consts.BROADRECEIVER1));// 更新第一个Activity的UI
            }
        }
    }

    public void nextMusic(View view) {
        if (PlayerUtil.FLAGLIST == 0) // 如果是本地歌曲
        {
            if (PlayerUtil.CURRENTPOSITION + 1 < PlayerUtil.CURRENTLIST.size()) {
                PlayerUtil.CURRENTPOSITION = PlayerUtil.CURRENTPOSITION + 1;
                PlayerUtil.CURRENT_MUSICBEAN = PlayerUtil.CURRENTLIST.get(PlayerUtil.CURRENTPOSITION);
                playActivityPresenter.start();
                fromPreOrNext = true;
                mHandler.sendEmptyMessage(0);
                PlayerUtil.startService(this, PlayerUtil.CURRENT_MUSICBEAN, PlayerUtil.STATE_PLAY);
                sendBroadcast(new Intent(Consts.BROADRECEIVER1));
            }
        } else if (PlayerUtil.FLAGLIST == 1) // 如果是网络歌曲
        {
            if (PlayerUtil.CURRENTPOSITION + 1 < PlayerUtil.CURRENTLIST.size()) {
                PlayerUtil.CURRENTPOSITION = PlayerUtil.CURRENTPOSITION + 1;
                PlayerUtil.CURRENT_MUSICBEAN = PlayerUtil.CURRENTLIST.get(PlayerUtil.CURRENTPOSITION);
                playActivityPresenter.start();
                fromPreOrNext = true;
                mHandler.sendEmptyMessage(0);
                PlayerUtil.startService(this, PlayerUtil.CURRENT_MUSICBEAN, PlayerUtil.STATE_PLAY);
                sendBroadcast(new Intent(Consts.BROADRECEIVER1));
            }
        }
    }

    public void downloadMusic(View view) {
        // 并下载歌曲,下载歌词。
        Intent downService = new Intent(this, DownService.class);
        downService.putExtra("songid", PlayerUtil.CURRENT_MUSICBEAN.getSongid());
        downService.putExtra("downUrl", PlayerUtil.CURRENT_MUSICBEAN.getDownUrl());
        startService(downService);
    }

    public void share(View view) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(PlayerUtil.CURRENT_MUSICBEAN.getSongname());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(PlayerUtil.CURRENT_MUSICBEAN.getUrl());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(PlayerUtil.CURRENT_MUSICBEAN.getSongname());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(PlayerUtil.CURRENT_MUSICBEAN.getAlbumpic_small());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(PlayerUtil.CURRENT_MUSICBEAN.getUrl());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(PlayerUtil.CURRENT_MUSICBEAN.getSongname());
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(PlayerUtil.CURRENT_MUSICBEAN.getSongname());
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(PlayerUtil.CURRENT_MUSICBEAN.getUrl());

// 启动分享GUI
        oks.show(this);
    }
}
