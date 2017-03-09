package keye.com.rxjavaobserver.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import keye.com.rxjavaobserver.R;

public class PermissionActivity extends AppCompatActivity {

    private static final String TAG = "RxPermissionsSample";
    private static final int CAMERACODE = 100;
    private Camera camera;
    private SurfaceView surfaceView;
    private Button enableCameraBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_permission);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        enableCameraBtn = (Button) findViewById(R.id.enableCamera);

        enableCameraBtn.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    //权限被拒绝过一次，显示权限说明
                    //没有授权，请申请权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERACODE);
                } else {
                    //没有授权，请申请权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERACODE);
                }


            } else {
                //拥有权限，启动相机
                releaseCamera();
                camera = Camera.open(0);
                try {
                    camera.setPreviewDisplay(surfaceView.getHolder());
                    camera.startPreview();
                } catch (IOException e) {
                    Log.e(TAG, "Error while trying to display the camera preview", e);
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERACODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //成功获取权限
                    releaseCamera();
                    camera = Camera.open(0);
                    try {
                        camera.setPreviewDisplay(surfaceView.getHolder());
                        camera.startPreview();
                    } catch (IOException e) {
                        Log.e(TAG, "Error while trying to display the camera preview", e);
                    }
                } else {
                    //权限被拒绝了
                    Toast.makeText(this, getResources().getString(R.string.permisionyNo), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseCamera();
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }
}
