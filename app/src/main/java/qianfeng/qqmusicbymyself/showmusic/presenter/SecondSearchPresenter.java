package qianfeng.qqmusicbymyself.showmusic.presenter;

import android.content.Context;

import qianfeng.qqmusicbymyself.BasePersenter;
import qianfeng.qqmusicbymyself.showmusic.model.ISecondSearchData;
import qianfeng.qqmusicbymyself.showmusic.model.ISecondSearchDataImpl;
import qianfeng.qqmusicbymyself.showmusic.view.ISecondSearchView;

/**
 * Created by Administrator on 2016/10/28 0028.
 */
public class SecondSearchPresenter implements BasePersenter {
    private ISecondSearchView iSecondSearchView;
    private ISecondSearchData iSecondSearchData;

    public SecondSearchPresenter(ISecondSearchView iSecondSearchView) {
        this.iSecondSearchView = iSecondSearchView;
        iSecondSearchData = new ISecondSearchDataImpl();
    }

    @Override
    public void start() {
        iSecondSearchView.initTabLayoutAndViewpager(iSecondSearchData.FiveStringArray());
    }

    @Override
    public void start(Context context) {

    }
}
