package qianfeng.qqmusicbymyself.showmusic.model.bean;

/**
 * Created by Administrator on 2016/11/11 0011.
 */
public class UserBean {
    private String userface;
    private String username;

    public UserBean(String userface, String username) {
        this.userface = userface;
        this.username = username;
    }

    public UserBean() {
    }

    public String getUserface() {
        return userface;
    }

    public void setUserface(String userface) {
        this.userface = userface;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userface='" + userface + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
