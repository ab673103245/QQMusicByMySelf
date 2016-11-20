package qianfeng.mylibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import qianfeng.mylibrary.bean.LrcBean;

/**
 * Created by Administrator on 2016/11/5 0005.
 */
public class LrcCustomView extends View {

    private List<LrcBean> list;

    private MediaPlayer mediaPlayer; // 用来与歌词进行同步，用MediaPlayer里面记录的position与歌词的时间进行对比

    private Paint gPaint;
    private Paint hPaint;

    private int currentPosition = -1;


    // 如果构造方法没有用的话，那就用set方法

    public void setList(List<LrcBean> list) {
        this.list = list;
    }


    public void setPlayer(MediaPlayer player) {
        this.mediaPlayer = player;
    }

    public LrcCustomView(Context context) {
        this(context, null);
    }

    public LrcCustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LrcCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        gPaint = new Paint();
        gPaint.setAntiAlias(true);
        gPaint.setColor(Color.WHITE);
        gPaint.setTextSize(36);
        gPaint.setTextAlign(Paint.Align.CENTER);

        hPaint = new Paint();
        hPaint.setAntiAlias(true);
        hPaint.setColor(getResources().getColor(R.color.colorPrimary));
        gPaint.setTextAlign(Paint.Align.CENTER);
        gPaint.setTextSize(36);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        int height = getHeight();
        int width = getWidth();
        Bitmap blankBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas lrcCanvas = new Canvas(blankBitmap);

        getCurrentPosition();// MediaPlayer的Java层和C层状态不同步的问题，就在这里

        postInvalidateDelayed(100);


    }

    private void getCurrentPosition() {

        // 有一种情况是：当MediaPlayer的java层和C层出现状态不一致时，这个getCurrentPosition有可能在MediaPlayer没准备好之前，就开始调用这个getCurrentPostion方法，就会出现异常。
        // 解决的方式是：try-catch，并且在catch到异常的时候，进行处理，就是延迟100毫秒重新调用一次onDraw方法，使其绘制视图

        try {
            int currentTime = mediaPlayer.getCurrentPosition();// 毫秒数

            for (int i = 0; i < list.size(); i++) {

                if (currentTime < list.get(0).getStart()) {
                    // 如果MediaPlayer还没有到达我第一句歌词出现的时间，就显示第一句歌词
                    currentPosition = 0;
                    return;
                }

                // 一头一尾要处理好
                if (currentTime > list.get(list.size() - 1).getStart()) {
                    currentPosition = list.size() - 1;
                    return;
                }

                if(currentTime >= list.get(i).getStart() && currentTime < list.get(i).getEnd())
                {
                    currentPosition = i;
                    return;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
//            在捕获到异常后，
            // 隔100毫秒重新绘制视图，这样就总有一个时刻，会进入到onDraw方法里面，进行重新绘制视图
            postInvalidateDelayed(100);

        }
    }


}
