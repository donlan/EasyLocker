package dong.lan.lock.lockAction.intentAction;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import dong.lan.lock.BaseFragment;
import dong.lan.lock.BasePresenter;
import dong.lan.lock.R;
import dong.lan.lock.lockAction.intentAction.model.App;

/**
 * Created by 梁桂栋 on 2016年08月15日 15:12.
 * Email:760625325@qq.com
 * github:gitbub.com/donlan
 * description:已经设置锁屏跳转的应用
 */
public class LockIntentAppsFragment extends BaseFragment {


    protected RecyclerView appsList;
    private IntentAppPresenter presenter;
    private ViewStub viewStub;
    protected View emptyView;
    protected IntentAppAdapter appAdapter;

    public static LockIntentAppsFragment newInstance(String tittle) {
        LockIntentAppsFragment fragment = new LockIntentAppsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_NUMBER, tittle);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setted_lock_app, container, false);
        appsList = (RecyclerView) rootView.findViewById(R.id.hadSettedAppsList);
        appsList.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        viewStub = (ViewStub) rootView.findViewById(R.id.empty_stub);
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (presenter.getIntentApps().size() == 0) {
            emptyView = viewStub.inflate();
        }else{
            presenter.refreshIntentApps();
        }
    }

    @Override
    public Fragment setupPresenter(BasePresenter presenter) {
        this.presenter = (IntentAppPresenter) presenter;
        return this;
    }
}
