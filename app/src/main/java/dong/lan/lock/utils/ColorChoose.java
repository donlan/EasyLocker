/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package dong.lan.lock.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import dong.lan.lock.R;

/**
 * Created by 梁桂栋 on 2016年08月11日 15:41.
 * Email:760625325@qq.com
 * github:gitbub.com/donlan
 * desc:颜色选择的对话框
 */
public class ColorChoose extends AlertDialog.Builder {
    private View view;
    private SeekBar red;
    private SeekBar green;
    private SeekBar blue;
    private TextView showTv;
    private SeekBar.OnSeekBarChangeListener listener;
    private AlertDialog dialog;
    private ActionCallback1 callback;
    public ColorChoose(Context context) {
        super(context);

        listener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                showTv.setBackgroundColor(Color.rgb(red.getProgress(),green.getProgress(),blue.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };


        view  = LayoutInflater.from(context).inflate(R.layout.base_color_choose,null);
        red = (SeekBar) view.findViewById(R.id.sb_red);
        green = (SeekBar) view.findViewById(R.id.sb_green);
        blue = (SeekBar) view.findViewById(R.id.sb_blue);
        showTv = (TextView) view.findViewById(R.id.tv_showColor);

        view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback!=null)
                    callback.onActionDone(getColor());
                dialog.dismiss();
            }
        });

        red.setOnSeekBarChangeListener(listener);
        green.setOnSeekBarChangeListener(listener);
        blue.setOnSeekBarChangeListener(listener);



        this.setView(view);
        dialog = this.show();
    }

    public ColorChoose setSaveClickListener(ActionCallback1 callback){
        this.callback = callback;
        return this;
    }

    public int getColor(){
        return Color.rgb(red.getProgress(),green.getProgress(),blue.getProgress());
    }

}
