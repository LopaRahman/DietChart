package com.example.lutfihabiba.dietchartfinal1;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by Lutfi Habiba on 26/06/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "DietChartManeger";

    // DietChart table name
    private static final String TABLE_DIETCHART = "dietchart";

    // DietChart Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_CATAGORY = "catagory";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String KEY_CATAGORY_VALUE="catagoryValue";
    private final ArrayList<DietChart> dietCharts_list = new ArrayList<DietChart>();

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DIETCHART_TABLE = "CREATE TABLE " + TABLE_DIETCHART + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CATAGORY + " TEXT,"
                + KEY_DATE + " TEXT," + KEY_TIME + " TEXT" +KEY_CATAGORY_VALUE+"TEXT,"+ ")";
        db.execSQL(CREATE_DIETCHART_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIETCHART);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void Add_DietChart(DietChart dietChart) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CATAGORY, dietChart.get_catagory());
        values.put(KEY_DATE, dietChart.get_date());
        values.put(KEY_TIME, dietChart.get_time());
        values.put(KEY_CATAGORY_VALUE,dietChart.get_catagoryValue());
        // Inserting Row
        db.insert(TABLE_DIETCHART, null, values);
        db.close(); // Closing database connection
    }

    // Getting single Chart
    DietChart Get_DietChart(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DIETCHART, new String[] { KEY_ID,
                        KEY_CATAGORY, KEY_DATE, KEY_TIME, KEY_CATAGORY_VALUE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        DietChart dietChart = new DietChart(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4));
        // return contact
        cursor.close();
        db.close();

        return dietChart;
    }
    public ArrayList<DietChart> Get_DietCharts() {
        try {
            dietCharts_list.clear();

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_DIETCHART;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    DietChart dietChart = new DietChart();
                    dietChart.set_id(Integer.parseInt(cursor.getString(0)));
                    dietChart.set_catagory(cursor.getString(1));
                    dietChart.set_date(cursor.getString(2));
                    dietChart.set_time(cursor.getString(3));
                    dietChart.set_catagoryValue(cursor.getString(4));
                    // Adding diet to list
                    dietCharts_list.add(dietChart);
                } while (cursor.moveToNext());
            }

            // return diet list
            cursor.close();
            db.close();
            return dietCharts_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_Diet_Chart", "" + e);
        }

        return dietCharts_list;
    }

    // Updating single dietChart
    public int Update_DietChart(DietChart dietChart) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CATAGORY, dietChart.get_catagory());
        values.put(KEY_DATE, dietChart.get_date());
        values.put(KEY_TIME, dietChart.get_time());
        values.put(KEY_CATAGORY_VALUE,dietChart.get_catagoryValue());

        // updating row
        return db.update(TABLE_DIETCHART, values, KEY_ID + " = ?",
                new String[] { String.valueOf(dietChart.get_id()) });
    }

    // Deleting single contact
    public void Delete_DietChart(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DIETCHART, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    // Getting contacts Count
    public int Get_Total_DietChart() {
        String countQuery = "SELECT  * FROM " + TABLE_DIETCHART;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }


}
