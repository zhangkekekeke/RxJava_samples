package keye.com.rxjavaobserver.bean;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016-11-13.
 */
public class ItemData {
    private String url;
    private String name;
    private String date;
    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public ItemData(String url, String name, String date) {
        this.url = url;
        this.name = name;
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
