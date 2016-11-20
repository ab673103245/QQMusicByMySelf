package qianfeng.qqmusicbymyself.showmusic.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import qianfeng.qqmusicbymyself.R;
import qianfeng.qqmusicbymyself.showmusic.model.bean.StringLoveBean;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class MyListViewAdapter extends BaseAdapter{
    private List<StringLoveBean> list;
    private Context context;
    private LayoutInflater inflater;

    public MyListViewAdapter(List<StringLoveBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.mine_mylistview_item,parent,false);
            holder = new ViewHolder();
            holder.iv_itemIv = (ImageView) convertView.findViewById(R.id.iv_itemIv);
            holder.tv1 = (TextView) convertView.findViewById(R.id.tv1);// 轻松旋律
            holder.tv2 = (TextView) convertView.findViewById(R.id.tv2); // 多少首歌
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        // 这是用户可见的最后一个item-->position
        StringLoveBean stringLoveBean = list.get(position);
        holder.iv_itemIv.setImageResource(stringLoveBean.getImgs());
        holder.tv1.setText(stringLoveBean.getSongname());
        holder.tv2.setText(stringLoveBean.getSongCount());
        return convertView;
    }

    class ViewHolder
    {
        ImageView iv_itemIv;
        TextView tv1,tv2;
    }
}
