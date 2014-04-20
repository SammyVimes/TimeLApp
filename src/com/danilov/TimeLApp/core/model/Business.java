package com.danilov.TimeLApp.core.model;

import java.util.Date;

/**
 * Created by Semyon Danilov on 19.04.2014.
 */
public class Business {

    private long id;
    private Date creationDate;
    private long businessTypeId;
    private int hoursSpent = 0;

    public long getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(final long businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setId(final long id) {
        this.id = id;
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
