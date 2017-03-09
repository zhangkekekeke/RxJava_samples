package keye.com.rxjavaobserver.addImagethread;

import android.graphics.Bitmap;

import java.io.File;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016-09-09.
 */
public class RxAddImage {
    private ImageListAdatper imageListAdatper;
    private File[] routes;
    private List imageList;

    public void updataImage() {
        Observable.from(routes)
                .flatMap(file -> Observable.from(file.listFiles()))
                .filter(file -> file.getName().endsWith(".jpg"))
                .map(file -> getBitmap(file))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> {
                    imageList.add(bitmap);
                    imageListAdatper.notifyDataSetChanged();
                });
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
