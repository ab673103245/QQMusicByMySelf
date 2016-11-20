package qianfeng.qqmusicbymyself.showmusic.view;

import java.util.List;

import qianfeng.qqmusicbymyself.util.dbutil.UserSearchHistory;

/**
 * Created by Administrator on 2016/10/26 0026.
 */
public interface ISearchView {
    void initRecycleView(List<UserSearchHistory> searchHistoryList);

    void initFlexLayout(List<UserSearchHistory> StringFromHostList);

    void initFl(List<String> list);
}
