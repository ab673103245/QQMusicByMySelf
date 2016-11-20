package qianfeng.qqmusicbymyself.showmusic.view.adapter.listViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import qianfeng.qqmusicbymyself.R;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;

/**
 * Created by Administrator on 2016/11/3 0003.
 */
public class MyAdapter extends BaseAdapter {
    private List<MusicBean> list;
    private LayoutInflater inflater;
    private Context context;

    public MyAdapter(List<MusicBean> list, Context context) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.popop_listview_item,parent,false);
            holder = new ViewHolder();
            holder.tv_songname = (TextView) convertView.findViewById(R.id.tv_songname);
            holder.tv_singername = (TextView) convertView.findViewById(R.id.tv_singername);
            holder.iv = (ImageView) convertView.findViewById(R.id.iv);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        MusicBean musicBean = list.get(position);

        if(musicBean.getSongname()!=null && !"".equals(musicBean.getSongname()))
        {
            holder.tv_songname.setText(musicBean.getSongname());
        }

        if(musicBean.getSingername()!=null && !"".equals(musicBean.getSingername()))
        {
            holder.tv_singername.setText(musicBean.getSingername());
        }

        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, position +  ":", Toast.LENGTH_SHORT).show();
            }
        });



        return convertView;
    }

    class ViewHolder
    {
        TextView tv_songname,tv_singername;
        ImageView iv;
    }
}
