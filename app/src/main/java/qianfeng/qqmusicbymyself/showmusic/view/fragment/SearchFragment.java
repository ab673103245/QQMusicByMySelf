package qianfeng.qqmusicbymyself.showmusic.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

import qianfeng.qqmusicbymyself.BaseFragement;
import qianfeng.qqmusicbymyself.R;
import qianfeng.qqmusicbymyself.showmusic.presenter.SearchPresenter;
import qianfeng.qqmusicbymyself.showmusic.view.ISearchView;
import qianfeng.qqmusicbymyself.showmusic.view.adapter.MyRecycleAdapter;
import qianfeng.qqmusicbymyself.showmusic.view.fragment.search_fg.Search_second_fg;
import qianfeng.qqmusicbymyself.util.Consts;
import qianfeng.qqmusicbymyself.util.dbutil.UserSearchHistory;

/**
 * Created by Administrator on 2016/10/26 0026.
 */
public class SearchFragment extends BaseFragement implements ISearchView {

    private RecyclerView recycleView;
    private FlexboxLayout flexLayout;
    private EditText et_content;
    private View view;

    private List<String> flTextList;


    private SearchPresenter searchPresenter = new SearchPresenter(this);
    private TextView toolbar_searchText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            Log.d("google-my:", "onCreateView: 我是searchFragment[] ");
            view = inflater.inflate(R.layout.search_fg_layout, container, false);
        }

        searchPresenter.initFlList();// 先拿到集合的个数，再用来初始化Fl里面的子tv的个数,一调用这个，就相当于给flTextList赋了一个引用了。

        initView(view);

        searchPresenter.start(getActivity()); // 开始加载数据库的数据


        return view;
    }

    private void initView(View view) {
        recycleView = ((RecyclerView) view.findViewById(R.id.recyclerView));
        flexLayout = ((FlexboxLayout) view.findViewById(R.id.f1));
        et_content = ((EditText) view.findViewById(R.id.et_content));
        toolbar_searchText = ((TextView) view.findViewById(R.id.toolbar_searchText));


        searchBtnClick();// 一旦点击了搜索按钮。

        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Consts.selectSongName = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        int left_right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
        int top_bottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());

        for (int i = 0; i < flTextList.size(); i++) {
            final TextView tv = new TextView(getActivity());
            FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            lp.setMargins(left_right,top_bottom,left_right,top_bottom);
            tv.setBackgroundResource(R.drawable.tv_bg);

            tv.setText(flTextList.get(i));
            tv.setTextColor(Color.WHITE);

            tv.setLayoutParams(lp);

            // 当tv被点击时，背景变绿色
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv.setBackgroundResource(R.drawable.tv_bg2);
                    et_content.setText(tv.getText().toString());
                    Consts.selectSongName = et_content.getText().toString();
                    searchBtnClick();// 这里是监听，没什么用，只不过想复用里面的数据库存储数据的方法而已。
                    // 还是得自己replace掉，然后显示下一个Fragment啊
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ll, new Search_second_fg()).commit();
                }
            });

            flexLayout.addView(tv);// 把tv添加进flexLayout中


            // 这是得到tv的布局参数，并不是得到其父容器的布局参数，margin等属性是父容器的布局参数才有的。
//            ViewGroup.LayoutParams lp = tv.getLayoutParams();
//            tv.requestLayout();
        }


    }

    private void searchBtnClick() {
        toolbar_searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!"".equals(et_content.getText()) || et_content.getText().toString() != null) {
                    UserSearchHistory userSearchHistory = new UserSearchHistory();
                    Log.d("google-my:", "onClick: userSearchHistory.setSearchHistory" + et_content.getText().toString());
                    userSearchHistory.setSearchHistory(et_content.getText().toString());
                    searchPresenter.saveUserSearchHistory(userSearchHistory);

                    Consts.selectSongName = et_content.getText().toString();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ll, new Search_second_fg()).commit();
                }
            }
        });
    }

    @Override
    public void initRecycleView(final List<UserSearchHistory> searchHistoryList) {
        // 在这里就已经能拿到数据了啊
        MyRecycleAdapter adapter = new MyRecycleAdapter(searchHistoryList, getActivity(), searchPresenter);
        // 查了一天的BUG，问题出在这里，请记住它！！！RecycleView一定要设置LayoutManager！否则显示不了item
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));// 一定要有这行，否则RecycleView数据出不来！
        recycleView.setAdapter(adapter);

        // recycleView中没有直接提供点击事件的API，因为它能作用于ListView和GridView和瀑布流布局，所以不知道怎么设置，这就需要你在adapter里面手动设置接口回调来设置点击事件
        adapter.setOnItemClickListener(new MyRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // 究竟删除过后的list，对这个传过来的searchHistoryList有没有影响？一直传？其实都是传递地址，没影响。都是指向堆中的同一地址。

                Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show();
                et_content.setText(searchHistoryList.get(position).getSearchHistory());

                UserSearchHistory userSearchHistory = new UserSearchHistory();
                userSearchHistory.setSearchHistory(searchHistoryList.get(position).getSearchHistory());
                searchPresenter.saveUserSearchHistory(userSearchHistory);

                // 思考这行代码到底需不需要？
                Consts.selectSongName = searchHistoryList.get(position).getSearchHistory();

                // 点击之后，拿另一个Fragment来替代本Fragment，只不过那个Fragment有上面的Toolbar，参数可以通过setArgument来传递
//                Bundle args = new Bundle();
//                args.putString("content",et_content.getText().toString());
//                setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ll, new Search_second_fg()).commit();

            }
        });

    }

    @Override
    public void initFlexLayout(List<UserSearchHistory> StringFromHostList) {

    }

    @Override
    public void initFl(List<String> list) {
        flTextList = list;
    }


}
