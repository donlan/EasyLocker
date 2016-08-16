package dong.lan.lock.lockAction.intentAction;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 梁桂栋 on 2016年08月15日 20:36.
 * Email:760625325@qq.com
 * github:gitbub.com/donlan
 * desc:数据库链接类
 */
public class DataHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "lock_db";
    public static final int VERSION=1;
    public DataHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    private static final String CREATE_INTENT_APP_TABLE="create table intent_app(packageName text primary key,pattern text);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_INTENT_APP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
