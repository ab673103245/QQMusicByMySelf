package qianfeng.mylibrary.bean;

/**
 * Created by Administrator on 2016/11/5 0005.
 */
public class LrcBean {
    private String text;
    private long start;
    private long end;

    public LrcBean(String text, long start, long end) {
        this.text = text;
        this.start = start;
        this.end = end;
    }

    public LrcBean() {
    }

    @Override
    public String toString() {
        return "LrcBean{" +
                "text='" + text + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
