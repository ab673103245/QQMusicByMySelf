package qianfeng.qqmusicbymyself.showmusic.model.bean;

/**
 * Created by Administrator on 2016/11/1 0001.
 */
public class CategoryBean {
    private String category;
    private int id;

    public CategoryBean(String category, int id) {
        this.category = category;
        this.id = id;
    }

    public CategoryBean() {
    }

    @Override
    public String toString() {
        return "CategoryBean{" +
                "category='" + category + '\'' +
                ", id=" + id +
                '}';
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
