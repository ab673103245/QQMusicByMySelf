package qianfeng.mylibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import qianfeng.mylibrary.bean.LrcBean;

/**
 * Created by 王松 on 2016/10/21.
 */

public class LrcView extends View {

    private List<LrcBean> list;
    private Paint gPaint;
    private Paint hPaint;
    private int width = 0, height = 0;
    private int currentPosition = 0;
    private MediaPlayer player;
    private int lastPosition = 0;
    private int mode = 0;
    public static final int KARAOKE = 1;

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setPlayer(MediaPlayer player) {
        this.player = player;
    }

    public void setList(List<LrcBean> list) {
        this.list = list;
    }

    public LrcView(Context context) {
        this(context, null);
    }

    public LrcView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LrcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LrcView);
        mode = ta.getInt(R.styleable.LrcView_lrcMode, mode);
        ta.recycle();

        gPaint = new Paint();
        gPaint.setAntiAlias(true);
        gPaint.setColor(getResources().getColor(android.R.color.darker_gray));
        gPaint.setTextSize(36);
        gPaint.setTextAlign(Paint.Align.CENTER);
        hPaint = new Paint();
        hPaint.setAntiAlias(true);
        hPaint.setColor(getResources().getColor(R.color.green));
        hPaint.setTextSize(36);
        hPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (width == 0 || height == 0) {
            width = getMeasuredWidth();
            height = getMeasuredHeight();
        }
        if (list == null || list.size() == 0) {
            canvas.drawText("暂无歌词", width / 2, height / 2, gPaint);
            return;
        }

        getCurrentPosition();

        int currentMillis = player.getCurrentPosition();
        long start = list.get(currentPosition).getStart();
        float v = (currentMillis - start) > 500 ? currentPosition * 80 : lastPosition * 80 + (currentPosition - lastPosition) * 80 * ((currentMillis - start) / 500f);
        setScrollY((int) v);
        if (getScrollY() == currentPosition * 80) {
            lastPosition = currentPosition;
        }
//        drawLrc1(canvas);
//        drawLrc2(canvas);
        drawLrc3(canvas, currentMillis);
        postInvalidateDelayed(100);
    }

    private void drawLrc3(Canvas canvas, long currentMillis) {
        if (mode == 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i == currentPosition) {
                    canvas.drawText(list.get(i).getText(), width / 2, height / 2 + 80 * i, hPaint);
                } else {
                    canvas.drawText(list.get(i).getText(), width / 2, height / 2 + 80 * i, gPaint);
                }
            }
        } else {
            for (int i = 0; i < list.size(); i++) {
                canvas.drawText(list.get(i).getText(), width / 2, height / 2 + 80 * i, gPaint);
            }
//        canvas.drawText(list.get(currentPosition).getText(), width / 2, height / 2 + 80 * currentPosition, hPaint);
            LrcBean lrcBean = list.get(currentPosition);
            float highLineWidth = gPaint.measureText(lrcBean.getText());
            int bitmapWidth = (int) ((currentMillis - lrcBean.getStart()) * 1.0f / (lrcBean.getEnd() - lrcBean.getStart()) * highLineWidth);
            if (bitmapWidth > 0) {
                Bitmap textBitmap = Bitmap.createBitmap(bitmapWidth, 90, Bitmap.Config.ARGB_8888);// 修改bitmap大小,使其完全覆盖字体，原本为80，不够覆盖字体
                Canvas textCanvas = new Canvas(textBitmap);
                textCanvas.drawText(lrcBean.getText(), highLineWidth / 2, 80, hPaint);
                canvas.drawBitmap(textBitmap, (width - highLineWidth) / 2, height / 2 + 80 * (currentPosition - 1), null);
            }
        }
    }

    private void drawLrc2(Canvas canvas) {
        for (int i = 0; i < list.size(); i++) {
            if (i == currentPosition) {
                canvas.drawText(list.get(i).getText(), width / 2, height / 2 + 80 * i, hPaint);
            } else {
                canvas.drawText(list.get(i).getText(), width / 2, height / 2 + 80 * i, gPaint);
            }
        }
    }

    public void init() {
        currentPosition = 0;
        lastPosition = 0;
        setScrollY(0);
        invalidate();
    }

    private void drawLrc1(Canvas canvas) {
        String text = list.get(currentPosition).getText();
        canvas.drawText(text, width / 2, height / 2, hPaint);

        for (int i = 1; i < 10; i++) {
            int index = currentPosition - i;
            if (index > -1) {
                canvas.drawText(list.get(index).getText(), width / 2, height / 2 - 80 * i, gPaint);
            }
        }
        for (int i = 1; i < 10; i++) {
            int index = currentPosition + i;
            if (index < list.size()) {
                canvas.drawText(list.get(index).getText(), width / 2, height / 2 + 80 * i, gPaint);
            }
        }
    }

    private void getCurrentPosition() {
        try {
            int currentMillis = player.getCurrentPosition();
            if (currentMillis < list.get(0).getStart()) {
                currentPosition = 0;
                return;
            }
            if (currentMillis > list.get(list.size() - 1).getStart()) {
                currentPosition = list.size() - 1;
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                if (currentMillis >= list.get(i).getStart() && currentMillis < list.get(i).getEnd()) {
                    currentPosition = i;
                    return;
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
            postInvalidateDelayed(100);
        }
    }
}
