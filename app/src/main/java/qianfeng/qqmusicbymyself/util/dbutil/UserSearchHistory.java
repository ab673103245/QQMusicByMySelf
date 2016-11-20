package qianfeng.qqmusicbymyself.util.dbutil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2016/10/26 0026.
 */
@Entity
public class UserSearchHistory {
    @Id
    private Long id;

    @Property(nameInDb = "sh")
    private String searchHistory;

    @Generated(hash = 2053319421)
    public UserSearchHistory(Long id, String searchHistory) {
        this.id = id;
        this.searchHistory = searchHistory;
    }

    @Generated(hash = 525076550)
    public UserSearchHistory() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearchHistory() {
        return this.searchHistory;
    }

    public void setSearchHistory(String searchHistory) {
        this.searchHistory = searchHistory;
    }

    

}
