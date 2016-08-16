
package dong.lan.lock.lockAction;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.WindowManager;

import dong.lan.lock.BaseActivity;
import dong.lan.lock.Global;
import dong.lan.lock.R;
import dong.lan.lock.lockConfig.SettingPresenter;

/*
 * 锁频页面
 */

public class LockActivity extends BaseActivity implements SettingPresenter.ConfigChange, LockViewManager.LockStatusListener {

    private LockViewManager viewManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        super.onCreate(savedInstanceState);

        Global.initIntentApps(this);
        setupView(R.layout.activity_lock);
        viewManager = LockViewManager.getInstance(LockActivity.this);
        viewManager.setLockStatusListener(this);
        viewManager.setLockView(LayoutInflater.from(LockActivity.this).inflate(R.layout.view_lock,null));
        viewManager.updateActivity(LockActivity.this);

        SettingPresenter.addConfigChangeListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            viewManager.Lock();
        } catch (NoLockStatusListenerException e) {
            e.printStackTrace();
            Show(e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        viewManager.updateActivity(LockActivity.this);
        overridePendingTransition(R.anim.zoom_enter,R.anim.zoom_exit);
        super.onDestroy();

    }

    //设置页面设置密码模型的回调，保证下次锁频时使用的时最新的密码模型
    @Override
    public void onPatterChange(String patter) {
        viewManager.updatePatter(patter);
    }

    @Override
    public void onLockItemColorChange(int color) {
        viewManager.updateLockItemColor(color);
    }

    @Override
    public void onLockTextColorChange(int color) {
        viewManager.updateLockTextColor(color);
    }

    //锁频页面屏蔽返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int key = event.getKeyCode();
        return key == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event);
    }

    @Override
    public void onLocked() {

    }

    @Override
    public void onUnlock() {
        finish();
    }
}
