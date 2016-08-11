package dong.lan.lock.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

/**
 * Created by 梁桂栋 on 2016年08月07日 21:55.
 * Email:760625325@qq.com
 * desc:　sharedPrefences的数据封装
 */
public class Config {

    public static final String SP_NAME ="LOCK";
    public static final String SP_BOOT_ON_ENABLE = "boot_on_enable";
    public static final String SP_LOCK_BG="lock_bg";
    public static final String SP_TOP_TEXT="top_text";
    public static final String SP_LOCK_PATTER = "lock_patter";
    public static final String SP_LOCK_ITEM_COLOR ="lock_item_color";
    public static final String SP_LOCK_TEXT_COLOR ="lock_text_color";


    private volatile static SharedPreferences sp;

    public static SharedPreferences getSP(Context context) {
        if (sp == null) {
            synchronized (Config.class) {
                sp = context.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
            }
        }
        return sp;
    }

    public static boolean isServiceRunning(Context context) {
        return getSP(context).getBoolean("SERVICE_RUNNING", false);
    }

    public static boolean canKillService(Context context) {
        return getSP(context).getBoolean("SERVICE_KILL", false);
    }

    public static void updateServiceRunning(Context context, boolean run) {
        getSP(context).edit().putBoolean("SERVICE_RUNNING", run).apply();
    }

    public static void updateServiceKill(Context context, boolean kill) {
        getSP(context).edit().putBoolean("SERVICE_KILL", kill).apply();
    }

    public static void setBootOnEnable(Context context, boolean enable) {
        getSP(context).edit().putBoolean(SP_BOOT_ON_ENABLE, enable).apply();
    }

    public static boolean getBootOnEnable(Context context) {
        return getSP(context).getBoolean(SP_BOOT_ON_ENABLE, false);
    }

    public static void setLockBg(Context context, String lockBg) {
        getSP(context).edit().putString(SP_LOCK_BG, lockBg).apply();
    }

    public static Uri getLockBg(Context context) {
        return Uri.parse(getSP(context).getString(SP_LOCK_BG, ""));
    }

    public static void setLockTopText(Context context, String topText) {
        getSP(context).edit().putString(SP_TOP_TEXT, topText).apply();
    }

    public static String getLockTopText(Context context) {
        return getSP(context).getString(SP_TOP_TEXT, "");
    }


    public static void setString(Context context, String key, String str) {
        getSP(context).edit().putString(key, str).apply();
    }

    public static String getString(Context context, String key) {
        return getSP(context).getString(key, "");
    }

    public static void setBoolean(Context context, String key, boolean b) {
        getSP(context).edit().putBoolean(key, b).apply();
    }

    public static boolean getBoolean(Context context, String key) {
        return getSP(context).getBoolean(key, false);
    }

    public static void setInt(Context context, String key, int i) {
        getSP(context).edit().putInt(key, i).apply();
    }

    public static int getInt(Context context, String key) {
        return getSP(context).getInt(key, 0);
    }

}
