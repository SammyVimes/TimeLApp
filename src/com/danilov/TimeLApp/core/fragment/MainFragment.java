package com.danilov.TimeLApp.core.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.danilov.TimeLApp.R;
import com.danilov.TimeLApp.core.model.Business;
import com.danilov.TimeLApp.core.model.BusinessType;
import com.danilov.TimeLApp.core.persistence.BusinessDBHelper;
import com.danilov.TimeLApp.core.util.Util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Semyon Danilov on 20.04.2014.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    private TextView tmpHoursQuantityTextView = null;
    private Spinner businessTypeSpinner = null;
    private int tmpHoursQuantity = 0;

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

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        Button buttonAddHour = (Button) this.getView().findViewById(R.id.buttonAddHour);
        Button buttonSubmit = (Button) this.getView().findViewById(R.id.buttonSubmit);
        Button buttonAddType = (Button) this.getView().findViewById(R.id.buttonAddType);
        buttonAddHour.setOnClickListener(this);
        buttonSubmit.setOnClickListener(this);
        buttonAddType.setOnClickListener(this);

        tmpHoursQuantityTextView = (TextView) this.getView().findViewById(R.id.hoursTmp);
        businessTypeSpinner = (Spinner) this.getView().findViewById(R.id.businessTypesSpinner);
        loadTypes();
        super.onActivityCreated(savedInstanceState);
    }

    private void loadTypes() {
        BusinessDBHelper helper = new BusinessDBHelper(getActivity());
        List<BusinessType> businessTypeList = helper.getBusinessTypeList();
        //i have overridden the toString method of business type
        ArrayAdapter<BusinessType> arrayAdapter = new ArrayAdapter<BusinessType>(getActivity(),
                                                    android.R.layout.simple_spinner_dropdown_item,
                                                    businessTypeList);
        businessTypeSpinner.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(final View v) {
         switch (v.getId()) {
             case R.id.buttonAddHour:
                 addHour();
                 break;
             case R.id.buttonSubmit:
                 submit();
                 break;
             case R.id.buttonAddType:
                 addType();
                 break;
             default:
                 break;
         }
    }

    private void addHour() {
        tmpHoursQuantity++;
        tmpHoursQuantityTextView.setText(tmpHoursQuantity + " hours");
    }

    private void addType() {
        EditText editText = (EditText) this.getView().findViewById(R.id.type);
        BusinessType businessType = new BusinessType();
        Editable editableText = editText.getText();
        if (editableText == null) {
            return;
        }
        businessType.setBusinessType(editableText.toString());
        BusinessDBHelper helper = new BusinessDBHelper(getActivity());
        long id = helper.createBusinessType(businessType);
        businessType.setId(id);
    }

    private void submit() {
        BusinessType businessType = (BusinessType) businessTypeSpinner.getSelectedItem();
        Calendar day = Calendar.getInstance();
        day.set(Calendar.HOUR_OF_DAY, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MILLISECOND, 0);
        BusinessDBHelper businessDBHelper = new BusinessDBHelper(getActivity());
        Business business = businessDBHelper.getDayBusiness(day, businessType);
        if (business == null) {
            business = new Business();
            business.setHoursSpent(tmpHoursQuantity);
            business.setCreationDate(new Date());
            business.setBusinessTypeId(businessType.getId());
            businessDBHelper.createBusiness(business);
            Util.toast(getActivity(), "Created");
        } else {
            business.setHoursSpent(business.getHoursSpent() + tmpHoursQuantity);
            businessDBHelper.updateBusiness(business);
            Util.toast(getActivity(), "Updated");
        }
    }

}
