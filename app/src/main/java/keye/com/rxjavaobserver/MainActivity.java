package keye.com.rxjavaobserver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import keye.com.rxjavaobserver.bean.Author;
import keye.com.rxjavaobserver.bean.DataUtils;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG_RxJava = "RxJava";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化Logger
        Logger.init(TAG_RxJava).logLevel(LogLevel.FULL);

//        test01();
        test02();
//        test03();
//        test04();
//        test07();
    }

    /**
     * 使用 Observable.create() 方法来创建一个 Observable 并为它定义事件触发规则
     */
    public void test01() {

        //创建Observable
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello World!");
                subscriber.onNext("Hello JiKeXueYuan");
                subscriber.onCompleted();
            }
        });

        //创建Observer
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Logger.d("RxJava -->> onCompleted()!");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Logger.d("RxJava -->> onNext()" + s);
            }
        };

        //订阅
        observable.subscribe(observer);

    }

    /**
     * Observable创建from()
     */
    public void test02() {
        new String[]{"Hello World!", "Hello KeYe!", "Hello jikexueyuan"}
        String[] array = ;
        List<String> list = new ArrayList<>();
        //创建Observable
        Observable<String> observable = Observable.from(array);

        //创建Observer
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Logger.d("RxJava -->> onCompleted()!");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                list.add(s);
                Logger.d("RxJava -->> onNext()" + s);
            }
        };

        observable.subscribe(observer);

    }

    /**
     * Observable操作符just()
     */
    public void test03() {

        //创建Observable
        Observable<String> observable = Observable.just("Hello", "RxJava", "jikexuey");

        //创建Observer
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Logger.d("RxJava -->> onCompleted()!");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Logger.d("RxJava -->> onNext()" + s);
            }
        };

        observable.subscribe(observer);

    }


    /**
     * subscribe()不完整回调
     */
    public void test04() {
        //创建Observable
        Observable<String> observable = Observable.just("Hello", "RxJava", "jikexuey");

        //2:不完整回调接口
        Action1 onNextAction = new Action1() {
            @Override
            public void call(Object o) {
                String str = (String) o;
                Logger.d("RxJava:onNextAction:call(Object o):" + str);
            }
        };

        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Logger.d("RxJava:onErrorAction:call(Throwable throwable):" + throwable.getMessage());
            }
        };


        Action0 onCompletedAction = new Action0() {
            @Override
            public void call() {
                Logger.d("RxJava:onCompletedAction:call()");
            }
        };

        observable.subscribe(onNextAction);

    }

    public void textX() {
        String[] array = new String[]{"Hello", "JiKeXueYuan", "RxJava", "World"};
        List<String> list = new ArrayList<>();

        //RxJava
        Observable.from(array).subscribe((s) ->
                list.add(s)
        );

        //Java
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }

    }



    /**
     * Map()变换操作符
     */
    public void text05() {
        Integer[] array = new Integer[]{1, 2, 3, 4};

        Observable.from(array)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return integer.toString();
                    }
                }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("RxJava", "Map()-->>>" + s);
            }
        });

    }

    /**
     * FlatMap()操作符
     */
    public void test06() {
        Observable.from(DataUtils.getData())
                .flatMap((Func1<Author, Observable<?>>) author -> {
                    Log.d("RxJava", author.name);
                    return Observable.from(author.Articles);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(article -> {
                    Log.d("RxJava-->>", article.toString());
                });
    }

    /**
     * Schedulers的API
     */
    public void test07() {
        Observable.from(DataUtils.getData2())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d("RxJava", String.valueOf(integer));
                    }
                });

    }


    //城市列表
    String[] array = new String[]{"北京", "天津", "石家庄", "保定"};

    //List集合
    List<SceneryInfo> SceneryInfos = new ArrayList() {
    };

    //城市景点类
    class SceneryInfo {
    }

    //模拟网络请求，返回SceneryInfo
    private SceneryInfo getSceneryInfo(String city) {

        return new SceneryInfo();
    }


    public void s() {
        Observable.from(array)
                .flatMap(new Func1<String, Observable<SceneryInfo>>() {
                    @Override
                    public Observable<SceneryInfo> call(String s) {
                        return Observable.just(getSceneryInfo(s));
                    }
                })
                .subscribeOn(Schedulers.io())//网络请求在IO线程执行
                .observeOn(AndroidSchedulers.mainThread()).//更新ui在主线程执行
                subscribe(new Subscriber<SceneryInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(SceneryInfo weatherInfo) {
                //添加景点数据
                SceneryInfos.add(weatherInfo);
            }
        });
    }

}
