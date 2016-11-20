package qianfeng.qqmusicbymyself.showmusic.presenter.select_presenter;

import android.content.Context;

import java.util.List;

import qianfeng.qqmusicbymyself.BasePersenter;
import qianfeng.qqmusicbymyself.showmusic.model.bean.CategoryBean;
import qianfeng.qqmusicbymyself.showmusic.model.select_model.ISelectLeftFgData;
import qianfeng.qqmusicbymyself.showmusic.model.select_model.ISelectLeftFgDataImpl;
import qianfeng.qqmusicbymyself.showmusic.view.fragment.select_fg.ISelectLeftFgView;

/**
 * Created by Administrator on 2016/11/1 0001.
 */
public class Select_left_fg_Presenter implements BasePersenter{

    private ISelectLeftFgView iSelectLeftFgView;
    private ISelectLeftFgData iSelectLeftFgData;


    public Select_left_fg_Presenter(ISelectLeftFgView iSelectLeftFgView) {
        this.iSelectLeftFgView = iSelectLeftFgView;
        iSelectLeftFgData = new ISelectLeftFgDataImpl();
    }


    @Override
    public void start() {
        List<CategoryBean> category = iSelectLeftFgData.getCategory();
        iSelectLeftFgView.initMyListView(category);
    }

    @Override
    public void start(Context context) {

    }
}
