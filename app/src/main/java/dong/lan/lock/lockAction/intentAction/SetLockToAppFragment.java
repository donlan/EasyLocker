package dong.lan.lock.lockAction.intentAction;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dong.lan.lock.BaseFragment;
import dong.lan.lock.BasePresenter;
import dong.lan.lock.R;
import dong.lan.lock.lockAction.intentAction.model.App;

/**
 * Created by 梁桂栋 on 2016年08月15日 15:12.
 * Email:760625325@qq.com
 * github:gitbub.com/donlan
 * description:设置锁屏应用跳转
 */
public class SetLockToAppFragment extends BaseFragment implements IntentAppAdapter.OnAppClickListener {

    private RecyclerView appsList;
    private IntentAppPresenter presenter;
    public static SetLockToAppFragment newInstance(String tittle) {
        SetLockToAppFragment fragment = new SetLockToAppFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_NUMBER, tittle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_to_set_lock_app, container, false);
        appsList = (RecyclerView) rootView.findViewById(R.id.appsList);
        appsList.setLayoutManager(new GridLayoutManager(getActivity(),4));
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        IntentAppAdapter appAdapter = new IntentAppAdapter(presenter.getApps());
        appAdapter.setOnAppClickListener(this);
        appsList.setAdapter(appAdapter);
    }

    @Override
    public Fragment setupPresenter(BasePresenter presenter) {
        this.presenter = (IntentAppPresenter) presenter;
        return this;
    }

    @Override
    public void onAppClick(App app) {
        presenter.setAppIntentPatter(app);
    }
}
