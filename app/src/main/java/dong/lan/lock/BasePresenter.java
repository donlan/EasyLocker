package dong.lan.lock;

import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 梁桂栋 on 2016年08月10日 10:44.
 * Email:760625325@qq.com
 */
public  interface BasePresenter {
    void start();
    void bindView(Fragment view);
}
