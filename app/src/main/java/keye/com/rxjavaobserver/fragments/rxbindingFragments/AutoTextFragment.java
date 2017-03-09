package keye.com.rxjavaobserver.fragments.rxbindingFragments;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import keye.com.rxjavaobserver.R;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class AutoTextFragment extends Fragment {
    private static AutoTextFragment fragment;
    private View rootView;
    private EditText inputET;
    private ListView autoLV;
    private List<String> list;
    private ArrayAdapter<String> adapter;

    public AutoTextFragment() {
        // Required empty public constructor
    }

    public static AutoTextFragment getInstance() {
        if (fragment == null)
            fragment = new AutoTextFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_auto_text, container, false);
            inputET = (EditText) rootView.findViewById(R.id.input_edit);
            autoLV = (ListView) rootView.findViewById(R.id.auto_lv);
        }

        initListener();

        return rootView;
    }

    //初始化监听器
    private void initListener() {
        RxTextView.textChangeEvents(inputET).debounce(400, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .switchMap(new Func1<TextViewTextChangeEvent, Observable<List<String>>>() {
                    @Override
                    public Observable<List<String>> call(TextViewTextChangeEvent event) {

                        return getDataFromNet(event.text().toString().trim()).subscribeOn(Schedulers.io());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> dataList) {
                        initadapter(dataList);
                    }
                });
    }

    private Observable<List<String>> getDataFromNet(String changeText) {

        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                //模拟从网络获取数据
                SystemClock.sleep(1000);
                List<String> dataList = new ArrayList<>();
                if (!changeText.equals("")) {
                    for (int i = 0; i < 10; i++) {
                        dataList.add("auto_text:" + changeText + i);
                    }
                }
                subscriber.onNext(dataList);
                subscriber.onCompleted();
            }
        });
    }

    //设置适配器，更新数据
    private void initadapter(List<String> tempList) {
        if (adapter == null) {
            list = new ArrayList<>();
            adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
            autoLV.setAdapter(adapter);
        }
        list.clear();
        list.addAll(tempList);
        adapter.notifyDataSetChanged();

    }

}
