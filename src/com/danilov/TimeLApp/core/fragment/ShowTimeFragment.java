package com.danilov.TimeLApp.core.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.danilov.TimeLApp.R;
import com.danilov.TimeLApp.core.model.Business;
import com.danilov.TimeLApp.core.model.BusinessType;
import com.danilov.TimeLApp.core.persistence.BusinessDBHelper;
import com.danilov.TimeLApp.core.util.Util;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Semyon Danilov on 18.04.2014.
 */
public class ShowTimeFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static String YEAR = "YEAR";
    public static String MONTH = "MONTH";

    private int year;
    private int month;

    private SlidingMenu slidingMenu = null;

    private List<BusinessType> businessTypeList = null;

    private TextView hoursQuantity = null;

    private Map<BusinessType, List<Business>> businessesByTypes = new HashMap<BusinessType, List<Business>>();

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
        slidingMenu = (SlidingMenu) view.findViewById(R.id.sliding_menu_layout);
        if (slidingMenu == null) {
            throw new RuntimeException("SlidingMenu::sliding_menu_layout not found. Can't create fragment");
        }
        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        Calendar thisMonth = Util.getClearMonth(year, month);
        BusinessDBHelper businessDBHelper = new BusinessDBHelper(getActivity());
        List<BusinessType> businessTypeList = businessDBHelper.getBusinessTypeList();
        if (businessTypeList.isEmpty()) {
            super.onActivityCreated(savedInstanceState);
            return;
        }
        for (BusinessType businessType : businessTypeList) {
            List<Business> businessList = businessDBHelper.getMonthBusiness(thisMonth, businessType);
            businessesByTypes.put(businessType, businessList);
        }
        businessTypeList = businessDBHelper.getBusinessTypeList();
        ListView listView = (ListView) this.getView().findViewById(R.id.menu_list);
        listView.setAdapter(new ArrayAdapter<BusinessType>(this.getActivity(), R.layout.drawer_list_item, R.id.textField, businessTypeList));
        listView.setOnItemClickListener(this);
        onSelectBusinessType(businessTypeList.get(0));
        super.onActivityCreated(savedInstanceState);
    }

    private View previousListItemView = null;

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        BusinessType businessType = (BusinessType) parent.getItemAtPosition(position);
        onSelectBusinessType(businessType);
        view.setSelected(true);
        parent.setSelected(true);
        if (previousListItemView != null && previousListItemView != view) {
            previousListItemView.setSelected(false);
        }
        previousListItemView = view;
        slidingMenu.toggle();
    }

    private void onSelectBusinessType(final BusinessType businessType) {
        List<Business> businesses = businessesByTypes.get(businessType);
        ActionBarActivity activity = (ActionBarActivity) getActivity();
        activity.setTitle(businessType.getBusinessType());
        long timeSpent = 0;
        for (Business business : businesses) {
            timeSpent += business.getHoursSpent();
        }
        hoursQuantity.setText(String.valueOf(timeSpent));
    }

}
