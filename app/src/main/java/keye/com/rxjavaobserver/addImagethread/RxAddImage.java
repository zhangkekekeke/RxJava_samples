package keye.com.rxjavaobserver.addImagethread;

import android.graphics.Bitmap;

import java.io.File;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
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
                .flatMap(new Func1<File, Observable<File>>() {
                    @Override
                    public Observable<File> call(File file) {
                        return Observable.from(file.listFiles());
                    }
                })
                .filter(new Func1<File, Boolean>() {
                    @Override
                    public Boolean call(File file) {
                        return file.getName().endsWith(".jpg");
                    }
                })
                .map(new Func1<File, Bitmap>() {
                    @Override
                    public Bitmap call(File file) {
                        return getBitmap(file);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        imageList.add(bitmap);
                        imageListAdatper.notifyDataSetChanged();
                    }
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
