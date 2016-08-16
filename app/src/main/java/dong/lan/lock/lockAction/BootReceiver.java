package dong.lan.lock.lockAction;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import dong.lan.lock.BuildConfig;
import dong.lan.lock.LockService;
import dong.lan.lock.utils.Config;

/**
 * Created by 梁桂栋 on 2016年08月07日 22:17.
 * Email:760625325@qq.com
 * desc:接收开机广播,开启锁屏服务
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            if(Config.getBootOnEnable(context)) {
                LockService.KILL = false;
                context.startService(new Intent(context, LockService.class));
                if (BuildConfig.DEBUG) Log.d("BootReceiver", "BOOT SERVICE");
            }
        }
    }
}
