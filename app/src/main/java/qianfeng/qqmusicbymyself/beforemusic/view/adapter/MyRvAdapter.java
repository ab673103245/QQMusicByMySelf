package qianfeng.qqmusicbymyself.beforemusic.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import qianfeng.qqmusicbymyself.R;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;

/**
 * Created by Administrator on 2016/11/3 0003.
 */
public class MyRvAdapter extends RecyclerView.Adapter {

    private List<MusicBean> list;
    private Context context;
    private LayoutInflater inflater;

    public MyRvAdapter(List<MusicBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.before_fg_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder holder1 = (MyViewHolder) holder;
        MusicBean musicBean = list.get(position);
        holder1.tv_songname.setText(musicBean.getSongname());
        holder1.tv_singername.setText(musicBean.getSingername());
        holder1.tv_albumname.setText(musicBean.getAlbumname());
        int playcount = musicBean.getPlaycount() + 1;
        holder1.tv_playcount.setText(playcount + "");
        holder1.iv_downmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "iv-->被点击了", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_downmore;
        TextView tv_playcount, tv_songname, tv_singername, tv_albumname;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_downmore = (ImageView) itemView.findViewById(R.id.iv_downmore);
            tv_playcount = (TextView) itemView.findViewById(R.id.tv_playcount);
            tv_songname = (TextView) itemView.findViewById(R.id.tv_songname);
            tv_singername = (TextView) itemView.findViewById(R.id.tv_singername);
            tv_albumname = (TextView) itemView.findViewById(R.id.tv_albumname);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
