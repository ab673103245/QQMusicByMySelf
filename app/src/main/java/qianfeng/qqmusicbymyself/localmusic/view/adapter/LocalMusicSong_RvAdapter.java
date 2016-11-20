package qianfeng.qqmusicbymyself.localmusic.view.adapter;

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
public class LocalMusicSong_RvAdapter extends RecyclerView.Adapter {

    private List<MusicBean> list;
    private LayoutInflater inflater;
    private Context context;

    public LocalMusicSong_RvAdapter(List<MusicBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case -2: {
                return new MyType2Holder(inflater.inflate(R.layout.local_type2_item,parent,false));
            }

            case -1: {
                return new MyType1Holder(inflater.inflate(R.layout.local_type1_item, parent, false));
            }

            case 0: {
                return new MyType0Holder(inflater.inflate(R.layout.local_type0_item, parent, false));
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MusicBean musicBean = list.get(position);
        int itemViewType = getItemViewType(position);
        Integer type = list.get(position).getType();
         switch (type) {
            case -2: {
                MyType2Holder holder_2 = ((MyType2Holder) holder);
            }
            break;

            case -1: {
                MyType1Holder holder_1 = (MyType1Holder) holder;
            }
            break;

            case 0: {
                MyType0Holder holder_0 = (MyType0Holder) holder;
                holder_0.tv_songname.setText(musicBean.getSongname());
                holder_0.tv_singername.setText(musicBean.getSingername());
                holder_0.tv_albumname.setText(musicBean.getAlbumname());
            }
            break;

        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    class MyType0Holder extends RecyclerView.ViewHolder {
        ImageView iv_downmore;
        TextView tv_songname, tv_singername, tv_albumname;

        public MyType0Holder(View itemView) {
            super(itemView);
            iv_downmore = (ImageView) itemView.findViewById(R.id.iv_downmore);
            tv_songname = (TextView) itemView.findViewById(R.id.tv_songname);
            tv_singername = (TextView) itemView.findViewById(R.id.tv_singername);
            tv_albumname = (TextView) itemView.findViewById(R.id.tv_albumname);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onType0ItemClickListener != null)
                    {
                        onType0ItemClickListener.onType0ItemClick(getLayoutPosition());
                    }
                }
            });

            iv_downmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onType0ItemClickListener != null)
                    {
                        onType0ItemClickListener.onType0ImageViewClick(getLayoutPosition());
                    }
                }
            });

        }
    }

    private OnType0ItemClickListener onType0ItemClickListener;

    public interface OnType0ItemClickListener {
        void onType0ItemClick(int position);
        void onType0ImageViewClick(int position);
    }

    public void setOnType0ItemClickListener(OnType0ItemClickListener onType0ItemClickListener) {
        this.onType0ItemClickListener = onType0ItemClickListener;
    }







    class MyType1Holder extends RecyclerView.ViewHolder {


        public MyType1Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onType1ItemClickListener != null) {
                        onType1ItemClickListener.onType1ItemClick();
                    }
                }
            });
        }
    }

    private OnType1ItemClickListener onType1ItemClickListener;

    public interface OnType1ItemClickListener {
        void onType1ItemClick();
    }

    public void setOnType1ItemClickListener(OnType1ItemClickListener onType1ItemClickListener) {
        this.onType1ItemClickListener = onType1ItemClickListener;
    }


    class MyType2Holder extends RecyclerView.ViewHolder {

        public MyType2Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onType2ItemClickListener != null) {
                        onType2ItemClickListener.onType2ItemClick();
                    }
                }
            });
        }
    }

    private OnType2ItemClickListener onType2ItemClickListener;

    public interface OnType2ItemClickListener {
        void onType2ItemClick();
    }

    public void setOnType2ItemClickListener(OnType2ItemClickListener onType2ItemClickListener) {
        this.onType2ItemClickListener = onType2ItemClickListener;
    }


}
