package qianfeng.qqmusicbymyself.showmusic.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import qianfeng.qqmusicbymyself.R;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class SingleSongRvAdapter extends RecyclerView.Adapter {

    private List<MusicBean> list;
    private Context context;
    private LayoutInflater inflater;

    public SingleSongRvAdapter(List<MusicBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(inflater.inflate(R.layout.singlesong_rv_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MusicBean musicBean = list.get(position);
        MyHolder myHolder = (MyHolder) holder;
        if (musicBean.getSongname() != null) {
            myHolder.tv_songname.setText(musicBean.getSongname());
        }
        if (musicBean.getSingername() != null) {
            myHolder.tv_singername.setText(musicBean.getSingername());
        }
        if(musicBean.getAlbumname()!= null)
        {
            myHolder.tv_albumname.setText(musicBean.getAlbumname());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView tv_songname,tv_singername,tv_albumname;
        ImageView iv_downmore,iv_addsong;

        public MyHolder(View itemView) {
            super(itemView);
            tv_songname = (TextView) itemView.findViewById(R.id.tv_songname);
            tv_singername = (TextView) itemView.findViewById(R.id.tv_singername);
            tv_albumname = (TextView) itemView.findViewById(R.id.tv_albumname);
            iv_downmore = (ImageView) itemView.findViewById(R.id.iv_downmore);
            iv_addsong = (ImageView) itemView.findViewById(R.id.iv_addsong);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemCilckListener != null)
                    {
                        onItemCilckListener.onItemCilck(getLayoutPosition());
                    }
                }
            });

            iv_addsong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemCilckListener != null)
                    {
                        onItemCilckListener.onAddIvClick(getLayoutPosition());
                    }
                }
            });

            iv_downmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemCilckListener != null)
                    {
                        onItemCilckListener.onDownIvClick(getLayoutPosition());
                    }
                }
            });


        }
    }

    public interface OnItemCilckListener{
        void onItemCilck(int position);
        void onAddIvClick(int position);
        void onDownIvClick(int position);
    }

    private OnItemCilckListener onItemCilckListener;

    public void setOnItemCilckListener(OnItemCilckListener onItemCilckListener)
    {
        this.onItemCilckListener = onItemCilckListener;
    }

}
