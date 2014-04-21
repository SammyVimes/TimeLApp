package com.danilov.TimeLApp.core.model;

/**
 * Created by Semyon Danilov on 19.04.2014.
 */
public class BusinessType {

    private long id;
    private String businessType;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(final String businessType) {
        this.businessType = businessType;
    }

    @Override
    public String toString() {
        return businessType;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof BusinessType)) {
            return false;
        }
        return id == ((BusinessType) o).getId();
    }

    @Override
    public int hashCode() {
        return (int) id;
    }
}
