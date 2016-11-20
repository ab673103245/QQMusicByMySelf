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
import qianfeng.qqmusicbymyself.showmusic.presenter.SearchPresenter;
import qianfeng.qqmusicbymyself.util.dbutil.UserSearchHistory;

/**
 * Created by Administrator on 2016/10/26 0026.
 */
public class MyRecycleAdapter extends RecyclerView.Adapter {
    private List<UserSearchHistory> list;
    private Context context;
    private LayoutInflater inflater;
    private SearchPresenter searchPresenter;



    public MyRecycleAdapter(List<UserSearchHistory> list, Context context, SearchPresenter searchPresenter) {
        this.list = list;
        this.context = context;
        this.searchPresenter = searchPresenter;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(inflater.inflate(R.layout.search_recycleview_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyHolder) holder).tv.setText(list.get(position).getSearchHistory());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private ImageView iv1,iv2;
        private TextView tv;
        public MyHolder(View itemView) {
            super(itemView);
            iv1 = ((ImageView) itemView.findViewById(R.id.iv1));
            iv2 = ((ImageView) itemView.findViewById(R.id.iv2));
            tv = ((TextView) itemView.findViewById(R.id.tv));

            // 在构造方法里面设置每个item的点击事件。
            iv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { // iv2是x，表示从数据库中移除这条数据
                    searchPresenter.removeHistory(list.get(getLayoutPosition()));
                    list.remove(list.get(getLayoutPosition())); // 从List集合中移除这条数据，并notifyDataSetChange刷新页面。
                    notifyDataSetChanged();// 刷新页面
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null)
                    {
                        onItemClickListener.onItemClick(getLayoutPosition()); // getlayoutPosition: 得到布局里面真实的position
                    }
                }
            });



        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(int position);  // 这个position是layoutPosition，是从getLayoutPosition传出去的值
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }


}
