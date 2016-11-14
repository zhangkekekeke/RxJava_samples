package keye.com.rxjavaobserver.bean;


import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2016-11-03.
 */
public class DataUtils {


    //耗时操作
    public static List<Integer> getData2() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


    public static List<Author> getData() {
        final int[] i = {0};
        List<Author> authorList = new ArrayList<>();

        Observable.just("张三", "王二", "轲叶")
                .map(name -> new Author(name))
                .map(author -> {
                    author.getArticles().add("article" + i[0]++);
                    author.getArticles().add("article" + i[0]++);
                    author.getArticles().add("article" + i[0]++);
                    return author;
                })
                .map(author -> {
                    authorList.add(author);
                    return authorList;
                })
                .subscribe();

        return authorList;

    }
}
