package qianfeng.qqmusicbymyself.showmusic.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import qianfeng.qqmusicbymyself.R;
import qianfeng.qqmusicbymyself.showmusic.model.bean.CategoryBean;

/**
 * Created by Administrator on 2016/11/1 0001.
 */
public class Left_fg_ListAdapter extends BaseAdapter {

    private List<CategoryBean> list;
    private Context context;
    private LayoutInflater inflater;

    private int clickPosition = 0;

    public Left_fg_ListAdapter(List<CategoryBean> list, Context context) {
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
        if (convertView == null) {
            // 只有在Fragment中动态创建的ViewPager不需要父容器协助生成布局参数，在其余的场合，都需要父容器协助提供布局参数，协助自身生成布局。
            convertView = inflater.inflate(R.layout.select_left_fg_item, parent, false);
            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.tv);
            holder.line = convertView.findViewById(R.id.line);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CategoryBean categoryBean = list.get(position);

        if(position == 0)
        {
            holder.line.setVisibility(View.VISIBLE);
            holder.tv.setTextColor(Color.WHITE);
        }

        refreshView(clickPosition);

        holder.tv.setText(categoryBean.getCategory());


        return convertView;
    }

    private void refreshView(int clickPosition) {

    }


    class ViewHolder {
        TextView tv;
        View line;
    }
}
