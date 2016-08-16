package dong.lan.lock.lockConfig;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.io.File;

import dong.lan.lock.BaseActivity;
import dong.lan.lock.BuildConfig;
import dong.lan.lock.LockService;
import dong.lan.lock.R;
import dong.lan.lock.lockAction.intentAction.LockToAppActivity;
import dong.lan.lock.utils.ActionCallback;
import dong.lan.lock.utils.Config;

/**
 * Created by 梁桂栋 on 2016年08月07日 21:55.
 * Email:760625325@qq.com
 * desc:锁频设置页面
 */
public class MainActivity extends BaseActivity {
    private CheckBox lockSwitcher;
    private TextView switcherInfo;
    private CheckBox bootOnEnable;
    private Button bgBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView(R.layout.activity_main);


        final SettingPresenter settingPresenter = new SettingPresenter(this);
        lockSwitcher = (CheckBox) findView(R.id.lock_switcher);
        bootOnEnable = (CheckBox) findView(R.id.set_lock_onstart);
        switcherInfo = (TextView) findView(R.id.lock_switcher_info);
        bgBtn = (Button) findView(R.id.set_lock_bg);

        lockSwitcher.setChecked(LockService.running);

        switcherInfo.setText(getResources().getString(R.string.current_lock_service_opening, LockService.running ? "开启" : "关闭"));
        bootOnEnable.setChecked(Config.getBootOnEnable(this));
        lockSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settingPresenter.setLockStatus(isChecked);
                switcherInfo.setText(getResources().getString(R.string.current_lock_service_opening,
                        isChecked ? "开启" : "关闭"));
            }
        });
        bootOnEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Config.setBootOnEnable(MainActivity.this, isChecked);
            }
        });

        bgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingPresenter.showBgChoice();
            }
        });

        findView(R.id.set_lock_top_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingPresenter.setTopText();
            }
        });
        findView(R.id.set_lock_patter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingPresenter.setLockPatter();
            }
        });
        findView(R.id.set_lock_item_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingPresenter.setLockItemColor();
            }
        });
        findView(R.id.set_lock_text_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingPresenter.setLockTextColor();
            }
        });
        findView(R.id.set_lock_intent_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LockToAppActivity.class));
            }
        });
        settingPresenter.start();
    }

    /*
    统一将相册或者调用相机获取的图片的绝对路径保存
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == 2) {
            Uri uri = data.getData();
            String[] filePathColumns = new String[]{MediaStore.Images.Media.DATA};
            Cursor c = this.getContentResolver().query(uri, filePathColumns, null, null, null);
            if (c == null)
                return;
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String path = c.getString(columnIndex);
            c.close();
            Config.setLockBg(this, path);
        } else if (requestCode == 3) {
            Config.setLockBg(this, Environment.getExternalStorageDirectory() + "/lock_bg.jpg");
        }
    }
}
