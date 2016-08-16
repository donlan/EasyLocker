package dong.lan.lock;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

/**
 * Created by 梁桂栋 on 2016年08月15日 15:16.
 * Email:760625325@qq.com
 * github:gitbub.com/donlan
 * desc:
 */
public abstract class BaseFragment extends Fragment {
    public static final String ARG_SECTION_NUMBER = "section_tittle";
    public abstract Fragment setupPresenter(BasePresenter presenter);
    public void Show(String string){
        Snackbar.make(getView(),string,Snackbar.LENGTH_SHORT).show();
    }
}
