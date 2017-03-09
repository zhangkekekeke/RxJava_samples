package keye.com.rxjavaobserver.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import keye.com.rxjavaobserver.R;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;

public class RxAndroidActivity extends AppCompatActivity {
    public static final String TAG = "RxAndroid";
    private Button button;
    private TextView textView;
    private Looper myHandlerTHreadLooper;
    private Subscription subscription;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_android);
        initView();//初始化控件

        MyHanlderThread myHanlderThread = new MyHanlderThread(TAG);//子线程
        myHanlderThread.start();

        myHandlerTHreadLooper = myHanlderThread.getLooper();//获取子线程looper

    }

    //初始化控件
    private void initView() {
        button = (Button) findViewById(R.id.rxandroid_btn);
        textView = (TextView) findViewById(R.id.rx_tv);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });
    }


    private StringBuffer stringBuffer = new StringBuffer();

    private void sendData() {
        subscription = initObservable()
                .subscribeOn(AndroidSchedulers.from(myHandlerTHreadLooper))//设置事件源线程
                .doOnSubscribe(new Action0() {//在订阅后，事件发射前，执行一些代码
                    @Override
                    public void call() {
                        handler.sendEmptyMessage(0);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//设置订阅者所在线程
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        stringBuffer.append(s + " ");//获取到数据后，添加入stringBuffer中
                        textView.setText(stringBuffer.toString());
                    }
                });

    }

    //模拟耗时操作，创建并返回被观察者
    private rx.Observable<String> initObservable() {
        return rx.Observable.defer(new Func0<rx.Observable<String>>() {
            @Override
            public rx.Observable<String> call() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return rx.Observable.just("one", "two", "three", "four", "five");
            }
        });
    }

    //自定义子线程
    class MyHanlderThread extends HandlerThread {
        public MyHanlderThread(String name) {
            super(name);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放subscription对象
        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
    }
}
