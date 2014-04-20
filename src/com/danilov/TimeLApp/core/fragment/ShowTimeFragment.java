package com.danilov.TimeLApp.core.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.danilov.TimeLApp.R;

/**
 * Created by Semyon Danilov on 18.04.2014.
 */
public class ShowTimeFragment extends Fragment {

    public static String YEAR = "YEAR";
    public static String MONTH = "MONTH";

    private TextView hoursQuantity = null;

    public static ShowTimeFragment newInstance(final int year, final int month) {
        ShowTimeFragment fragment = new ShowTimeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(YEAR, year);
        bundle.putInt(MONTH, month);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_time_fragment_layout, container, false);
        if (view == null) {
            throw new RuntimeException("Layout::show_time_fragment_layout not found. Can't create fragment");
        }
        hoursQuantity = (TextView) view.findViewById(R.id.hours_quantity);
        if (hoursQuantity == null) {
            throw new RuntimeException("TextView::hours_quantity not found. Can't create fragment");
        }
        return view;
    }

}