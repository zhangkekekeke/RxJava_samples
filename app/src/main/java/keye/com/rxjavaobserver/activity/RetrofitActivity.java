package keye.com.rxjavaobserver.activity;

import android.os.Bundle;
import android.util.Log;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.List;

import keye.com.rxjavaobserver.R;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class RetrofitActivity extends RxAppCompatActivity {
    public static final String API_URL = "https://raw.githubusercontent.com/";
    private Retrofit retrofit;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        //初始化Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        //发送请求，返回数据的Observable
        subscription = retrofit.create(GetGithub.class)
                .itemDatas("zhangkekekeke", "RxJavaObserver")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .flatMap(strings -> Observable.from(strings))
                .subscribe(s -> {
                    Log.d("RxJava_R", s);
                });
    }

    //定义请求接口
    public interface GetGithub {
        @GET("/{owner}/{txt}/master/jsondata")
        Observable<List<String>> itemDatas(
                @Path("owner") String owner,
                @Path("txt") String repo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消订阅~
        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
    }
}
