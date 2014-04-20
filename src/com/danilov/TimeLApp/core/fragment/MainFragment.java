package com.danilov.TimeLApp.core.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.danilov.TimeLApp.R;

/**
 * Created by Semyon Danilov on 20.04.2014.
 */
public class MainFragment extends Fragment {

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }



    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_layot, container, false);
        if (view == null) {
            throw new RuntimeException("Layout::main_fragment_layot not found. Can't create fragment");
        }
        return view;
    }

}
