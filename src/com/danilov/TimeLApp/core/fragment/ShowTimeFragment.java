package com.danilov.TimeLApp.core.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.danilov.TimeLApp.R;
import com.danilov.TimeLApp.core.model.Business;
import com.danilov.TimeLApp.core.model.BusinessType;
import com.danilov.TimeLApp.core.persistence.BusinessDBHelper;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Semyon Danilov on 18.04.2014.
 */
public class ShowTimeFragment extends Fragment {

    public static String YEAR = "YEAR";
    public static String MONTH = "MONTH";

    private int year;
    private int month;

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
        Bundle aruments = getArguments();
        this.year = aruments.getInt(YEAR);
        this.month = aruments.getInt(MONTH);
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

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        Calendar thisMonth = Calendar.getInstance();
        thisMonth.set(Calendar.YEAR, year);
        thisMonth.set(Calendar.MONTH, month);
        thisMonth.set(Calendar.DAY_OF_MONTH, 0);
        thisMonth.set(Calendar.HOUR_OF_DAY, 0);
        thisMonth.set(Calendar.MINUTE, 0);
        thisMonth.set(Calendar.SECOND, 0);
        thisMonth.set(Calendar.MILLISECOND, 0);
        BusinessDBHelper businessDBHelper = new BusinessDBHelper(getActivity());
        List<BusinessType> businessTypeList = businessDBHelper.getBusinessTypeList();
        List<Business> businesses = businessDBHelper.getMonthBusiness(thisMonth, businessTypeList.get(0));
        long timeSpent = 0;
        for (Business business : businesses) {
            timeSpent += business.getHoursSpent();
        }
        hoursQuantity.setText(String.valueOf(timeSpent));
        super.onActivityCreated(savedInstanceState);
    }
}
