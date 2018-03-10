package com.github.slick.sample.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.slick.Presenter;
import com.github.slick.SlickFragment;
import com.github.slick.sample.R;
import com.github.slick.sample.activity.ViewTestable;
import com.github.slick.sample.fragment.dagger.DaggerFragment;
import com.github.slick.sample.fragment.dagger.delegate.DelegateDaggerFragment;
import com.github.slick.sample.fragment.delegate.DelegateFragment;
import com.github.slick.test.SlickPresenterTestable;

public class FragmentSlickSimple extends SlickFragment<ViewFragmentSimple, PresenterFragmentSimple> implements ViewFragmentSimple {

    private static final String TAG = FragmentSlickSimple.class.getSimpleName();

    @Presenter
    PresenterFragmentSimple presenter;

    public FragmentSlickSimple() {
        // Required empty public constructor
    }

    public static FragmentSlickSimple newInstance() {
        return new FragmentSlickSimple();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_simple, container, false);

        view.findViewById(R.id.button_delegate_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment, DelegateFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });

        view.findViewById(R.id.button_dagger_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment, DaggerFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });

        view.findViewById(R.id.button_delegate_dagger_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment, DelegateDaggerFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

    @Override
    protected Object bind() {
        return FragmentSlickSimple_Slick.bind(this, 1, "2");
        // return Slick.bind(this, 1, "2");
    }

    @Override
    public SlickPresenterTestable<? extends ViewTestable> presenter() {
        return presenter;
    }
}