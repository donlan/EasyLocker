package dong.lan.lock.lockConfig;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dong.lan.lock.BaseActivity;
import dong.lan.lock.BasePresenter;
import dong.lan.lock.LockService;
import dong.lan.lock.R;
import dong.lan.lock.utils.ActionCallback1;
import dong.lan.lock.utils.ColorChoose;
import dong.lan.lock.utils.Config;

/**
 * Created by 梁桂栋 on 2016年08月10日 10:57.
 * Email:760625325@qq.com
 * github:gitbub.com/donlan
 * desc:
 */
public class SettingPresenter implements BasePresenter {
    private Activity ac;
    Intent serviceIntent;

    @Override
    public void start() {
        SharedPreferences sp = Config.getSP(ac);
        serviceIntent = new Intent(ac, LockService.class);
    }

    @Override
    public void bindView(Fragment view) {

    }

    public SettingPresenter(Activity ac) {
        this.ac = ac;
    }

    //更新锁屏的状态
    public void setLockStatus(boolean isChecked) {
        if (isChecked && !LockService.running) {
            LockService.KILL = false;
            ac.startService(serviceIntent);
        } else if (!isChecked && LockService.running) {
            LockService.KILL = true;
            ac.stopService(serviceIntent);
        }
    }
    //背景图片设置提示框
    public void showBgChoice() {
        new AlertDialog.Builder(ac).setMessage("从哪里获取一张图片")
                .setPositiveButton("相机", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File outImgFile = new File(Environment.getExternalStorageDirectory(), "lock_bg.jpg");
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outImgFile));
                        cameraIntent.putExtra("uri_path", Uri.fromFile(outImgFile).getPath());
                        ac.startActivityForResult(cameraIntent, 3);
                    }
                })
                .setNegativeButton("相册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        ac.startActivityForResult(galleryIntent, 2);
                    }
                })
                .show();
    }

    //设置锁屏页面顶部文字
    public void setTopText() {
        final View v = LayoutInflater.from(ac).inflate(R.layout.base_dialog, null);
        new AlertDialog.Builder(ac)
                .setView(v)
                .setTitle("设置锁屏顶部文字")
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et = (EditText) v.findViewById(R.id.base_et);
                        String string = et.getText().toString();
                        Config.setString(ac, Config.SP_TOP_TEXT, string);
                    }
                }).show();
    }

    //设置密码匹配模型
    public void setLockPatter() {
        final View v = LayoutInflater.from(ac).inflate(R.layout.base_dialog, null);
        new AlertDialog.Builder(ac)
                .setView(v)
                .setTitle("设置密码的匹配模型")
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et = (EditText) v.findViewById(R.id.base_et);
                        String string = et.getText().toString();
                        if (string.length() < 1) {
                            ((BaseActivity) ac).Show("密码模型长度必须大于１");
                            return;
                        }
                        for (int i = 0; i < string.length(); i++)
                            if (string.charAt(i) > 68) {
                                ((BaseActivity) ac).Show("密码模型只能由ABCD四个字母组成");
                                return;
                            }
                        Config.setString(ac, Config.SP_LOCK_PATTER, string);
                        for (int i = 0; i < configChanges.size(); i++) {
                            configChanges.get(i).onPatterChange(string);
                        }
                    }
                }).show();
    }

    private static List<ConfigChange> configChanges = new ArrayList<>();
    //添加配置改变的回调监听
    public static void addConfigChangeListener(ConfigChange configChange) {
        if (configChanges.contains(configChange))
            return;
        configChanges.add(configChange);
    }

    //设置锁屏页面按钮颜色
    public void setLockItemColor() {
        ColorChoose colorChoose = new ColorChoose(ac);
        colorChoose.setSaveClickListener(new ActionCallback1() {
            @Override
            public void onActionDone(Object o) {
                Integer c = (Integer) o;
                Config.setInt(ac, Config.SP_LOCK_ITEM_COLOR, c);
                for (int i = 0; i < configChanges.size(); i++) {
                    configChanges.get(i).onLockItemColorChange(c);
                }
            }
        });
    }

    //设置锁屏页面字体的颜色
    public void setLockTextColor() {
        ColorChoose colorChoose = new ColorChoose(ac);
        colorChoose.setSaveClickListener(new ActionCallback1() {
            @Override
            public void onActionDone(Object o) {
                Integer c = (Integer) o;
                Config.setInt(ac, Config.SP_LOCK_TEXT_COLOR, c);
                for (int i = 0; i < configChanges.size(); i++) {
                    configChanges.get(i).onLockTextColorChange(c);
                }
            }
        });
    }

    /*
    锁屏配置改变的回调接口定义
     */

    public interface ConfigChange {
        void onPatterChange(String patter);
        void onLockItemColorChange(int color);
        void onLockTextColorChange(int color);
    }
}
