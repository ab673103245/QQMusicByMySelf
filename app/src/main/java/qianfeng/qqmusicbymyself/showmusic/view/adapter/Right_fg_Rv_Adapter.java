package qianfeng.qqmusicbymyself.showmusic.view.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import qianfeng.qqmusicbymyself.R;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;

/**
 * Created by Administrator on 2016/11/2 0002.
 */
public class Right_fg_Rv_Adapter extends RecyclerView.Adapter {

    private List<MusicBean> list;
    private Context context;
    private LayoutInflater inflater;

    public Right_fg_Rv_Adapter(List<MusicBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    //    // 思考使用MVM模式还需要些什么参数
//    private int resId;  // 这个就是自己的资源文件LayoutInflater
//    private int variableId; // 布局文件musicbean这个对象的R文件Id

//    public Right_fg_Rv_Adapter(List<T> list, Context context, int resId, int variableId) {
//        this.list = list;
//        this.context = context;
//        this.resId = resId;
//        this.variableId = variableId;
//        inflater = LayoutInflater.from(context);
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        return new MyHolder(DataBindingUtil.inflate(inflater, resId, parent, false));
        return new MyHolder(inflater.inflate(R.layout.select_right_fg_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;

        // 一定要用binding来setVariableId(),
//        myHolder.getDataBinding().setVariable(variableId, list.get(position));

        MusicBean musicBean = list.get(position);
        if (musicBean.getAlbumpic_small() != null && !"".equals(musicBean.getAlbumpic_small())) {
            Picasso.with(context).load(musicBean.getAlbumpic_small()).into(myHolder.iv_albumpic_small);
        }

        myHolder.tv_songname.setText(musicBean.getSongname());
        myHolder.tv_singername.setText(musicBean.getSingername());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnClickListener {
        void clickReItem(int position);
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    class MyHolder extends RecyclerView.ViewHolder {
        ImageView iv_albumpic_small;
        TextView tv_songname, tv_singername;

        private ViewDataBinding dataBinding;

        public MyHolder(View itemView) {
            super(itemView);
            iv_albumpic_small = (ImageView) itemView.findViewById(R.id.iv_albumpic_small);
            tv_songname = (TextView) itemView.findViewById(R.id.tv_songname);
            tv_singername = (TextView) itemView.findViewById(R.id.tv_singername);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.clickReItem(getLayoutPosition());
                    }
                }
            });
        }

//        public MyHolder(ViewDataBinding dataBinding) {
//            super(dataBinding.getRoot());
//
//            this.dataBinding = dataBinding;
//
////            iv_albumpic_small = (ImageView) itemView.findViewById(R.id.iv_albumpic_small);
////            tv_songname = (TextView) itemView.findViewById(R.id.tv_songname);
////            tv_singername = (TextView) itemView.findViewById(R.id.tv_singername);
//            dataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (onClickListener != null) {
//                        onClickListener.clickReItem(getLayoutPosition());
//                    }
//                }
//            });

    }

//        public ViewDataBinding getDataBinding() {
//            return dataBinding;
//        }

}






