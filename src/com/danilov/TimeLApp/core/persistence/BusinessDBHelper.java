package com.danilov.TimeLApp.core.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.danilov.TimeLApp.core.model.Business;
import com.danilov.TimeLApp.core.model.BusinessType;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Semyon Danilov on 19.04.2014.
 */
public class BusinessDBHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "businessDB";

    // Table Names
    private static final String TABLE_BUSINESS = "business";
    private static final String TABLE_BUSINESS_TYPE = "business_type";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATION_DATE = "creationDate";

    // BUSINESS Table - column names
    private static final String KEY_BUSINESS_TYPE_ID = "businessTypeId";
    private static final String KEY_HOURS_SPENT = "hoursSpent";

    // BUSINESS TYPE Table - column names
    private static final String KEY_BUSINESS_TYPE = "businessType";

    // Table Create Statements
    // Business table create statement
    private static final String CREATE_TABLE_BUSINESS = "CREATE TABLE "
            + TABLE_BUSINESS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_BUSINESS_TYPE_ID + " INTEGER,"
            + KEY_HOURS_SPENT + " INTEGER,"
            + KEY_CREATION_DATE + " DATETIME" + ")";

    // Business Type table create statement
    private static final String CREATE_TABLE_BUSINESS_TYPE = "CREATE TABLE " + TABLE_BUSINESS_TYPE
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_BUSINESS_TYPE + " TEXT)";

    public BusinessDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_BUSINESS);
        db.execSQL(CREATE_TABLE_BUSINESS_TYPE);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_BUSINESS);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_BUSINESS_TYPE);
        // create new tables
        onCreate(db);
    }

    public long createBusiness(final Business business) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db == null) {
            throw new RuntimeException("Creating business failed");
        }
        ContentValues values = new ContentValues();
        values.put(KEY_BUSINESS_TYPE_ID, business.getBusinessTypeId());
        values.put(KEY_CREATION_DATE, business.getCreationDate().getTime());
        values.put(KEY_HOURS_SPENT, business.getHoursSpent());
        long business_id = db.insert(TABLE_BUSINESS, null, values);
        db.close();
        return business_id;
    }

    public long createBusinessType(final BusinessType businessType) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db == null) {
            throw new RuntimeException("Creating business failed");
        }
        ContentValues values = new ContentValues();
        values.put(KEY_BUSINESS_TYPE, businessType.getBusinessType());
        long businessTypeId = db.insert(TABLE_BUSINESS_TYPE, null, values);
        db.close();
        return businessTypeId;
    }

    public void updateBusiness(final Business business) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db == null) {
            throw new RuntimeException("Updating business failed");
        }
        ContentValues values = new ContentValues();
        values.put(KEY_BUSINESS_TYPE_ID, business.getBusinessTypeId());
        values.put(KEY_HOURS_SPENT, business.getHoursSpent());
        db.update(TABLE_BUSINESS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(business.getId()) });
        db.close();
    }

    public Business getDayBusiness(final Calendar day, final BusinessType businessType) {
        Calendar nextDay = Calendar.getInstance();
        nextDay.setTime(day.getTime());
        nextDay.add(Calendar.DAY_OF_YEAR, 1);
        String selectQuery = "SELECT  * FROM " + TABLE_BUSINESS + " WHERE "
                + KEY_BUSINESS_TYPE_ID + " = " + businessType.getId() + " and " +
                KEY_CREATION_DATE + " >= " + day.getTimeInMillis() + " and "
                + KEY_CREATION_DATE + " <= " + nextDay.getTimeInMillis();
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        if (db == null) {
            throw new RuntimeException("Retrieving business failed");
        }
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        Business business = null;
        if (c.moveToFirst()) {
            business = new Business();
            business.setId(c.getLong((c.getColumnIndex(KEY_ID))));
            business.setBusinessTypeId(c.getInt(c.getColumnIndex(KEY_BUSINESS_TYPE_ID)));
            business.setHoursSpent(c.getInt(c.getColumnIndex(KEY_HOURS_SPENT)));
            business.setCreationDate(new Date(c.getLong(c.getColumnIndex(KEY_CREATION_DATE))));
        }
        return business;
    }

    public List<Business> getMonthBusiness(final Calendar month, final BusinessType businessType) {
        List<Business> businessMonth = new LinkedList<Business>();
        Calendar nextMonth = Calendar.getInstance();
        nextMonth.setTime(month.getTime());
        nextMonth.add(Calendar.MONTH, 1);
        String selectQuery = "SELECT  * FROM " + TABLE_BUSINESS + " WHERE "
                + KEY_BUSINESS_TYPE_ID + " = " + businessType.getId() + " and " +
                KEY_CREATION_DATE + " >= " + month.getTimeInMillis() + " and "
                + KEY_CREATION_DATE + " <= " + nextMonth.getTimeInMillis();
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        if (db == null) {
            throw new RuntimeException("Retrieving business failed");
        }
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Business business = new Business();
                business.setId(c.getLong((c.getColumnIndex(KEY_ID))));
                business.setBusinessTypeId(c.getInt(c.getColumnIndex(KEY_BUSINESS_TYPE_ID)));
                business.setHoursSpent(c.getInt(c.getColumnIndex(KEY_HOURS_SPENT)));
                business.setCreationDate(new Date(c.getLong(c.getColumnIndex(KEY_CREATION_DATE))));

                // adding to tags list
                businessMonth.add(business);
            } while (c.moveToNext());
        }
        return businessMonth;
    }

    public List<BusinessType> getBusinessTypeList() {
        List<BusinessType> businessTypeList = new LinkedList<BusinessType>();
        String selectQuery = "SELECT  * FROM " + TABLE_BUSINESS_TYPE;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        if (db == null) {
            throw new RuntimeException("Retrieving business failed");
        }
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                BusinessType businessType = new BusinessType();
                businessType.setId(c.getLong((c.getColumnIndex(KEY_ID))));
                businessType.setBusinessType(c.getString(c.getColumnIndex(KEY_BUSINESS_TYPE)));

                // adding to tags list
                businessTypeList.add(businessType);
            } while (c.moveToNext());
        }
        return businessTypeList;
    }

}
