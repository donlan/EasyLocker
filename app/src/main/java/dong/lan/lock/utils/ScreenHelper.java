/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package dong.lan.lock.utils;

import android.app.Activity;
import android.graphics.Point;
import android.view.WindowManager;

/**
 * Created by 梁桂栋 on 2016年08月11日 13:17.
 * Email:760625325@qq.com
 * github:gitbub.com/donlan
 * desc:
 */
public final class ScreenHelper {
    /*
    获取屏幕的宽高
     */
    public static void getScreenPixel(Activity context,Point point){
        WindowManager wm = context.getWindowManager();
        wm.getDefaultDisplay().getSize(point);
    }
}
