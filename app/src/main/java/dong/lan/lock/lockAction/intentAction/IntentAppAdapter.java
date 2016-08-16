package dong.lan.lock.lockAction.intentAction;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import dong.lan.lock.R;
import dong.lan.lock.lockAction.intentAction.model.App;

/**
 * Created by 梁桂栋 on 2016年08月15日 15:53.
 * Email:760625325@qq.com
 * github:gitbub.com/donlan
 * desc:锁屏跳转应用的本机应用显示适配器
 */
public class IntentAppAdapter extends RecyclerView.Adapter<IntentAppAdapter.Holder> {

    private List<App> apps;
    private OnAppClickListener onAppClickListener;
    private OnAppLongClickListener onAppLongClickListener;

    public IntentAppAdapter(List<App> apps){
        this.apps = apps;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Holder holder = new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_intent_app, null));
        if(onAppClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAppClickListener.onAppClick(apps.get(holder.getLayoutPosition()));
                }
            });
        }
        if(onAppLongClickListener!=null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onAppLongClickListener.onAppLongClick(apps.get(holder.getLayoutPosition()));
                    return true;
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        App app = apps.get(position);
        holder.icon_iv.setImageDrawable(app.getIcon());
        holder.icon_tv.setText(app.getIntentPattern());
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{
        public TextView icon_tv;
        public ImageView icon_iv;
        public Holder(View itemView) {
            super(itemView);
            icon_tv = (TextView) itemView.findViewById(R.id.icon_tv);
            icon_iv = (ImageView) itemView.findViewById(R.id.icon_iv);
        }
    }
    public void setOnAppClickListener(OnAppClickListener clickListener){
        this.onAppClickListener = clickListener;
    }
    public void setOnAppLOngClickListener(OnAppLongClickListener clickListener){
        this.onAppLongClickListener = clickListener;
    }
    public interface OnAppClickListener{
        void onAppClick(App app);
    }
    public interface OnAppLongClickListener{
        void onAppLongClick(App app);
    }
}
