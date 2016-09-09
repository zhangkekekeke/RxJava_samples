package keye.com.rxjavaobserver.addImagethread;

import android.graphics.Bitmap;

import java.io.File;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016-09-09.
 */
public class RxAddImage {
    private ImageCollectorView imageCollectorView;
    private File[] folders;

    public void addImage() {

        Observable.from(folders)
                .flatMap(new Func1<File, Observable<File>>() {
                    @Override
                    public Observable<File> call(File file) {
                        return Observable.from(file.listFiles());
                    }
                })
                .filter(new Func1<File, Boolean>() {
                    @Override
                    public Boolean call(File file) {
                        return file.getName().endsWith(".png");
                    }
                })
                .map(new Func1<File, Bitmap>() {
                    @Override
                    public Bitmap call(File file) {
                        return getBitmapFromFile(file);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        imageCollectorView.addImage(bitmap);
                    }
                });
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
