package com.github.slick.supportfragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks;

import com.github.slick.OnDestroyListener;
import com.github.slick.SlickPresenter;
import com.github.slick.SlickUniqueId;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-11-03
 */

public class SlickDelegateFragment<V, P extends SlickPresenter<V>> extends FragmentLifecycleCallbacks {

    private String id;
    private OnDestroyListener listener;

    private P presenter;
    private Class cls;
    private boolean multiInstance = false;

    public SlickDelegateFragment(P presenter, Class cls, String id) {
        if (presenter == null) {
            throw new IllegalStateException("Presenter cannot be null.");
        }
        this.presenter = presenter;
        this.cls = cls;
        this.id = id;
        if (id != null) multiInstance = true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onFragmentStarted(FragmentManager fm, Fragment fragment) {
        if (multiInstance) {
            if (isSameInstance(fragment)) {
                presenter.onViewUp((V) fragment);
            }

        } else if (cls.isInstance(fragment)) {
            presenter.onViewUp((V) fragment);
        }
    }

    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment fragment) {
        if (multiInstance) {
            if (isSameInstance(fragment)) {
                presenter.onViewDown();
            }
        } else if (cls.isInstance(fragment)) {
            presenter.onViewDown();
        }
    }

    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment fragment) {
        if (multiInstance) {
            if (isSameInstance(fragment)) {
                destroy(fragment);
            }
        } else if (cls.isInstance(fragment)) {
            destroy(fragment);
        }
    }

    private void destroy(Fragment fragment) {
        if (!fragment.getActivity().isChangingConfigurations()) {
            fragment.getFragmentManager().unregisterFragmentLifecycleCallbacks(this);
            presenter.onDestroy();
            if (listener != null) {
                listener.onDestroy(id);
            }
            presenter = null;
        }
    }

    public P getPresenter() {
        return presenter;
    }

    public void setListener(OnDestroyListener listener) {
        this.listener = listener;
    }


    public static String getId(Object view) {
        if (view instanceof SlickUniqueId) return ((SlickUniqueId) view).getUniqueId();
        return null;
    }

    private boolean isSameInstance(Object view) {
        final String id = getId(view);
        return id != null && id.equals(this.id);
    }
}