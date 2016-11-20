package qianfeng.qqmusicbymyself.localmusic.presenter;

import android.content.Context;

import qianfeng.qqmusicbymyself.BasePersenter;
import qianfeng.qqmusicbymyself.localmusic.model.ILocalMusicData;
import qianfeng.qqmusicbymyself.localmusic.model.ILocalMusicDataImpl;
import qianfeng.qqmusicbymyself.localmusic.view.ILocalMusicView;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class LocalMusicFgPersenter implements BasePersenter {
    private ILocalMusicView iLocalMusicView;
    private ILocalMusicData iLocalMusicData;



    public LocalMusicFgPersenter(ILocalMusicView iLocalMusicView) {
        this.iLocalMusicView = iLocalMusicView;
        iLocalMusicData = new ILocalMusicDataImpl();
    }

    @Override
    public void start() {
        iLocalMusicView.initTabLayout(iLocalMusicData.getFour());
    }

    @Override
    public void start(Context context) {

    }






}

//
//    package qianfeng.qqmusicbymyself.showmusic.model;
//
//    import android.util.Log;
//
//    import java.util.ArrayList;
//    import java.util.List;
//
//    import qianfeng.qqmusicbymyself.util.dbutil.UserSearchHistory;
//    import qianfeng.qqmusicbymyself.util.dbutil.UserSearchHistoryDao;
//
//    /**
//     * Created by Administrator on 2016/10/26 0026.
//     */
//    public class ISearchHistoryDataImpl implements ISearchHistoryData {
//
//        private UserSearchHistory currentUserSearchHistory;
//        private UserSearchHistory filterUserSearchHistory;
//        private boolean isInsert = false;
//
//        @Override
//        public List<UserSearchHistory> loadHistory(UserSearchHistoryDao userSearchHistoryDao) {
//            List<UserSearchHistory> list = userSearchHistoryDao.queryBuilder().orderDesc(UserSearchHistoryDao.Properties.Id).list();
//            for (UserSearchHistory userSearchHistory : list) {
//                Log.d("google-my:", "loadHistory: 从数据库中读取的是什么数据+++" + userSearchHistory.getSearchHistory());
//            }
//            return list;
//        }
//
//        @Override
//        public void saveHistory(UserSearchHistoryDao userSearchHistoryDao, UserSearchHistory userSearchHistory) {
//            Log.d("google-my:", "saveHistory: 22++" + userSearchHistory.getSearchHistory());
//
//            if (!"".equals(userSearchHistory.getSearchHistory()) && userSearchHistory.getSearchHistory() != null) {// 加入空字符串判断，不让空字符串添加进集合中
//
//                List<UserSearchHistory> list1 = userSearchHistoryDao.queryBuilder().orderDesc(UserSearchHistoryDao.Properties.Id).list();
//
//                if (list1 != null && list1.size() == 0) {
//
//                    userSearchHistoryDao.insert(userSearchHistory);
//
//                } else if (list1 != null && list1.size() > 0) {
//                    // 拿到最新的那条数据
//                    currentUserSearchHistory = list1.get(list1.size() - 1);
//
//                    if(currentUserSearchHistory.getSearchHistory().equals(userSearchHistory)){
//                        // 如果和最新一条数据相等的话，就什么也不做
//                    }else
//                    {
//                        // 否则的话还是要验重啊！
//                        // 验完重之后才添加啊！
//                        for(int i = 0; i < list1.size(); i++)
//                        {
//                            isInsert = false;
//                            // 如果表中已有数据，就将其id增大，继续插入
//                            if(userSearchHistory.getSearchHistory().equals(list1.get(i).getSearchHistory()))
//                            {
//                                isInsert = true;
//                                UserSearchHistory userSearchHistory1 = new UserSearchHistory();
//                                userSearchHistory1.setSearchHistory(list1.get(i).getSearchHistory());
//                                userSearchHistoryDao.delete(list1.get(i));// 先删除旧数据
//                                userSearchHistoryDao.insert(userSearchHistory1); // 再添加新数据
//                                break;// 跳出for循环
//                            }
//                        }
//                        if(!isInsert)
//                        {
//                            userSearchHistoryDao.insert(userSearchHistory);
//                        }
//                    }
//
//                }
//
////        userSearchHistoryDao.insert(userSearchHistory);
//
//                long count = userSearchHistoryDao.queryBuilder().count();
//
//                // 如果数据库中的条目数大于20条，就按降序排列，删去最开始存储的哪一条数据。
//                if (count > 20) {
//                    List<UserSearchHistory> list = userSearchHistoryDao.queryBuilder().orderDesc(UserSearchHistoryDao.Properties.Id).list();
//
//                    userSearchHistoryDao.delete(list.get(list.size() - 1));
//
//                }
//            }
//        }
//
//        @Override
//        public boolean removeHistory(UserSearchHistoryDao userSearchHistoryDao, UserSearchHistory userSearchHistory) {
//            UserSearchHistory unique = userSearchHistoryDao.queryBuilder().where(UserSearchHistoryDao.Properties.Id.eq(userSearchHistory.getId())).unique();
//            if (unique != null) {
//                userSearchHistoryDao.delete(unique);
//                return true;
//            }
//
//            return false;
//        }
//
//        @Override
//        public List<String> searchTextFL() {
//            List<String> list = new ArrayList<>();
//            list.add("太阳的后裔");
//            list.add("BEYOND");
//            list.add("张学友");
//            list.add("搁浅");
//            list.add("蒙面唱将猜猜猜");
//            list.add("周杰伦");
//            list.add("洛天依");
//            list.add("许巍");
//            list.add("逆流成河");
//            list.add("陈奕迅");
//            list.add("DON'T SPEAK TIFFANY");
//            return list;
//        }
//    }
//
//
//
//
