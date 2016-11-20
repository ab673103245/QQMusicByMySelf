package qianfeng.qqmusicbymyself.showmusic;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;

import org.lenve.customshapeimageview.CustomShapeImageView;

import java.io.File;
import java.util.List;

import qianfeng.qqmusicbymyself.App;
import qianfeng.qqmusicbymyself.BaseActivity;
import qianfeng.qqmusicbymyself.R;
import qianfeng.qqmusicbymyself.localmusic.view.LocalMusicFragment;
import qianfeng.qqmusicbymyself.play_activity.PlayActivity;
import qianfeng.qqmusicbymyself.receiver.MyReceiver;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBeanDao;
import qianfeng.qqmusicbymyself.showmusic.presenter.MainActivityPersenter;
import qianfeng.qqmusicbymyself.showmusic.view.IMainActivityView;
import qianfeng.qqmusicbymyself.showmusic.view.adapter.listViewAdapter.MyAdapter;
import qianfeng.qqmusicbymyself.showmusic.view.fragment.Main_fragment;
import qianfeng.qqmusicbymyself.util.BitmapCache;
import qianfeng.qqmusicbymyself.util.Consts;
import qianfeng.qqmusicbymyself.util.MusicUtil;
import qianfeng.qqmusicbymyself.util.PlayerUtil;

public class MainActivity extends BaseActivity implements IMainActivityView {

    public static int ACTIVITY_STATE = 0;
    private FragmentTransaction transaction;
    public static Fragment CURRENTFRAGMENT;
    private Main_fragment main_fragment;
    private Main_fragment fragment;
    private CustomShapeImageView music_thumbnail;
    private TextView music_name;
    private ImageView play_list;
    private ImageView pause_or_play;
    private RelativeLayout bottom;


    private MainActivityPersenter mainActivityPersenter = new MainActivityPersenter(this);
    private MyReceiver receiver;
    private TextView tv_singername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        mainActivityPersenter.start(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Consts.BROADRECEIVER1);
        filter.addAction(Consts.BROADRECEIVER2);
        filter.addAction(Consts.BROADRECEIVER3);
        receiver = new MyReceiver();
        registerReceiver(receiver, filter);
    }

    private void initView() {
        MusicBean.loader = new ImageLoader((((App)getApplication()).getRequestQueue()), new BitmapCache(this));

        music_thumbnail = ((CustomShapeImageView) findViewById(R.id.music_thumbnail));
        music_name = ((TextView) findViewById(R.id.music_name));
        play_list = ((ImageView) findViewById(R.id.play_list));
        pause_or_play = ((ImageView) findViewById(R.id.pause_or_play));
        bottom = ((RelativeLayout) findViewById(R.id.bottom)); // 左右横滑可切换上下首，在一个RelativeLayout里面设置setOnTouchListener方法
        tv_singername = ((TextView) findViewById(R.id.tv_singername));
    }

    @Override
    protected void onResume() {
        super.onResume();


        fragment = new Main_fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.ll, fragment).commit();

//        transaction = getSupportFragmentManager().beginTransaction();
//        main_fragment = new Main_fragment();
//        transaction.add(R.id.ll, main_fragment).commit();
//        CURRENTFRAGMENT = main_fragment;

    }

    @Override
    public void updataUI(MusicBean musicBean) {

        PlayerUtil.CURRENT_MUSICBEAN = musicBean;

        if (musicBean != null) {
//            PlayerUtil.CURRENT_MUSICBEAN = musicBean;

            music_name.setText(PlayerUtil.CURRENT_MUSICBEAN.getSongname());
            // 这只是网络图片，那么本地图片呢？
            if (musicBean.getUrl() != null && PlayerUtil.FLAGLIST == 0 && !"".equals(musicBean.getUrl())) {
//                music_thumbnail.setImageBitmap(MusicUtil.getBitmap(PlayerUtil.CURRENT_MUSICBEAN.getUrl()));//getAlbumpic_small() == getUrl == 本地歌曲的路径及路径所对应的歌曲图片
//                remoteViews.setImageViewBitmap(R.id.music_thumbnail, MusicUtil.getBitmap(PlayerUtil.CURRENT_MUSICBEAN.getUrl()));
                Bitmap bitmap = MusicUtil.getBitmap(musicBean.getUrl());
                Log.d("google-my:", "updataUI: 去数据库里面拿歌曲图片");
                if (bitmap != null && musicBean.getAlbumpic_small() == null) // 本地歌曲的这个musicBean.getAlbumpic一定为空
                {
                    Log.d("google-my:", "updataUI: 是一直加载这里吗");
                    music_thumbnail.setImageBitmap(bitmap);
                } else if (musicBean.getAlbumpic_small() != null && !"".equals(musicBean.getAlbumpic_small())) {
                    // 能够进入到这里来，应该已经下载过了,图片已经缓存过了
                    Log.d("google-my:", "updataUI: 网络图片本地缓存，能成功吗？");
                    String albumpic_small = musicBean.getAlbumpic_small();
                    String picPath = albumpic_small.substring(albumpic_small.lastIndexOf("/") + 1);
                    Bitmap bitmap1 = BitmapFactory.decodeFile(new File(this.getExternalCacheDir(), picPath).getAbsolutePath());
                    if(bitmap1 != null)
                    {
                        music_thumbnail.setImageBitmap(bitmap1);
                        Log.d("google-my:", "updataUI: 网络图片缓存成功");
                    }else
                    {
                        Log.d("google-my:", "updataUI: 网络图片在本地找不到");
                        music_thumbnail.setImageResource(R.drawable.default1);
                    }
                }else{
                    Log.d("google-my:", "updataUI: bitmap==null");
                    music_thumbnail.setImageResource(R.drawable.default1);
                }
            } else if (musicBean.getAlbumpic_small() != null && PlayerUtil.FLAGLIST == 1) // 网络图片
            {
//                Picasso.with(this).load(musicBean.getAlbumpic_small()).into(music_thumbnail);
                // 在这里就应该使用volley把网络图片缓存下来
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if(networkInfo != null) { // 如果有网络
                    ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(music_thumbnail, R.drawable.default1, R.mipmap.ic_launcher);
                    // 难道volley会自己调用你提供的ImageLoader.ImageCache的实现类的BitmapCache中的getBitmap和putBitmap方法，进行图片的三级缓存？
                    MusicBean.loader.get(musicBean.getAlbumpic_small(), imageListener);// 难道volley会自己调用你提供的ImageLoader.ImageCache的实现类的BitmapCache中的getBitmap和putBitmap方法，进行图片的三级缓存？
//  FileOutputStream fos = new FileOutputStream(new File(context.getExternalCacheDir(), url)); 实际上在这里就指明了要缓冲的位置
                }else   // 当Info为null，就是没有网络的时候
                {
                    Toast.makeText(MainActivity.this, "请检查手机联网状态", Toast.LENGTH_SHORT).show();
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
                }
//                Intent service = new Intent(this, PlayerService.class);
//                service.putExtra("type",Consts.UPDATANOTIFICATIONREMOTEVIEWS);
//                startService(service);

            }

            tv_singername.setText(PlayerUtil.CURRENT_MUSICBEAN.getSingername());
        }

        updataUIBottom();

    }

    @Override
    public void updataUIBottom() {

        if (PlayerUtil.PLAYERCURRENTSTATE == PlayerUtil.STATE_PLAY) {
            pause_or_play.setImageResource(R.drawable.search_stop_btn);
        } else {
            pause_or_play.setImageResource(R.drawable.ring_btnplay); // 暂停的时候
        }
    }

    public void pauseOrPlay(View view) {
//        PlayerUtil.playOrPause();

        if (PlayerUtil.PLAYERCURRENTSTATE == PlayerUtil.STATE_STOP) {
            pause_or_play.setImageResource(R.drawable.search_stop_btn);
            PlayerUtil.startService(this, PlayerUtil.CURRENT_MUSICBEAN, PlayerUtil.STATE_PLAY);
        } else if (PlayerUtil.PLAYERCURRENTSTATE == PlayerUtil.STATE_PAUSE) {
            pause_or_play.setImageResource(R.drawable.search_stop_btn);
            PlayerUtil.startService(this, PlayerUtil.CURRENT_MUSICBEAN, PlayerUtil.STATE_PAUSE_OR_PLAY);
//            updataUI(PlayerUtil.CURRENT_MUSICBEAN);// 这样更新的速度太慢了
        } else {
            pause_or_play.setImageResource(R.drawable.ring_btnplay);
            PlayerUtil.startService(this, PlayerUtil.CURRENT_MUSICBEAN, PlayerUtil.STATE_PAUSE_OR_PLAY);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        mainActivityPersenter.saveSP(this);
    }


    @Override
    public void onBackPressed() {
        if (ACTIVITY_STATE == 0) {
            super.onBackPressed();
        } else if (ACTIVITY_STATE == 1) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.ll,new Main_fragment()).commit();
            ACTIVITY_STATE = 0;
            Log.d("google-my:", "onBackPressed: addFragmentBackPress::");
            getSupportFragmentManager().beginTransaction().replace(R.id.ll, new Main_fragment()).commit();

        } else if (ACTIVITY_STATE == 2) {
            ACTIVITY_STATE = 1;
            getSupportFragmentManager().beginTransaction().replace(R.id.ll, new LocalMusicFragment()).commit();
        }

    }

    public void goPlayerActivity(View view) { // 这是底部导航栏的点击事件,点击之后，进入另一个Activity，里面有seekBar等控件
        startActivity(new Intent(this, PlayActivity.class));
    }

    public void play_list(View view) {
        // 播放列表

        MusicBeanDao musicBeanDao = ((App) getApplication()).getMusicBeanDao();

        final List<MusicBean> musicBeanList = musicBeanDao.queryBuilder().orderDesc(MusicBeanDao.Properties.Id).list();

//        Toast.makeText(MainActivity.this, "播放列表,程序员正在努力进行该板块，该功能暂未对外开放", Toast.LENGTH_SHORT).show();

        if (musicBeanList != null && musicBeanList.size() > 0) {
            View view1 = LayoutInflater.from(this).inflate(R.layout.play_list_popopwindow, null);
            final PopupWindow popupWindow = new PopupWindow(view1, getResources().getDisplayMetrics().widthPixels, (int) (getResources().getDisplayMetrics().heightPixels * 0.7f));

            popupWindow.setFocusable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOutsideTouchable(true);

            ListView listView = (ListView) view1.findViewById(R.id.listview);
            TextView tv_close = (TextView) view1.findViewById(R.id.tv_close);
            tv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });

            final MyAdapter adapter = new MyAdapter(musicBeanList, this);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PlayerUtil.CURRENT_MUSICBEAN = musicBeanList.get(position);
                    PlayerUtil.CURRENTPOSITION = position;
                    PlayerUtil.CURRENTLIST = musicBeanList;
                    PlayerUtil.startService(MainActivity.this, PlayerUtil.CURRENT_MUSICBEAN, PlayerUtil.STATE_PLAY);
                    sendBroadcast(new Intent(Consts.BROADRECEIVER1)); // 发送广播更新UI
                    {
                        mainActivityPersenter.saveMusicBeanDao(PlayerUtil.CURRENT_MUSICBEAN); // 保存至music.db数据库中
                    }
                    popupWindow.dismiss();
//                    adapter.notifyDataSetChanged();// 通知适配器，数据源发生了改变。再请求一次数据？
                }
            });


            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        } else {
            Toast.makeText(MainActivity.this, "music.db数据库里面没有数据啊", Toast.LENGTH_SHORT).show();
        }


    }

}
