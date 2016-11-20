package qianfeng.qqmusicbymyself.showmusic.view.fragment.select_fg;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import qianfeng.qqmusicbymyself.BaseFragement;
import qianfeng.qqmusicbymyself.R;
import qianfeng.qqmusicbymyself.showmusic.model.bean.CategoryBean;
import qianfeng.qqmusicbymyself.showmusic.presenter.select_presenter.Select_left_fg_Presenter;

/**
 * Created by Administrator on 2016/11/1 0001.
 * 用ScrollView做成一个ListView的效果，但是没有每个item下面的下划线
 */
public class LeftFragment extends BaseFragement implements ISelectLeftFgView {
    private LinearLayout ll;
    private Select_left_fg_Presenter selectLeftPresenter = new Select_left_fg_Presenter(this);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_left_fg_layout, container, false);
        initView(view);

        selectLeftPresenter.start();

        return view;
    }

    private void initView(View view) {
        ll = ((LinearLayout) view.findViewById(R.id.ll));

    }


    @Override
    public void initMyListView(final List<CategoryBean> list) {


        for (int i = 0; i < list.size(); i++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.select_left_fg_item, ll, false); // LinearLayout

            TextView tv = ((TextView) view.findViewById(R.id.tv));
            tv.setText(list.get(i).getCategory());

            View line = ((View) view.findViewById(R.id.line));
            line.setVisibility(View.GONE);

            final int finalI = i;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   updataColor(finalI);
                    if(rightFragment != null)
                    {
                        rightFragment.updataRightId(list.get(finalI).getId());
                    }
                }
            });

            ll.addView(view);

        }

        TextView tv = (TextView) ll.getChildAt(0).findViewById(R.id.tv);
        View line = (View) ll.getChildAt(0).findViewById(R.id.line);
        // java代码中，得到res文件下的资源文件的方法，getResource().
        tv.setTextColor(getResources().getColor(R.color.colorPrimary));
        line.setVisibility(View.VISIBLE);



    }
//@android:drawable/ic_input_add
    private void updataColor(int finalI) {

        int childCount = ll.getChildCount();

        for (int i = 0; i < childCount; i++) {
            if(finalI == i)
            {
                View childAt = ll.getChildAt(i);
                TextView tv = (TextView) childAt.findViewById(R.id.tv);
                tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                View line = (View) childAt.findViewById(R.id.line);
                line.setVisibility(View.VISIBLE);
            }else
            {
                View childAt = ll.getChildAt(i);
                TextView tv = (TextView) childAt.findViewById(R.id.tv);
                tv.setTextColor(Color.GRAY);
                View line = (View) childAt.findViewById(R.id.line);
                line.setVisibility(View.GONE);
            }
        }

    }

    private RightFragment rightFragment;

    public void setRightFg(RightFragment rightFg)
    {
        this.rightFragment = rightFg;
    }


}
