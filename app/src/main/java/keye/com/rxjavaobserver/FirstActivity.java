package keye.com.rxjavaobserver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import keye.com.rxjavaobserver.activity.PermissionActivity;
import keye.com.rxjavaobserver.activity.RetrofitActivity;
import keye.com.rxjavaobserver.activity.RxAndroidActivity;
import keye.com.rxjavaobserver.activity.RxBindingActivity;
import keye.com.rxjavaobserver.activity.RxPermissionActivity;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_rxandroid:
                startActivity(new Intent(this, RxAndroidActivity.class));
                break;
            case R.id.btn_permision6:
                startActivity(new Intent(this, PermissionActivity.class));
                break;
            case R.id.btn_rxpermision:
                startActivity(new Intent(this, RxPermissionActivity.class));
                break ;
            case R.id.btn_rxbindding:
                startActivity(new Intent(this, RxBindingActivity.class));
                break;
            case R.id.btn_retrofit:
                startActivity(new Intent(this, RetrofitActivity.class));
                break;
            case R.id.btn_main:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

}
