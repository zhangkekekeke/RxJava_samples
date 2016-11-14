package keye.com.rxjavaobserver.addImagethread;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.util.List;

import keye.com.rxjavaobserver.MainActivity;

/**
 * Created by Administrator on 2016-01-09.
 */
public class AddImage {
    private ImageListAdatper imageListAdatper;
    private List imageList;


    /**
     * 假设：1.我们有一个Listview，用于显示显示多张图片
     * 2.现在给出一个目录数组，把所有目录中的jpg图片都显示到listview中
     * 3.读取图片是耗时操作，需要在后台运行，listview更新需要在UI线程执行
     * <p>
     * 常规实现方式：
     */
    public void updataImage(final File[] routes, final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (File route : routes) {
                    File[] files = route.listFiles();
                    for (File file : files) {
                        if (file.getName().endsWith(".jpg")) {
                            final Bitmap bitmap = getBitmap(file);
                            ((MainActivity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    imageList.add(bitmap);
                                    imageListAdatper.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                }
            }
        }).start();

    }


    public void updataImageCopy(final File[] routes, final Context context) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (File route : routes) {
                    File[] files = route.listFiles();
                    for (File file : files) {
                        if (file.getName().endsWith(".jpg")) {
                            final Bitmap bitmap = getBitmap(file);
                            ((MainActivity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    imageList.add(bitmap);
                                    imageListAdatper.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                }
            }
        }.start();

    }

    private Bitmap getBitmap(File file) {
        //获取图片代码
        return null;
    }

    class ImageListAdatper {
        public void notifyDataSetChanged() {
        }
    }

}
