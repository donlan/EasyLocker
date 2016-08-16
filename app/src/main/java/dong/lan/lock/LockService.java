package dong.lan.lock;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.IBinder;
import android.support.annotation.Nullable;

import dong.lan.lock.lockAction.LockActivity;

/**
 * Created by 梁桂栋 on 2016年08月07日 21:55.
 * Email:760625325@qq.com
 * desc:锁屏开关的后台服务，主要是接受屏幕关闭的广播，以及服务被清除后自动重启服务
 */
public class LockService extends Service {
    public static boolean running = false;
    public static boolean KILL = false;
    private Intent lockIntent = null;

    @Override
    public void onCreate() {
        super.onCreate();
        running = true;
        lockIntent = new Intent(LockService.this, LockActivity.class);
        //因为Calling startActivity() from outside of an Activity  context requires the FLAG_ACTIVITY_NEW_TASK flag
        lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        registerComponentCallbacks(new ComponentCallbacks() {
            @Override
            public void onConfigurationChanged(Configuration newConfig) {

            }

            @Override
            public void onLowMemory() {

            }
        });

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        running = true;
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterComponentCallbacks(new ComponentCallbacks() {
            @Override
            public void onConfigurationChanged(Configuration newConfig) {

            }

            @Override
            public void onLowMemory() {

            }
        });
        //被销毁时启动自身，保持自身在后台存活
        running = false;
        if (!KILL)
            startService(new Intent(LockService.this, LockService.class));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private BroadcastReceiver screenOffOnReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
                startActivity(lockIntent);
            }
        }
    };

    @Override
    public void registerComponentCallbacks(ComponentCallbacks callback) {
        IntentFilter mScreenOnOrOffFilter = new IntentFilter();
        mScreenOnOrOffFilter.addAction("android.intent.action.SCREEN_OFF");
        LockService.this.registerReceiver(screenOffOnReceiver, mScreenOnOrOffFilter);
    }

    @Override
    public void unregisterComponentCallbacks(ComponentCallbacks callback) {
        if (screenOffOnReceiver != null) {
            LockService.this.unregisterReceiver(screenOffOnReceiver);
        }
    }

}
