package com.danilov.TimeLApp.core.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    // BUSINESS TYPE Table - column names
    private static final String KEY_BUSINESS_TYPE = "businessType";

    // Table Create Statements
    // Business table create statement
    private static final String CREATE_TABLE_BUSINESS = "CREATE TABLE "
            + TABLE_BUSINESS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_BUSINESS_TYPE_ID + " INTEGER,"
            + KEY_CREATION_DATE + " DATETIME" + ")";

    // Business Type table create statement
    private static final String CREATE_TABLE_TAG = "CREATE TABLE " + TABLE_BUSINESS_TYPE
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_BUSINESS_TYPE + " TEXT)";


    public BusinessDBHelper(final Context context, final String name, final SQLiteDatabase.CursorFactory factory, final int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {

    }

}
