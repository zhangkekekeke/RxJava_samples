package keye.com.rxjavaobserver.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-11-03.
 */
public class Author {
    public List<String> Articles=new ArrayList<>();
    public String name;

    public Author(String name) {
        this.name = name;
    }

    public List<String> getArticles() {
        return Articles;
    }

    public void setArticles(List<String> articles) {
        Articles = articles;
    }
}
