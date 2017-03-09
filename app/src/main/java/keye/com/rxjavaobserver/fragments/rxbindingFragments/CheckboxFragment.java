package keye.com.rxjavaobserver.fragments.rxbindingFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.jakewharton.rxbinding.widget.RxCompoundButton;

import keye.com.rxjavaobserver.R;
import rx.functions.Action1;

public class CheckboxFragment extends Fragment {
    private static CheckboxFragment fragment;
    private CheckBox signCB;
    private Button signBtn;
    private View rootView;

    public CheckboxFragment() {
        // Required empty public constructor
    }

    public static CheckboxFragment getInstance() {
        if (fragment == null) {
            fragment = new CheckboxFragment();
        }
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
            rootView = inflater.inflate(R.layout.fragment_checkbox, container, false);
            signCB = (CheckBox) rootView.findViewById(R.id.sign_checkbox);
            signBtn = (Button) rootView.findViewById(R.id.sign_btn);
        }
        //初始化监听器
        initListener();
        return rootView;
    }

    private void initListener() {
        //选中事件监听
        RxCompoundButton.checkedChanges(signCB).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                signBtn.setEnabled(aBoolean);
                signBtn.setBackgroundResource(aBoolean ? R.color.colorAccent : R.color.gray);
            }
        });
    }

}
