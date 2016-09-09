package keye.com.rxjavaobserver.addImagethread;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;

import keye.com.rxjavaobserver.MainActivity;

/**
 * Created by Administrator on 2016-09-09.
 */
public class AddImageThread {
    private ImageCollectorView imageCollectorView;

    public void addImage(final File[] folders, final Context context) {

        new Thread() {
            @Override
            public void run() {
                super.run();
                for (File folder : folders) {
                    File[] files = folder.listFiles();
                    for (File file : files) {
                        if (file.getName().endsWith(".png")) {
                            final Bitmap bitmap = getBitmapFromFile(file);
                            ((MainActivity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    imageCollectorView.addImage(bitmap);
                                }
                            });
                        }
                    }
                }
            }
        }.start();

    }

    private Bitmap getBitmapFromFile(File file) {
        //获取图片代码
        return null;
    }

    class ImageCollectorView {
        public void addImage(Bitmap bitmap) {
        }
    }

}
