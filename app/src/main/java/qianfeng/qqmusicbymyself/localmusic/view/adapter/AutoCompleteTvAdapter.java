package qianfeng.qqmusicbymyself.localmusic.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import qianfeng.qqmusicbymyself.R;
import qianfeng.qqmusicbymyself.showmusic.model.bean.MusicBean;
import qianfeng.qqmusicbymyself.util.pinyinUtil.PinYin4j;

/**
 * Created by Administrator on 2016/10/31 0031.
 */
public class AutoCompleteTvAdapter extends BaseAdapter implements Filterable {

    // 这是未经过过滤的歌曲名
    private List<MusicBean> list;
    private Context context;
    private LayoutInflater inflater;

    // 这是已经经过过滤的歌曲名
    private List<MusicBean> filterList;

    // 存放一本书书名的全部拼音集合
    private List<Set<String>> MusicNamePinyin;

    // 存放一本书首字母拼音的集合
    private List<Set<String>> MusicNamePY;

    private PinYin4j pinyin4;

    private boolean isAdd = false;



    public AutoCompleteTvAdapter(List<MusicBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        pinyin4  = new PinYin4j();

        MusicNamePinyin = new ArrayList<>();
        MusicNamePY = new ArrayList<>();


        for (MusicBean musicBean : list) {

            Log.d("google-my:", "AutoCompleteTvAdapter: musicBean.getSongname(1)" + musicBean.getSongname());
            if (musicBean.getSongname() != null && musicBean.getSongname().length() > 0) {
                Log.d("google-my:", "AutoCompleteTvAdapter: musicBean.getSongname(2)" + musicBean.getSongname());
                MusicNamePinyin.add(pinyin4.getAllPinyin(musicBean.getSongname()));
                MusicNamePY.add(pinyin4.getPinyin(musicBean.getSongname()));
            }

        }

        Log.d("google-my:", "AutoCompleteTvAdapter:MusicNamePinyin " + MusicNamePinyin);
        Log.d("google-my:", "AutoCompleteTvAdapter:MusicNamePY " + MusicNamePY);
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
        ViewHolder holder = null;
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.singlesong_rv_item,parent,false);
            holder = new ViewHolder();
            holder.tv_songname = (TextView) convertView.findViewById(R.id.tv_songname);
            holder.tv_singername = (TextView) convertView.findViewById(R.id.tv_singername);
            holder.tv_albumname = (TextView) convertView.findViewById(R.id.tv_albumname);
            holder.iv_downmore = (ImageView) convertView.findViewById(R.id.iv_downmore);
            holder.iv_addsong = (ImageView) convertView.findViewById(R.id.iv_addsong);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        MusicBean musicBean = list.get(position);

        if (musicBean.getSongname() != null) {
            holder.tv_songname.setText(musicBean.getSongname());
        }
        if (musicBean.getSingername() != null) {
            holder.tv_singername.setText(musicBean.getSingername());
        }
        if(musicBean.getAlbumname()!= null)
        {
            holder.tv_albumname.setText(musicBean.getAlbumname());
        }

        holder.iv_addsong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "已添加到播放队列中", Toast.LENGTH_SHORT).show();
            }
        });

        holder.iv_downmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "应该从下面弹出一个popupWindow,显示多种操作", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    class ViewHolder{
        TextView tv_songname,tv_singername,tv_albumname;
        ImageView iv_downmore,iv_addsong;

    }



    @Override
    public Filter getFilter() {
        return new MyFilter(); // new一个文件过滤对象
    }


    class MyFilter extends Filter {


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // constraint: 是用户输入的文本
//            FilterResults filterResults = new FilterResults();
//
//            if(filterList == null)
//            {
//                filterList = new ArrayList<>(list); // 这个要显示过滤结果的集合，用list里面的内容初始化。
//            }
//
//            if(constraint == null && constraint.toString().length() == 0)  // 如果用户没有输入文本
//            {
//                filterResults.values = filterList; // 如果用户没有输入文本的话，就返回一个未过滤的集合
//                filterResults.count = 0;
//            }else  // 如果用户有输入文本
//            {
//                List<MusicBean> musicBeanList = new ArrayList<>(); // 拿一个集合来记录过滤后的内容啊！
//
//                // 拿到过滤文本后怎么处理？ 首先判断，是否是中文，如果是中文的话，转换为小写还是中文
//                String lowerCase = constraint.toString().toLowerCase();
//
//                for (int i = 0; i < filterList.size(); i++) {
//                    MusicBean musicBean = filterList.get(i);  // filterList集合里面放的全部都是中文歌曲名
//                    isAdd = false;
//                    if(musicBean.getSingername().contains(lowerCase)) // 如果是中文
//                    {
//                        musicBeanList.add(musicBean);
//                    }else
//                    {
//                       if(!isAdd)  // 如果还没有被添加进musicBeanList集合中
//                       {
//                           // 遍历pinyinList
//                           Set<String> strings = MusicNamePinyin.get(i);
//                           Iterator<String> iterator = strings.iterator();
//                           while(iterator.hasNext())
//                           {
//                               String next = iterator.next();
//                               if(next.contains(lowerCase))
//                               {
//                                   musicBeanList.add(musicBean);
//                                   isAdd = true;
//                                   break;
//                               }
//                           }
//                       }
//
//                        if(!isAdd)
//                        {
//                            Set<String> strings = MusicNamePY.get(i);
//                            Iterator<String> iterator = strings.iterator();
//                            while (iterator.hasNext())
//                            {
//                                String next = iterator.next();
//                                if(next.contains(lowerCase))
//                                {
//                                    musicBeanList.add(musicBean);
//                                    isAdd = true;
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                }
//
//                filterResults.values = musicBeanList;
//                filterResults.count = musicBeanList.size();
//
//            }
//
//
//            return filterResults;
            FilterResults results = new FilterResults();

            if (filterList == null) {
                // new出来的这个要过滤的集合，使用list作为参数的，也就是这个是复制从外面传进来的list的所有数据来构造自身的集合！
                filterList = new ArrayList<>(list); // filterBooks是new出来用于被过滤的集合!!! 注意里面传了个参数list!!
            }

            if (constraint == null && constraint.toString().length() == 0) { // 如果用户没有输入文本
                results.count = filterList.size();
                results.values = filterList; // 把这个ArrayList结果集返回。
            }else  // 如果用户有输入文本
            {
                List<MusicBean> bookList = new ArrayList<>();
                // 拿到过滤文本后怎么处理？一是判断是否中文，二是处理拼音。

                String lowerCase = constraint.toString().toLowerCase(); // 中文全部转化为小写之后还是中文

                for (int i = 0; i < filterList.size(); i++) {
                    isAdd = false; // 每次循环一开始，这个i所对应的元素都没有被添加进bookList集合中
                    MusicBean musicBean = filterList.get(i);
                    //假设用户输入了中文查询条件, filterBooks就是由list里面的数据原封不动的构造出来的，里面当然存储书名是用中文的
                    if(musicBean.getSongname().contains(lowerCase)) //假设用户输入了中文查询条件,
                    {
                        bookList.add(musicBean);
                    }else  // 用户输入的是拼音的情况,分两种，全拼和拼音首字母
                    {
                        if(!isAdd) // 为了保证满足两种条件的拼音不被重复添加进list集合中而定义的boolean变量。
                        {
                            // 用户输入了拼音查询的条件
                            // 姓名全拼查询
                            Set<String> strings = MusicNamePinyin.get(i);
                            Iterator<String> iterator = strings.iterator();
                            while(iterator.hasNext())
                            {
                                String next = iterator.next();
                                if(next.contains(lowerCase))
                                {
                                    bookList.add(musicBean);
                                    isAdd = true;
                                    break;
                                }
                            }
                        }

                        if(!isAdd)
                        {
                            // 姓名简拼查询
                            Set<String> strings = MusicNamePY.get(i);
                            Iterator<String> iterator = strings.iterator();
                            while(iterator.hasNext())
                            {
                                String next = iterator.next();
                                if(next.contains(lowerCase))
                                {
                                    bookList.add(musicBean);
                                    isAdd = true;
                                    break;
                                }
                            }
                        }
                    }


                }

                results.count = bookList.size();
                results.values = bookList;

            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<MusicBean> musicBeenList = (List<MusicBean>) results.values;
            if(musicBeenList != null && musicBeenList.size() > 0)
            {
                list.removeAll(list); // 移除掉List原本的所有数据
                list.addAll(musicBeenList); // 添加后来过滤后的数据
                notifyDataSetChanged(); // 当list的数据源发生改变时，立刻调用这个方法，重新刷新一遍视图，调用getView方法
            }
        }
    }
}

