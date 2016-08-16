package dong.lan.lock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;


/**
 * Created by 梁桂栋 on 2016年08月09日 15:18.
 * Email:760625325@qq.com
 */
public class BaseActivity extends AppCompatActivity {
    FrameLayout container;
    View content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);
        container = (FrameLayout) findViewById(R.id.view_container);
    }


    public void setupView(int layoutId){
        content = LayoutInflater.from(this).inflate(layoutId,null);
        container.addView(content);
    }


    public void Show(String str){
        Snackbar.make(container,str,Snackbar.LENGTH_SHORT).show();
    }

    public View findView(int id) {
        return content.findViewById(id);
    }
}
