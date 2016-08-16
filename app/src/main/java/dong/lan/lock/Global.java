package dong.lan.lock;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dong.lan.lock.lockAction.intentAction.DataHelper;
import dong.lan.lock.lockAction.intentAction.model.App;

/**
 * Created by 梁桂栋 on 2016年08月16日 15:34.
 * Email:760625325@qq.com
 * github:gitbub.com/donlan
 * desc:
 */
public final class Global {
    public static final int FLAG_GET = 0;
    public static final int FLAG_INTENT_GET = 1;
    public static final int FLAG_INTENT_APP = 2;
    public static final int FLAG_GET_UNLOCK = 3;

    Global() {
    }

    public static void initIntentApps(Context context) {
        if(intentApps.size()>0)
            return;
        DataHelper dataHelper = new DataHelper(context);
        SQLiteDatabase db = dataHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor c = db.rawQuery("select * from intent_app", null);
            if (!c.moveToFirst())
                return;
            PackageManager pm = context.getPackageManager();
            do {
                String pkn = c.getString(0);
                String pattern = c.getString(1);
                App app = new App(pkn, pattern);
                try {
                    app.setIcon(pm.getApplicationIcon(pkn));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                intentApps.add(app);
            } while (c.moveToNext());
            c.close();
            dataHelper.close();
            dataHelper = null;
        }
    }

    public static List<App> intentApps = new ArrayList<>();

    //根据包名从已设置锁屏跳转的列表中查找该App
    public static App getAppByPkn(String pkn) {
        for (App a :
                Global.intentApps) {
            if (a.getPkgName().equals(pkn))
                return a;
        }
        return null;
    }


    //根据匹配模型寻找并返回匹配的应用
    public static App getAppByPattern(String pattern) {
        for (App a :
                Global.intentApps) {
            if (a.getIntentPattern().equals(pattern))
                return a;
        }
        return null;
    }
}
