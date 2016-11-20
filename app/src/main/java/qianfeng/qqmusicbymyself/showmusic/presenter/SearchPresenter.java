package qianfeng.qqmusicbymyself.showmusic.presenter;

import android.content.Context;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

import java.util.List;

import qianfeng.qqmusicbymyself.BasePersenter;
import qianfeng.qqmusicbymyself.showmusic.model.ISearchHistoryData;
import qianfeng.qqmusicbymyself.showmusic.model.ISearchHistoryDataImpl;
import qianfeng.qqmusicbymyself.showmusic.model.bean.DaoMaster;
import qianfeng.qqmusicbymyself.showmusic.model.bean.DaoSession;
import qianfeng.qqmusicbymyself.showmusic.view.ISearchView;
import qianfeng.qqmusicbymyself.util.dbutil.UserSearchHistory;
import qianfeng.qqmusicbymyself.util.dbutil.UserSearchHistoryDao;


/**
 * Created by Administrator on 2016/10/26 0026.
 */
public class SearchPresenter implements BasePersenter {

    private ISearchView iSearchView;

    private ISearchHistoryData iSearchHistoryData;

    private UserSearchHistoryDao userSearchHistoryDao;

    public SearchPresenter(ISearchView iSearchView) {
        this.iSearchView = iSearchView;
        iSearchHistoryData = new ISearchHistoryDataImpl();
    }

    @Override
    public void start() {

    }
    public void removeHistory(UserSearchHistory userSearchHistory)
    {
        iSearchHistoryData.removeHistory(userSearchHistoryDao,userSearchHistory);
    }

    public void saveUserSearchHistory(UserSearchHistory userSearchHistory)
    {
        Log.d("google-my:", "saveUserSearchHistory: ++" + userSearchHistory.getSearchHistory());
        iSearchHistoryData.saveHistory(userSearchHistoryDao,userSearchHistory);
    }

    public List<UserSearchHistory> loadUserSearchHistory()
    {
        return iSearchHistoryData.loadHistory(userSearchHistoryDao);
    }

    private void initUserSearchHistoryDao(Context context) {
        DaoMaster.DevOpenHelper dbHelper = new DaoMaster.DevOpenHelper(context,"android.db");
        Database db = dbHelper.getWritableDb();
        DaoSession daoSession = new DaoMaster(db).newSession();
        userSearchHistoryDao = daoSession.getUserSearchHistoryDao();
    }

    @Override
    public void start(Context context) {
        if(userSearchHistoryDao == null)
        {
            initUserSearchHistoryDao(context);
        }

        iSearchView.initRecycleView(iSearchHistoryData.loadHistory(userSearchHistoryDao));

    }

    public void initFlList()
    {
        iSearchView.initFl(iSearchHistoryData.searchTextFL());
    }



}
