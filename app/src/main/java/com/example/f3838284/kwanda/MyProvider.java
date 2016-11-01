package com.example.f3838284.kwanda; /**
 * Created by F3838284 on 10/27/2016.
 */


import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import android.net.Uri;
import android.text.TextUtils;

public class MyProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.example.f3838284.kwanda";
    static final String URL = "content://" + PROVIDER_NAME + "/data";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String _ID = "_id";
    static final String FDLP = "FDLP";

    private static HashMap<String, String> DATA_PROJECTION_MAP;

    static final int DATE = 1;
    static final int DATE_ID = 2;

    static final UriMatcher uriMatcher;
    public static String DeliveryDate = "DayOfDelivery";
    public static String PregnancyDuration = "Duration";
    public static String SelectedDay = "SeletedDay";
    public static String SelectedAngle = "SeletedAngle";

    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "data", DATE);
        uriMatcher.addURI(PROVIDER_NAME, "data/#", DATE_ID);
    }

    /**
     * Database specific constant declarations
     */
    private SQLiteDatabase db;
    static final String DATABASE_NAME = "Pregnancy";
    static final String DATES_TABLE_NAME = "data";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + DATES_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " FDLP TEXT NOT NULL, " +
                    " SeletedAngle TEXT NOT NULL, " +
                    " DayOfDelivery TEXT NOT NULL, " +
                    " SeletedDay TEXT NOT NULL, " +
                    " Duration TEXT NOT NULL);";

    /**
     * Helper class that actually creates and manages
     * the provider's underlying data repository.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL("DROP TABLE IF EXISTS " +  DATES_TABLE_NAME);
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +  DATES_TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */
        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        db.execSQL("DROP TABLE IF EXISTS " +  DATES_TABLE_NAME);
        db.execSQL(CREATE_DB_TABLE);

        /**
         * Add a new date record
         */
        long rowID = db.insert(	DATES_TABLE_NAME, null, values);

        /**
         * If record is added successfully
         */

        if (rowID > 0)
        {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(DATES_TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case DATE:
                qb.setProjectionMap(DATA_PROJECTION_MAP);
                break;

            case DATE_ID:
                qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if (sortOrder == null || sortOrder == ""){
            /**
             * By default sort on date
             */
            sortOrder = FDLP;
        }
        Cursor c = qb.query(db,	projection,	selection, selectionArgs,null, null, sortOrder);

        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)){
            case DATE:
                count = db.delete(DATES_TABLE_NAME, selection, selectionArgs);
                break;

            case DATE_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete( DATES_TABLE_NAME, _ID +  " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)){
            case DATE:
                count = db.update(DATES_TABLE_NAME, values, selection, selectionArgs);
                break;

            case DATE_ID:
                count = db.update(DATES_TABLE_NAME, values, _ID + " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            /**
             * Get all date records
             */
            case DATE:
                return "vnd.android.cursor.dir/vnd.example.date";

            /**
             * Get a particular date
             */
            case DATE_ID:
                return "vnd.android.cursor.item/vnd.example.date";

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}
