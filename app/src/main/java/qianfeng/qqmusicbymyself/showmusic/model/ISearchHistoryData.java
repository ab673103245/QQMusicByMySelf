package qianfeng.qqmusicbymyself.showmusic.model;

import java.util.List;

import qianfeng.qqmusicbymyself.util.dbutil.UserSearchHistory;
import qianfeng.qqmusicbymyself.util.dbutil.UserSearchHistoryDao;

/**
 * Created by Administrator on 2016/10/26 0026.
 */
public interface ISearchHistoryData {


    List<UserSearchHistory> loadHistory(UserSearchHistoryDao userSearchHistoryDao);

    void saveHistory(UserSearchHistoryDao userSearchHistoryDao, UserSearchHistory userSearchHistory);

    boolean removeHistory(UserSearchHistoryDao userSearchHistoryDao, UserSearchHistory userSearchHistory);

    List<String> searchTextFL();

}
