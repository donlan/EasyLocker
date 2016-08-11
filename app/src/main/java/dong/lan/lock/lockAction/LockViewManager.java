package dong.lan.lock.lockAction;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.Date;

import dong.lan.lock.BuildConfig;
import dong.lan.lock.R;
import dong.lan.lock.utils.Config;
import dong.lan.lock.utils.ImageHelper;
import dong.lan.lock.utils.ScreenHelper;

/**
 * Created by 梁桂栋 on 2016年08月07日 22:02.
 * Email:760625325@qq.com
 * desc:锁屏页面
 */
public class LockViewManager {

    private String key = "";
    public int itemID[] = {R.id.lock_zero, R.id.lock_one, R.id.lock_two, R.id.lock_three,
            R.id.lock_four, R.id.lock_five, R.id.lock_six, R.id.lock_seven,
            R.id.lock_eight, R.id.lock_nine};
    public TextView checkBox[] = new TextView[10];


    private Activity activity;
    private TextView time;
    private TextClock clock;
    private ImageView background;
    private static View lockView;
    private WindowManager windowManager = null;
    public static LockViewManager manager = null;
    private static WindowManager.LayoutParams layoutParams;
    public static Vibrator vibrator;
    private volatile boolean isLock = false;

    private String patter;
    private int lockItemColor;
    private int textColor;

    public static synchronized LockViewManager getInstance(Activity activity) {
        if (manager == null)
            manager = new LockViewManager(activity);
        return manager;
    }

    public LockViewManager(Activity activity) {
        this.activity = activity;
        vibrator = (Vibrator) activity.getApplication().getApplicationContext()
                .getSystemService(Context.VIBRATOR_SERVICE);
        date = new Date();
        patter = Config.getString(activity, Config.SP_LOCK_PATTER);
        lockItemColor = Config.getInt(activity, Config.SP_LOCK_ITEM_COLOR);
        textColor = Config.getInt(activity, Config.SP_LOCK_TEXT_COLOR);
        init();
    }

    public synchronized void updateActivity(Activity ac) {
        activity = ac;
    }

    public void updatePatter(String patter) {
        this.patter = patter;
    }

    public void updateLockItemColor(int color) {
        lockItemColor = color;
    }

    public void updateLockTextColor(int color) {
        textColor = color;
    }

    public void updateConfig() {
        patter = Config.getString(activity, Config.SP_LOCK_PATTER);
        lockItemColor = Config.getInt(activity, Config.SP_LOCK_ITEM_COLOR);
        textColor = Config.getInt(activity, Config.SP_LOCK_TEXT_COLOR);
    }

    private void setLockBg() {
        Uri uri = Config.getLockBg(activity);
        String path = uri.getPath();
        Bitmap bitmap = null;
        Point point = new Point();
        ScreenHelper.getScreenPixel(activity, point);
        bitmap = ImageHelper.scaleImage(path, point.x, point.y);
        if (bitmap != null)
            background.setImageBitmap(bitmap);
    }

    public void setLockView(View v) {
        lockView = v;
        clock = (TextClock) lockView.findViewById(R.id.timeShow);
        time = (EditText) lockView.findViewById(R.id.time);
        background = (ImageView) lockView.findViewById(R.id.image_bg);
        TextView topText = (TextView) lockView.findViewById(R.id.top_text);
        Button resetBtn = (Button) lockView.findViewById(R.id.lock_reset);
        resetBtn.setOnClickListener(l);
        setLockItemBackground(resetBtn);
        Button funBtn = (Button) lockView.findViewById(R.id.lock_);
        funBtn.setOnClickListener(l);
        setLockItemBackground(funBtn);
        setLockBg();
        String topStr = Config.getLockTopText(activity);
        topText.setText(topStr.equals("") ? "墨梅无锋" : topStr);

        clock.setTextColor(textColor);
        topText.setTextColor(textColor);
        time.setTextColor(textColor);
        resetBtn.setTextColor(textColor);
        funBtn.setTextColor(textColor);

        for (int i = 0; i < 10; i++) {
            checkBox[i] = (TextView) lockView.findViewById(itemID[i]);
            checkBox[i].setOnClickListener(l);
            setLockItemBackground(checkBox[i]);
            checkBox[i].setTextColor(textColor);
        }
    }

    private void setLockItemBackground(View item) {
        Drawable drawable = null;
        if (Build.VERSION.SDK_INT >= 21)
            drawable = ImageHelper.tintDrawable(activity.getDrawable(R.drawable.lock), ColorStateList.valueOf(lockItemColor));
        else
            drawable = ImageHelper.tintDrawable(activity.getResources().getDrawable(R.drawable.lock), ColorStateList.valueOf(lockItemColor));
        if (Build.VERSION.SDK_INT >= 16)
            item.setBackground(drawable);
        else
            item.setBackgroundDrawable(drawable);
    }

    //初始化锁屏view的布局配置
    private void init() {
        isLock = false;
        windowManager = activity.getWindowManager();
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        layoutParams.flags = 1280;
    }

    public synchronized void Lock() {
        if (lockView != null && !isLock) {
            if (isLock)
                windowManager.updateViewLayout(lockView, layoutParams);
            else
                windowManager.addView(lockView, layoutParams);
            isLock = true;
        }
    }

    public synchronized void unLock() {
        if (windowManager != null && isLock) {
            windowManager.removeView(lockView);
            isLock = false;
        }
    }

    Date date;

    //将当前时间以字符串返回
    private String getKey(boolean isShow) {
        String time = "";
        date.setTime(System.currentTimeMillis());

        if (date.getHours() < 10 && !isShow)
            time += "0" + date.getHours();
        else
            time += date.getHours();

        if (isShow)
            time += " : ";

        if (date.getMinutes() < 10)
            time += "0" + date.getMinutes();
        else
            time += date.getMinutes();
        return time;
    }

    //检查输入的密码是否匹配
    private void Check(String s) {
        key += s;
        time.append(s);

        if (patter.equals("")) {
            if (key.length() == 4) {
                if (key.equals(getKey(false))) {
                    activity.finish();
                    unLock();
                } else {
                    vibrator.vibrate(350);
                    show("密码不对　：）");
                }
                time.setText("");
                key = "";
            }
        } else if (key.length() == patter.length()) {
            String keyStr = getKey(false);
            char[] k = new char[4];
            for (int i = 0; i < 4; i++)
                k[i] = keyStr.charAt(i);
            String code = "";
            for (int i = 0; i < patter.length(); i++) {
                char c = patter.charAt(i);
                code += k[c - 65];
            }
            if (code.equals(key)) {
                activity.finish();
                unLock();
            } else {
                vibrator.vibrate(350);
                show("密码不对　：）");
            }
            if (BuildConfig.DEBUG) Log.d("LockViewManager", code + "," + key);
            time.setText("");
            key = "";

        }
    }

    private void anim(View target) {
        vibrator.vibrate(50);
        ObjectAnimator.ofFloat(target, "scaleX", 1f, 0.8f, 1f).setDuration(250).start();
        ObjectAnimator.ofFloat(target, "scaleY", 1f, 0.8f, 1f).setDuration(250).start();
    }

    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.lock_reset) {
                time.setText("");
                key = "";
            } else if (id == R.id.lock_) {
                //// TODO: 16-8-9  
            } else {
                if (vibrator.hasVibrator()) {
                    vibrator.cancel();
                }
                vibrator.vibrate(20);
                TextView textView = (TextView) v;
                Check(textView.getText().toString());
            }
        }
    };


    public void show(String str) {
        Snackbar.make(lockView, str, 1000).show();
    }

}
