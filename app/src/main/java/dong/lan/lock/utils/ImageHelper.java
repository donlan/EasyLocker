/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package dong.lan.lock.utils;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;

import dong.lan.lock.BuildConfig;

/**
 * Created by 梁桂栋 on 2016年08月11日 13:11.
 * Email:760625325@qq.com
 * github:gitbub.com/donlan
 * desc:
 */
public final class ImageHelper {
    /*
    缩放图片
     */
    public static Bitmap scaleImage(String imgPath, int toWidth, int toHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        if (BuildConfig.DEBUG) Log.d("ImageHelper", imgPath);
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options);
        int w = options.outWidth;
        int h = options.outHeight;
        w = w / toWidth;
        h = h / toHeight;
        int s = Math.max(w, h);
        if (s < 1)
            s = 1;
        options.inSampleSize = s;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imgPath, options);
    }

    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }
}
