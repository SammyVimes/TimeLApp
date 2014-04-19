package com.danilov.TimeLApp.core.model;

import java.util.Date;

/**
 * Created by Semyon Danilov on 19.04.2014.
 */
public class Business {

    private long id;
    private Date creationDate;
    private int businessTypeId;
    private int hoursSpent;

    public int getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(final int businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }

    public long getId() {
        return id;
    }

    public int getHoursSpent() {
        return hoursSpent;
    }

    public void setHoursSpent(final int hoursSpent) {
        this.hoursSpent = hoursSpent;
    }
}
