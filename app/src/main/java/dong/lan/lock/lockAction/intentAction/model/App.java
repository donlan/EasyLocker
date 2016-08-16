package dong.lan.lock.lockAction.intentAction.model;

import android.graphics.drawable.Drawable;

/**
 * Created by 梁桂栋 on 2016年08月15日 15:57.
 * Email:760625325@qq.com
 * github:gitbub.com/donlan
 * desc:
 */
public class App {
    private String pkgName;
    private String intentPattern;
    private Drawable icon;


    public App(String pkgName, String intentPattern) {
        this.pkgName = pkgName;
        this.intentPattern = intentPattern;
    }

    public App(String pkgName, String intentPattern, Drawable icon) {
        this.pkgName = pkgName;
        this.intentPattern = intentPattern;
        this.icon = icon;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getIntentPattern() {
        return intentPattern;
    }

    public void setIntentPattern(String intentPattern) {
        this.intentPattern = intentPattern;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof App)
            return this.pkgName.equals(((App)o).getPkgName());
        else if(o instanceof String)
            return this.pkgName.equals(o.toString());
        return super.equals(o);
    }
}
