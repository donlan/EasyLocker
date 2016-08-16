package dong.lan.lock.lockAction.intentAction;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import dong.lan.lock.BasePresenter;
import dong.lan.lock.Global;
import dong.lan.lock.R;
import dong.lan.lock.lockAction.intentAction.model.App;

/**
 * Created by 梁桂栋 on 2016年08月15日 16:22.
 * Email:760625325@qq.com
 * github:gitbub.com/donlan
 */
public class IntentAppPresenter implements BasePresenter {
    private Context context;
    private LockIntentAppsFragment fragment;

    private List<App> apps = new ArrayList<>();
    private DataHelper dataHelper;

    public IntentAppPresenter(Context context) {
        this.context = context;
        dataHelper = new DataHelper(context);
    }

    @Override
    public void start() {
        loadAllInstalledApp();
    }

    @Override
    public void bindView(Fragment view) {
        this.fragment = (LockIntentAppsFragment) view;
    }

    public void onDestroy() {
        dataHelper.close();
    }

    public List<App> getApps() {
        if (apps.size() == 0)
            loadAllInstalledApp();
        return apps;
    }


    public List<App> getIntentApps() {
        Global.initIntentApps(context);
        return Global.intentApps;
    }

    //获取已经设置了锁屏跳转的应用列表

    //加载手机中安装的应用
    private void loadAllInstalledApp() {
        if (apps.size() > 1)
            apps.clear();
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packageInfos = pm.getInstalledPackages(PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
        for (PackageInfo info :
                packageInfos) {

            if (pm.getLaunchIntentForPackage(info.packageName) == null) {
                continue;
            }
            Drawable drawable = pm.getApplicationIcon(info.applicationInfo);
            if (drawable != null) {
                App app = new App(info.packageName, null);
                app.setIcon(drawable);
                app.setIntentPattern(pm.getApplicationLabel(info.applicationInfo).toString());
                apps.add(app);

            }
        }
    }


    //设置一个应用的锁屏适配模型
    public void setAppIntentPatter(final App app) {
        final View v = LayoutInflater.from(context).inflate(R.layout.base_dialog, null);
        new AlertDialog.Builder(context)
                .setView(v)
                .setTitle("设置跳转适配模型")
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et = (EditText) v.findViewById(R.id.base_et);
                        String string = et.getText().toString();
                        if (TextUtils.isEmpty(string)) {
                            fragment.Show("匹配模型不能为空");
                            return;
                        }
                        try {
                            Integer.parseInt(string);
                        } catch (Exception e) {
                            fragment.Show("只能是０－９的数字组合");
                            return;
                        }
                        SQLiteDatabase db = dataHelper.getWritableDatabase();
                        if (!db.isOpen())
                            return;
                        ContentValues cv = new ContentValues();
                        if (Global.intentApps.contains(app)) {
                            cv.put("pattern", string);
                            db.update("intent_app", cv, "packageName = ? ", new String[]{app.getPkgName()});
                            App a = Global.getAppByPkn(app.getPkgName());
                            if (a != null)
                                a.setIntentPattern(string);
                        } else {
                            if(Global.getAppByPattern(string)!=null) {
                                fragment.Show("此匹配模型已经被适配");
                                return;
                            }
                            cv.put("packageName", app.getPkgName());
                            cv.put("pattern", string);
                            db.insert("intent_app", null, cv);
                            Global.intentApps.add(app);
                        }
                        app.setIntentPattern(string);
                        refreshIntentApps();
                    }
                }).show();
    }

    public void refreshIntentApps() {
        if(fragment.emptyView!=null && fragment.emptyView.getVisibility() == View.VISIBLE)
            fragment.emptyView.setVisibility(View.GONE);
        if (fragment.appAdapter == null) {
            fragment.appAdapter = new IntentAppAdapter(Global.intentApps);
            fragment.appAdapter.setOnAppLOngClickListener(new IntentAppAdapter.OnAppLongClickListener() {
                @Override
                public void onAppLongClick(App app) {
                    deleteIntentApp(app);
                }
            });
            fragment.appsList.setAdapter(fragment.appAdapter);
        }
        fragment.appAdapter.notifyDataSetChanged();
    }

    //删除已经设置了锁屏跳转的应用
    public void deleteIntentApp(App app) {
        SQLiteDatabase db = dataHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete("intent_app", "packageName = ? ", new String[]{app.getPkgName()});
            Global.intentApps.remove(app);
            refreshIntentApps();
        }
    }
}
