package keye.com.rxjavaobserver.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;

import com.jakewharton.rxbinding.support.v7.widget.RxToolbar;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import keye.com.rxjavaobserver.R;
import keye.com.rxjavaobserver.fragments.rxbindingFragments.AutoTextFragment;
import keye.com.rxjavaobserver.fragments.rxbindingFragments.CheckboxFragment;
import keye.com.rxjavaobserver.fragments.rxbindingFragments.SimpleClickFragment;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

public class RxBindingActivity extends RxAppCompatActivity implements RadioGroup.OnCheckedChangeListener {


    private RadioGroup mRadioGroup;
    private Toolbar rxBindingToolbar;
    private BehaviorSubject behaviorSubject = BehaviorSubject.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_binding);
        initToolbar();

        mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);
        mRadioGroup.setOnCheckedChangeListener(this);
    }

    private void initToolbar() {
        rxBindingToolbar = (Toolbar) findViewById(R.id.rxbinding_toolbar);
        setSupportActionBar(rxBindingToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Toolbar的item点击
        RxToolbar.itemClicks(rxBindingToolbar)
                .compose(RxLifecycle.bindUntilEvent(behaviorSubject, ActivityEvent.DESTROY))
                .subscribe((menuItem -> {
            Snackbar.make(rxBindingToolbar, "toolbar " + ((MenuItem) menuItem).getTitle(), Snackbar.LENGTH_SHORT).show();
        }));

        //Toolbar的导航栏点击
        RxToolbar.navigationClicks(rxBindingToolbar).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Snackbar.make(rxBindingToolbar, "toolbar navigationClicks", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rxbinding_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.simple_click_btn:
                setFragment(SimpleClickFragment.getInstance());
                break;
            case R.id.auto_text_btn:
                setFragment(AutoTextFragment.getInstance());
                break;
            case R.id.checkbox_btn:
                setFragment(CheckboxFragment.getInstance());
                break;
        }
    }

    private void setFragment(Fragment instance) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mlayout, instance)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        behaviorSubject.onNext(ActivityEvent.DESTROY);
    }
}
