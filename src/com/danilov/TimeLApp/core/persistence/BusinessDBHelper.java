package com.danilov.TimeLApp.core.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.danilov.TimeLApp.core.model.Business;
import com.danilov.TimeLApp.core.model.BusinessType;

import java.util.Date;

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

    public Business getBusiness(final Date day, final BusinessType businessType) {

        return null;
    }

}
